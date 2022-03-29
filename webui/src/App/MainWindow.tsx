import { Alignment, Button, Classes, Dialog, Navbar, NavbarGroup, NavbarHeading, useHotkeys } from "@blueprintjs/core";
import { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { LeftRightSplit } from "src/Components/LeftRightSplit";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchFixture } from "src/Types/Patch";
import { ChannelFunctions } from "./ChannelFunctions";
import { FixtureSelection } from "./FixtureSelection";

export function MainWindow() {
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

    const [isDialogOpen, setIsDialogOpen] = useState(false);

    return (
        <>
            <Navbar>
                <NavbarGroup>
                    <NavbarHeading>
                        GlowDTF
                    </NavbarHeading>
                </NavbarGroup>
                <NavbarGroup align={Alignment.RIGHT}>
                    <Button minimal={true} icon="th" onClick={() => navigate("patch")} >
                        <LabelWithHotkey label="Patch" combo={["⇧", "P"]} />
                    </Button>
                    <Button minimal={true} icon="cross" onClick={() => setIsDialogOpen(true)} >
                        Close
                    </Button>
                </NavbarGroup>
            </Navbar>
            <LeftRightSplit>
                <FixtureSelection setSelectedFixture={setSelectedFixture} />
                <ChannelFunctions selectedFixture={selectedFixture} />
            </LeftRightSplit>
            <Dialog
                title={`Close Confirmation`}
                isOpen={isDialogOpen}
                canEscapeKeyClose={true}
                canOutsideClickClose={true}
                onClose={(e) => {
                    e.nativeEvent.stopImmediatePropagation()
                    setIsDialogOpen(false)
                }}
                className="bp3-dark"
            >
                <div className={Classes.DIALOG_BODY}>
                    <p>Are you sure you want to close GlowDTF?</p>
                    <p><strong>All patched Fixtures and Fixtures Types will be lost!</strong></p>
                </div>
                <div className={Classes.DIALOG_FOOTER}>
                    <div className={Classes.DIALOG_FOOTER_ACTIONS}>
                        <Button
                            text="Cancel"
                            onClick={() => setIsDialogOpen(false)}
                            autoFocus
                        />
                        <Button
                            text="Close"
                            intent="danger"
                            onClick={() => {
                                const msg = new ClientMessage.Shutdown();
                                connectionProvider.send(msg);
                                window.close(); 
                                // TODO sometimes does not fire when WebSocket connection is dropped first
                            }}
                            data-cy="confirm_fixture_type_remove_button"
                        />
                    </div>
                </div>
            </Dialog>
        </>
    );
}