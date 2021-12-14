import { Slider } from "@blueprintjs/core";
import { FunctionComponent, useContext, useMemo } from "react";
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

    const fixtureInd = patchData.fixtures.findIndex( fixture => fixture.uuid === selectedFixture?.uuid)

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
            key={`${selectedFixture?.uuid}_${chFInd}`}
            />
        })}
    </>;
}

type ChannelFunctionSliderProps = {
    chF: ChannelFunction;
    chFInd: number;
    fixtureInd: number;
}

const ChannelFunctionSlider = ({ chF, chFInd, fixtureInd }: ChannelFunctionSliderProps) => {
    const rigState = useContext(RigStateContext);

    const chInd = chF.multiByteChannelInd

    const labelStepSize = useMemo(() => {
        const numberOfLabels = 8;
        const rangeSize = chF.dmxTo - chF.dmxFrom
        return rangeSize / numberOfLabels
    }, [chF])

    const disabled = rigState[fixtureInd]?.chFDisabled[chFInd]
    const chValue = rigState[fixtureInd]?.chValues[chInd] ?? 0

    return <div>
        <div>
            <div css={`
                color: ${disabled === null ? "inherit" : "gray"}
            `}>
                {chF.name}
                {disabled !== null && `  (${disabled})`}
            </div>
        </div>
        <Slider
            min={chF.dmxFrom}
            max={chF.dmxTo}
            value={chValue}
            labelStepSize={labelStepSize}
            onChange={ newValue => {
                const msg = new ClientMessage.SetChannel({
                    fixtureInd: fixtureInd, 
                    chInd: chInd, 
                    value: newValue}
                )
                connectionProvider.send(msg)
            }}
            disabled={disabled !== null}
        />
    </div>
}


