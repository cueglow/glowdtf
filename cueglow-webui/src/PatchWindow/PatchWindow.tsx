import { Alignment, Button, Navbar, NavbarGroup, NavbarHeading, Tab, Tabs } from "@blueprintjs/core";
import { RouteComponentProps, useNavigate } from "@reach/router";
import React, { useState } from "react";
import { useHotkeys } from "react-hotkeys-hook";
import { FixturePatch } from "./FixturePatch";
import { FixtureTypes } from "./FixtureTypes";

// Import SASS-variables from blueprint.js
/* eslint import/no-webpack-loader-syntax: off */
const bp = require('sass-extract-loader!@blueprintjs/core/lib/scss/variables.scss');

function PatchWindow(props: RouteComponentProps) {
    const navigate = useNavigate();
    useHotkeys('esc', () => { navigate("/"); });
    const [tabId, setTabId] = useState("fixtures");

    function handleNavbarTabChange(newTabId: string) {
        setTabId(newTabId);
    }

    return (
        <div style={{height: "100%",}}>
            <Navbar>
                <NavbarGroup align={Alignment.LEFT}>
                    <Button text={<kbd>Esc</kbd>}
                        icon="cross" minimal={true} onClick={() => navigate("/")} />
                    <NavbarHeading style={{ paddingLeft: "6vw" }}>
                        <strong>Patch</strong>
                    </NavbarHeading>
                </NavbarGroup>
                <NavbarGroup align={Alignment.CENTER} style={{ justifyContent: "center", }}>
                    <Tabs id="patchNavbar" animate={false} onChange={handleNavbarTabChange}>
                        <Tab id="fixtures" title="Fixtures" />
                        <Tab id="fixtureTypes" title="Fixture Types" />
                        {/* horrible hack to get navbar-height to the same size as Tabs-size
                    see  https://github.com/palantir/blueprint/issues/3727 */}
                        <div style={{ height: bp.global["$pt-navbar-height"].value, }}></div>
                    </Tabs>
                </NavbarGroup>
            </Navbar>
            {/* switch this over to reach router so that reloading keeps you on Fixture Types */}
            {(() => {
                switch (tabId) {
                    case "fixtures":
                        return <FixturePatch />;
                    case "fixtureTypes":
                        return <FixtureTypes />;
                }
            }) ()}
        </div>

    );
}



export default PatchWindow;