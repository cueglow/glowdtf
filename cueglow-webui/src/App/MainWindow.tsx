import { Alignment, Button, Navbar, NavbarGroup, NavbarHeading, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps, useNavigate } from "@reach/router";
import { useMemo } from "react";
import { LabelWithHotkey } from "src/Components/HotkeyHint";

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

    return (
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
    );
}