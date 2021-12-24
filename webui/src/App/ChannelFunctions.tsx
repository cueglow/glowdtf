import { Slider } from "@blueprintjs/core";
import { Tooltip2 } from "@blueprintjs/popover2";
import _ from "lodash";
import React, { FunctionComponent, useContext, useMemo } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchContext } from "src/ConnectionProvider/PatchDataProvider";
import { RigStateContext, RigStateProvider } from "src/ConnectionProvider/RigStateProvider";
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

    const channelFunctionElements = selectedFixture && sortedGroups.map(({ featureGroup, chFs }) => {
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
                return <ChannelFunctionElement
                    chF={channelFunction}
                    chFInd={chFInd}
                    fixtureUuid={selectedFixture?.uuid}
                    channels={channels}
                    key={`${selectedFixture?.uuid}_${chFInd}`}
                />
            })}
        </>
    })

    return <>
        <h3 className="bp3-heading" css={`margin-top: 0;`}>Channel Functions</h3>
        <RigStateProvider>
            {channelFunctionElements}
        </RigStateProvider>
    </>
}

type ChannelFunctionElementProps = {
    chF: ChannelFunction;
    chFInd: number;
    fixtureUuid: string;
    channels: MultiByteChannel[];
}

const ChannelFunctionElement = ({ chF, chFInd, fixtureUuid, channels }: ChannelFunctionElementProps) => {
    const { chInd, geometry, channelName, dmxAddress, labelStepSize } = useMemo(() => {
        const channel = channels[chF.multiByteChannelInd];
        const geometry = channel?.geometry;
        const chInd = chF.multiByteChannelInd;
        // label step size
        const numberOfLabels = 8;
        const rangeSize = chF.dmxTo - chF.dmxFrom;
        const labelStepSize = rangeSize / numberOfLabels;

        const dmxAddress = channel.offsets.map((offset) => `${channel.dmxBreak}.${offset}`).join(", ");

        const channelName = channel?.name;
        return { chInd, geometry, channelName, dmxAddress, labelStepSize };
    }, [chF, channels])

    // boundary from chF-related to rigState-value-related

    const rigState = useContext(RigStateContext);

    const fixtureState = rigState[fixtureUuid]

    const chValue = fixtureState?.chValues[chInd] ?? 0

    const disabled = fixtureState?.chFDisabled[chFInd];

    const channelValueDependentElements = useMemo(() => {
        const outOfRange = !_.inRange(chValue, chF.dmxFrom, chF.dmxTo + 1)

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
            {"DMX Channel: " + channelName}
            <br />
            {"DMX Offset: " + dmxAddress}
        </>

        const tooltipedChannelFunctionName =
            <Tooltip2 content={tooltipContent}>
                <>
                    {chF.name}
                    {disabled !== null && `  (${disabled})`}
                </>
            </Tooltip2>

        return <div css={`
        padding-left: ${2 * bp.ptGridSizePx}px;
        padding-right: ${2 * bp.ptGridSizePx}px;
        padding-bottom: ${1 * bp.ptGridSizePx}px;
        overflow-x: hidden;
        `}>
            <div>
                <div css={`
                color: ${(disabled !== null || outOfRange) ? "gray" : "inherit"};
            `}>
                    {tooltipedChannelFunctionName}
                </div>
            </div>
            <Slider
                min={chF.dmxFrom}
                max={chF.dmxTo}
                value={chValue}
                labelStepSize={labelStepSize}
                onChange={newValue => {
                    const msg = new ClientMessage.SetChannel({
                        fixtureUuid: fixtureUuid,
                        chInd: chInd,
                        value: newValue
                    }
                    );
                    connectionProvider.send(msg);
                }}
                disabled={disabled !== null}
            />
        </div>;
    }, [chF, chInd, channelName, dmxAddress, fixtureUuid, geometry, labelStepSize, chValue, disabled])

    return channelValueDependentElements


}