import { Alignment, Navbar, NavbarGroup, Tab, Tabs, useHotkeys } from "@blueprintjs/core";
import { useMemo } from "react";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { NavbarExitWithTitle } from "../Components/NavbarExitWithTitle";
import { FixturePatch } from "./FixturePatch";
import { FixtureTypes } from "./FixtureTypes";
import { NewFixtureWrapper } from "./NewFixture";

export function PatchWindow() {
    return (
        <Routes>
            <Route path="/*" element={<PatchTabWrapper />} />
            <Route path="newFixture" element={<NewFixtureWrapper />} />
        </Routes>
    );
}

export function PatchTabWrapper() {
    const navigate = useNavigate();

    const location = useLocation();

    const hotkeys = useMemo(() => [
        {
            combo: "i",
            global: true,
            label: "Navigate to the Fixture Patch",
            onKeyDown: () => document.body.classList.contains("bp3-overlay-open") || navigate("/patch/fixtures"),
        },
        {
            combo: "p",
            global: true,
            label: "Navigate to Fixture Types",
            onKeyDown: () => document.body.classList.contains("bp3-overlay-open") || navigate("/patch/fixtureTypes"),
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
            <Routes>
                <Route path="/*" element={<FixturePatch />} />
                <Route path="fixtureTypes" element={<FixtureTypes />} />
            </Routes>
        </div>);
}
