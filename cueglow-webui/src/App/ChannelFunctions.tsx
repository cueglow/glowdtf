import { FunctionComponent } from "react";
import { PatchFixture } from "src/Types/Patch";
import { } from "styled-components/macro";

export const ChannelFunctions: FunctionComponent<{ selectedFixture: PatchFixture | null }> = ({ selectedFixture }) => {
    return <>
        <h3 css={`margin-top: 0;`}>Channel Functions</h3>
        {selectedFixture?.fid} {selectedFixture?.name}
    </>;
}
