import { Alignment, Button, Navbar, NavbarGroup, NavbarHeading, Tab, Tabs, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps, Router, useLocation, useNavigate } from "@reach/router";
import { useMemo } from "react";
import { bpVariables } from "src/BlueprintVariables/BlueprintVariables";
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

function PatchTabWrapper(this: any, props: RouteComponentProps) {
    const navigate = useNavigate();
    const location = useLocation();
    const hotkeys = useMemo(() => [
        {
            combo: "esc",
            global: true,
            label: "Go Back to Main View",
            onKeyDown: () => navigate("/"),
        }
    ], [navigate]);
    useHotkeys(hotkeys);
    return (
        <div style={{ height: "100%", }}>
            <Navbar>
                <NavbarGroup align={Alignment.LEFT}>
                    <Button text={<kbd>Esc</kbd>}
                        icon="cross" minimal={true} onClick={() => navigate("/")} />
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
                        defaultSelectedTabId={(() => {
                            const lastPath = location.pathname.split("/").pop();
                            if (lastPath === "patch") {
                                return "fixtures";
                            } else {
                                return lastPath;
                            }
                        })()}>
                        <Tab id="fixtures" title="Fixtures" />
                        <Tab id="fixtureTypes" title="Fixture Types" />
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