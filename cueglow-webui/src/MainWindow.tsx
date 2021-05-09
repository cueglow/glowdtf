import { Alignment, Button, Navbar, NavbarGroup, NavbarHeading, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps, useNavigate } from "@reach/router";
import { useMemo } from "react";



function MainWindow(props: RouteComponentProps) {
    const navigate = useNavigate();
    const hotkeys = useMemo(() => [
        {
            combo: "shift+p",
            global: true,
            label: "Open Patch View",
            onKeyDown: () => navigate("patch"),
        }
    ], []);
    useHotkeys(hotkeys);

    return (
        <Navbar>
            <NavbarGroup>
                <NavbarHeading>
                    CueGlow
                </NavbarHeading>
            </NavbarGroup>
            <NavbarGroup align={Alignment.RIGHT}>
                <Button text={<span>Patch <kbd>⇧</kbd>+<kbd>P</kbd></span>} minimal={true} icon="th" onClick={() => navigate("patch")} />
            </NavbarGroup>
        </Navbar>
    );
}



export default MainWindow;