import { Slider } from "@blueprintjs/core";
import { FunctionComponent, useContext, useMemo, useState } from "react";
import { PatchContext } from "src/ConnectionProvider/PatchDataProvider";
import { ChannelFunction } from "src/Types/FixtureType";
import { PatchFixture } from "src/Types/Patch";
import { } from "styled-components/macro";

export const ChannelFunctions: FunctionComponent<{ selectedFixture: PatchFixture | null }> = ({ selectedFixture }) => {
    const patchData = useContext(PatchContext);

    const fixtureType = patchData.fixtureTypes.find( ({fixtureTypeId}) => 
        fixtureTypeId === selectedFixture?.fixtureTypeId
    )

    const dmxMode = fixtureType?.modes.find( ({name}) => name === selectedFixture?.dmxMode)
    
    return <>
        <h3 css={`margin-top: 0;`}>Channel Functions</h3>
        {/* {selectedFixture?.fid} {selectedFixture?.name} */}
        {dmxMode?.channelFunctions.map( (channelFunction, index) => {
            return <ChannelFunctionSlider 
                chF={channelFunction}
                key={`${selectedFixture?.uuid}_${index}`}
            />
        })}
    </>;
}

type ChannelFunctionSliderProps = {
    chF: ChannelFunction;
}

const ChannelFunctionSlider = ({ chF }: ChannelFunctionSliderProps) => {
    console.log(`rendering `, chF)

    const [value, setValue] = useState(0);
    const labelStepSize = useMemo(() => {
        const numberOfLabels = 8;
        const rangeSize = chF.dmxTo - chF.dmxFrom
        return rangeSize/numberOfLabels
    }, [chF.dmxFrom, chF.dmxTo])

    return <div>
        <p>{chF.name}</p>
        <Slider 
            min={chF.dmxFrom} 
            max={chF.dmxTo}
            value={value}
            onChange={ newValue => setValue(newValue)}
            labelStepSize={labelStepSize}
        />
    </div>
}