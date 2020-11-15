import { Alignment, Button, Navbar, NavbarGroup, NavbarHeading } from "@blueprintjs/core";
import { RouteComponentProps, useNavigate } from "@reach/router";
import React from "react";
import { useHotkeys } from "react-hotkeys-hook";



function MainWindow(props: RouteComponentProps) {
    const navigate = useNavigate();
    useHotkeys('p', () => { navigate("patch"); });

    return (
        <Navbar>
            <NavbarGroup>
                <NavbarHeading>
                    CueGlow
                </NavbarHeading>
            </NavbarGroup>
            <NavbarGroup align={Alignment.RIGHT}>
                <Button text={<span>Patch <kbd>P</kbd></span>} minimal={true} icon="th" onClick={() => navigate("patch")} />
            </NavbarGroup>
        </Navbar>
    );
}



export default MainWindow;