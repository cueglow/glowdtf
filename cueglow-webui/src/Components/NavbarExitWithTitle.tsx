import { Alignment, Button, NavbarGroup, NavbarHeading, useHotkeys } from "@blueprintjs/core";
import { useNavigate } from "@reach/router";
import { useMemo } from "react";
import { HotkeyHint } from "src/Components/HotkeyHint";


export function NavbarExitWithTitle(props: { title: string; exitPath: string; }) {
    const navigate = useNavigate();

    const hotkeys = useMemo(() => [
        {
            combo: "esc",
            global: true,
            label: "Go Back",
            onKeyDown: () => navigate(props.exitPath),
        },
    ], [navigate, props.exitPath]);
    useHotkeys(hotkeys);

    return <NavbarGroup align={Alignment.LEFT}>
        <Button icon="cross" minimal={true} onClick={() => navigate(props.exitPath)}>
            <HotkeyHint combo="Esc" />
        </Button>
        <NavbarHeading style={{ paddingLeft: "6vw" }}>
            <strong>{props.title}</strong>
        </NavbarHeading>
    </NavbarGroup>;
}
