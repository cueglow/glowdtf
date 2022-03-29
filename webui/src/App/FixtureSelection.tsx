import { FunctionComponent, useContext, useMemo } from "react";
import { GlowTabulator } from "src/Components/GlowTabulator";
import { PatchContext } from "src/ConnectionProvider/PatchDataProvider";
import { PatchFixture } from "src/Types/Patch";
import { } from "styled-components/macro";

export const FixtureSelection: FunctionComponent<{ setSelectedFixture: (arg0: PatchFixture) => void }> = ({ setSelectedFixture }) => {
    return <div css={`
        display: flex;
        flex-direction: column;
        height: 100%;
    `}>
        <h3 className="bp3-heading" css={`margin-top: 0;`}>Fixtures</h3>
        <div css={`
            flex-grow: 1;
            min-height: 0;
        `}>
            <FixtureSelectionTable
                rowSelectionChanged={
                    (selectedData) => setSelectedFixture(selectedData[0])
                }
            />
        </div>
    </div>;
}

function FixtureSelectionTable(props: { rowSelectionChanged: (selectedData: PatchFixture[]) => void }) {
    const patchData = useContext(PatchContext);

    const columns = useMemo(() => [
        { field: "fid", title: "FID" },
        { field: "name", title: "Name" },
    ], []);

    return (
        <>
            <GlowTabulator
                data={patchData.fixtures}
                columns={columns}
                height="100%"
                layout="fitDataStretch"
                selectable={1}
                onRowSelectionChanged={props.rowSelectionChanged}
            />
        </>
    );
}