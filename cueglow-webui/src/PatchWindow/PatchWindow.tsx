import { Alignment, Button, Navbar, NavbarGroup, NavbarHeading, Tab, Tabs } from "@blueprintjs/core";
import { RouteComponentProps, Router, useLocation, useNavigate } from "@reach/router";
import React from "react";
import { useHotkeys } from "react-hotkeys-hook";
import { FixturePatch } from "./FixturePatch";
import { FixtureTypes } from "./FixtureTypes";
import NewFixture from "./NewFixture";

// Import SASS-variables from blueprint.js
/* eslint import/no-webpack-loader-syntax: off */
const bp = require('sass-extract-loader!@blueprintjs/core/lib/scss/variables.scss');

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
    useHotkeys('esc', () => { navigate("/"); });
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
                        <div style={{ height: bp.global["$pt-navbar-height"].value, }}></div>
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