import { Slider } from "@blueprintjs/core";
import { Tooltip2 } from "@blueprintjs/popover2";
import _ from "lodash";
import { FunctionComponent, useContext, useMemo } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchContext } from "src/ConnectionProvider/PatchDataProvider";
import { RigStateContext } from "src/ConnectionProvider/RigStateProvider";
import { ChannelFunction, MultiByteChannel } from "src/Types/FixtureType";
import { PatchFixture } from "src/Types/Patch";
import { } from "styled-components/macro";

export const ChannelFunctions: FunctionComponent<{ selectedFixture: PatchFixture | null }> = ({ selectedFixture }) => {
    const patchData = useContext(PatchContext);

    const fixtureTypes = patchData.fixtureTypes

    const fixtureTypeId = selectedFixture?.fixtureTypeId

    const dmxMode = selectedFixture?.dmxMode

    const fixtureType = fixtureTypes.find((fixtureType) =>
        fixtureType.fixtureTypeId === fixtureTypeId
    )

    const dmxModeObject = fixtureType?.modes.find(({ name }) => name === dmxMode)

    const channelFunctions = dmxModeObject?.channelFunctions ?? []

    const channels = dmxModeObject?.multiByteChannels

    const fixtureInd = patchData.fixtures.findIndex(fixture => fixture.uuid === selectedFixture?.uuid)

    const chFsByFeatureGroup = _.groupBy(channelFunctions, chF => chF.featureGroup)

    const featureGroupOrder = ["Dimmer", "Position", "Color", "Gobo", "Beam", "Focus", "Shapers", "Video", "Control"]

    const sortedGroups: { featureGroup: string, chFs: ChannelFunction[] }[] = []

    // first the known Feature Groups in wanted order
    featureGroupOrder.forEach(featureGroup => {
        const chFs = chFsByFeatureGroup[featureGroup]
        if (chFs != null) {
            sortedGroups.push({ featureGroup, chFs })
        }
    })
    // now the unknown Feature Groups
    _.forEach(chFsByFeatureGroup, (chFs, featureGroup) => {
        if (featureGroup !== "Raw DMX" && !featureGroupOrder.includes(featureGroup)) {
            sortedGroups.push({ featureGroup, chFs })
        }
    })
    // and last, Raw DMX
    const rawDmxChFs = chFsByFeatureGroup["Raw DMX"]
    if (rawDmxChFs != null) {
        sortedGroups.push({ featureGroup: "Raw DMX", chFs: chFsByFeatureGroup["Raw DMX"] })
    }

    return <>
        <h3 className="bp3-heading" css={`margin-top: 0;`}>Channel Functions</h3>
        {/* {selectedFixture?.fid} {selectedFixture?.name} */}
        {sortedGroups.map(({ featureGroup, chFs }) => {
            return <>

                <h4 className="bp3-heading" css={`
                    margin-top: ${2 * bp.ptGridSizePx}px;
                `}>
                    <Tooltip2 content={`Feature Group ${featureGroup}`}>
                        {featureGroup}
                    </Tooltip2>
                </h4>

                {chFs?.map((channelFunction) => {
                    if (channels == null) { return <></> }
                    const chFInd = channelFunctions.indexOf(channelFunction)
                    const channel = channels[channelFunction.multiByteChannelInd]
                    return <ChannelFunctionSlider
                        chF={channelFunction}
                        chFInd={chFInd}
                        fixtureInd={fixtureInd}
                        geometry={channel?.geometry}
                        channel={channel}
                        key={`${selectedFixture?.uuid}_${chFInd}`}
                    />
                })}
            </>
        })}
    </>;
}

type ChannelFunctionSliderProps = {
    chF: ChannelFunction;
    chFInd: number;
    fixtureInd: number;
    geometry: string;
    channel: MultiByteChannel;
}

const ChannelFunctionSlider = ({ chF, chFInd, fixtureInd, geometry, channel }: ChannelFunctionSliderProps) => {
    const rigState = useContext(RigStateContext);

    const chInd = chF.multiByteChannelInd

    const labelStepSize = useMemo(() => {
        const numberOfLabels = 8;
        const rangeSize = chF.dmxTo - chF.dmxFrom
        return rangeSize / numberOfLabels
    }, [chF])

    const disabled = rigState[fixtureInd]?.chFDisabled[chFInd]
    const chValue = rigState[fixtureInd]?.chValues[chInd] ?? 0
    const dmxAddress = channel.offsets.map((offset) =>
        `${channel.dmxBreak}.${offset}`
    ).join(", ")

    const outOfRange = !_.inRange(chValue, chF.dmxFrom, chF.dmxTo+1)

    const tooltipContent = <>
        {outOfRange && <>
            <b>{"Value out of range"}</b>
        <br />
        </>}
        {(disabled !== null) && <>
            <b>{"Disabled by ModeMaster"}</b>
            <br />
            <b>{disabled}</b>
            <br />
        </>}
        {"Geometry: " + geometry}
        <br />
        {"Attribute: " + (chF.attribute ?? `Raw DMX Channel ${chInd + 1}`)}
        <br />
        {"DMX Channel: " + channel.name}
        <br />
        {"DMX Offset: " + dmxAddress}
    </>

    return <div css={`
        padding-left: ${2 * bp.ptGridSizePx}px;
        padding-right: ${3 * bp.ptGridSizePx}px;
        padding-bottom: ${1 * bp.ptGridSizePx}px;
        overflow-x: hidden;
        `}>
        <div>
            <div css={`
                color: ${(disabled !== null || outOfRange) ?  "gray" : "inherit"};
            `}>
                <Tooltip2 content={tooltipContent}>
                    <>
                        {chF.name}
                        {disabled !== null && `  (${disabled})`}
                    </>
                </Tooltip2>
            </div>
        </div>
        <Slider
            min={chF.dmxFrom}
            max={chF.dmxTo}
            value={chValue}
            labelStepSize={labelStepSize}
            onChange={newValue => {
                const msg = new ClientMessage.SetChannel({
                    fixtureInd: fixtureInd,
                    chInd: chInd,
                    value: newValue
                }
                )
                connectionProvider.send(msg)
            }}
            disabled={disabled !== null}
        />
    </div>
}


