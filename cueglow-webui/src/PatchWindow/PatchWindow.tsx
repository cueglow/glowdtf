import { Alignment, Button, Navbar, NavbarGroup, NavbarHeading, Tab, Tabs, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps, Router, useLocation, useNavigate } from "@reach/router";
import { useMemo } from "react";
import { bpVariables } from "src/BlueprintVariables/BlueprintVariables";
import { HotkeyHint, LabelWithHotkey } from "src/Utilities/HotkeyHint";
import { FixturePatch } from "./FixturePatch";
import { FixtureTypes } from "./FixtureTypes";
import NewFixture from "./NewFixture";

function PatchWindow(props: RouteComponentProps) {
    

    return (
        <Router>
            <PatchTabWrapper path="/" default />
            <NewFixture path="newFixture" />
        </Router>
    );
}

function PatchTabWrapper(props: RouteComponentProps) {
    const navigate = useNavigate();
    const location = useLocation();
    const hotkeys = useMemo(() => [
        {
            combo: "esc",
            global: true,
            label: "Go Back to Main View",
            onKeyDown: () => navigate("/"),
        },
        {
            combo: "i",
            global: true,
            label: "Navigate to the Fixture Patch",
            onKeyDown: () => navigate("/patch/fixtures"),
        },
        {
            combo: "p",
            global: true,
            label: "Navigate to Fixture Types",
            onKeyDown: () => navigate("/patch/fixtureTypes"),
        },
    ], [navigate]);
    useHotkeys(hotkeys);
    return (
        <div style={{ height: "100%", }}>
            <Navbar>
                <NavbarGroup align={Alignment.LEFT}>
                    <Button icon="cross" minimal={true} onClick={() => navigate("/")}>
                        <HotkeyHint combo="Esc" />
                    </Button>
                    <NavbarHeading style={{ paddingLeft: "6vw" }}>
                        <strong>Patch</strong>
                    </NavbarHeading>
                </NavbarGroup>
                <NavbarGroup align={Alignment.CENTER} style={{ justifyContent: "center", }}>
                    <Tabs id="patchNavbar" animate={false}
                        onChange={(newTabId: string) => {
                            if (newTabId === "fixtures") {
                                navigate("/patch")
                            } else {
                                navigate("/patch/" + newTabId)
                            }
                        }}
                        selectedTabId={(() => {
                            const lastPath = location.pathname.split("/").pop();
                            if (lastPath === "patch") {
                                return "fixtures";
                            } else {
                                return lastPath;
                            }
                        })()}>
                        <Tab id="fixtures">
                            <LabelWithHotkey label="Fixtures" combo="I" />
                        </Tab>
                        <Tab id="fixtureTypes">
                            <LabelWithHotkey label="Fixture Types" combo="P" />
                        </Tab>
                        {/* horrible hack to get navbar-height to the same size as Tabs-size
                    see  https://github.com/palantir/blueprint/issues/3727 */}
                        <div style={{ height: bpVariables.ptNavbarHeight, }}></div>
                    </Tabs>
                </NavbarGroup>
            </Navbar>
            <Router>
                <FixturePatch path="/" default />
                <FixtureTypes path="fixtureTypes" />
            </Router>
        </div>);
}



export default PatchWindow;