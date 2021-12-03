import { Alignment, Button, Navbar, NavbarGroup, NavbarHeading, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps, useNavigate } from "@reach/router";
import { useMemo, useState } from "react";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { LeftRightSplit } from "src/Components/LeftRightSplit";
import { PatchFixture } from "src/Types/Patch";
import { ChannelFunctions } from "./ChannelFunctions";
import { FixtureSelection } from "./FixtureSelection";

export function MainWindow(props: RouteComponentProps) {
    const navigate = useNavigate();

    const hotkeys = useMemo(() => [
        {
            combo: "shift+p",
            global: true,
            label: "Open Patch View",
            onKeyDown: () => navigate("patch"),
        }
    ], [navigate]);
    useHotkeys(hotkeys);

    const [selectedFixture, setSelectedFixture] = useState<PatchFixture | null>(null);

    return (
        <>
            <Navbar>
                <NavbarGroup>
                    <NavbarHeading>
                        CueGlow
                    </NavbarHeading>
                </NavbarGroup>
                <NavbarGroup align={Alignment.RIGHT}>
                    <Button minimal={true} icon="th" onClick={() => navigate("patch")} >
                        <LabelWithHotkey label="Patch" combo={["⇧", "P"]} />
                    </Button>
                </NavbarGroup>
            </Navbar>
            <LeftRightSplit>
                <FixtureSelection setSelectedFixture={setSelectedFixture} />
                <ChannelFunctions selectedFixture={selectedFixture} />
            </LeftRightSplit>
        </>
    );
}