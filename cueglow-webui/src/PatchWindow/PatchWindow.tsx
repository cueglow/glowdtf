import { Alignment, Navbar, NavbarGroup, Tab, Tabs, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps, Router, useLocation, useNavigate } from "@reach/router";
import { useMemo } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { FixturePatch } from "./FixturePatch";
import { FixtureTypes } from "./FixtureTypes";
import { NavbarExitWithTitle } from "../Components/NavbarExitWithTitle";
import { NewFixtureWrapper } from "./NewFixture";

export function PatchWindow(props: RouteComponentProps) {
    return (
        <Router>
            <PatchTabWrapper path="/" default />
            <NewFixtureWrapper path="newFixture" />
        </Router>
    );
}

export function PatchTabWrapper(props: RouteComponentProps) {
    const navigate = useNavigate();

    const location = useLocation();

    const hotkeys = useMemo(() => [
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
                <NavbarExitWithTitle title="Patch" exitPath="/" />
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
                        <div style={{ height: bp.vars.ptNavbarHeight, }}></div>
                    </Tabs>
                </NavbarGroup>
            </Navbar>
            <Router>
                <FixturePatch path="/" default />
                <FixtureTypes path="fixtureTypes" />
            </Router>
        </div>);
}
