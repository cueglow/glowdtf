import { Slider, Tooltip } from "@blueprintjs/core";
import { Tooltip2 } from "@blueprintjs/popover2";
import { FunctionComponent, useContext, useMemo } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchContext } from "src/ConnectionProvider/PatchDataProvider";
import { RigStateContext } from "src/ConnectionProvider/RigStateProvider";
import { ChannelFunction } from "src/Types/FixtureType";
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

    return <>
        <h3 css={`margin-top: 0;`}>Channel Functions</h3>
        {/* {selectedFixture?.fid} {selectedFixture?.name} */}
        {channels?.flatMap((channel) => {
            console.log("channel with channelFunctionIndices ", channel?.channelFunctionIndices)
            return channel?.channelFunctionIndices?.slice(1).map((chFInd) => {
                const channelFunction = channelFunctions[chFInd]
                console.log("non-raw channelFunction ", channelFunction.name)
                return <ChannelFunctionSlider
                    chF={channelFunction}
                    chFInd={chFInd}
                    fixtureInd={fixtureInd}
                    geometry={channel?.geometry}
                    key={`${selectedFixture?.uuid}_${chFInd}`}
                />
            })
        })}
        <h4>Raw DMX</h4>
        {channels?.map((channel) => {
            const chFInd = channel?.channelFunctionIndices[0] ?? 0
            const channelFunction = channelFunctions[chFInd]
            console.log("raw channelFunction ", channelFunction.name)
            return <ChannelFunctionSlider
                chF={channelFunction}
                chFInd={chFInd}
                fixtureInd={fixtureInd}
                geometry={channel?.geometry}
                key={`${selectedFixture?.uuid}_${chFInd}`}
            />
        })}
    </>;
}

type ChannelFunctionSliderProps = {
    chF: ChannelFunction;
    chFInd: number;
    fixtureInd: number;
    geometry: string;
}

const ChannelFunctionSlider = ({ chF, chFInd, fixtureInd, geometry }: ChannelFunctionSliderProps) => {
    const rigState = useContext(RigStateContext);

    const chInd = chF.multiByteChannelInd

    const labelStepSize = useMemo(() => {
        const numberOfLabels = 8;
        const rangeSize = chF.dmxTo - chF.dmxFrom
        return rangeSize / numberOfLabels
    }, [chF])

    const disabled = rigState[fixtureInd]?.chFDisabled[chFInd]
    const chValue = rigState[fixtureInd]?.chValues[chInd] ?? 0

    const tooltipContent = <>
        {"Geometry: " + geometry}
        <br />
        {"Attribute: " + (chF.attribute ?? `Raw DMX Channel ${chInd+1}`)}
    </>

    return <div css={`
        padding-left: ${2 * bp.ptGridSizePx}px;
        padding-right: ${3 * bp.ptGridSizePx}px;
        padding-bottom: ${1 * bp.ptGridSizePx}px;
        overflow-x: hidden;
        `}>
        <div>
            <div css={`
                color: ${disabled === null ? "inherit" : "gray"}
            `}>
                <Tooltip2 content={tooltipContent}>
                    {chF.name}
                    {disabled !== null && `  (${disabled})`}
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


