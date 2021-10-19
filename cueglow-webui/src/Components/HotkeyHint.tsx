import { Fragment } from "react"
import { bp } from "src/BlueprintVariables/BlueprintVariables"
import styled from 'styled-components/macro'

/**
 * Render a Label with Hotkey Hint behind
 *
 * @param title Label Text
 * @param hotkey Either a String for the Hotkey, or an array of keys to be
 * pressed simultaneously or a 2D array for key chords
 * 
 * @example 
 * // Press Ctrl+K, followed by R
 * hotkey = [["Ctrl", "K"], ["R"]]
 */
export function LabelWithHotkey(props: {label: string, combo?: string|string[]|string[][]}) {
    return <>{props.label} <HotkeyHint combo={props.combo} /></>
}

export function HotkeyHint(props: {combo?: string|string[]|string[][]}) {
    const combo: string[][] = normalizeHotkeyCombo(props.combo)
    const hotkeyElements = combo.map((combo, index) => {
        const comboElements = combo.map((key, index) => {
            if (index === 0) {
                return <HotkeyKbd key={key+index}>{key}</HotkeyKbd>
            } else {
                return <Fragment key={key+index}>+<HotkeyKbd>{key}</HotkeyKbd></Fragment>
            }
        })
        if (index === 0) {
            return <Fragment key={combo.join()+index}>
                {comboElements}
            </Fragment>
        } else {
            return <Fragment key={combo.join()+index}>
                <NonBreakingSpace/><NonBreakingSpace/>{comboElements}
            </Fragment>
        }
    })
    return <>{hotkeyElements}</>
}

const HotkeyKbd = styled.kbd`
    display: inline-block;
    font-family: ${bp.vars.ptFontFamily};
    font-size: 85%;
    border: 1px solid;
    border-radius: 3px;
    padding: 1px 3px;
    position: relative;
    bottom: 0.3px;
    line-height: 1.15;
`


export function NonBreakingSpace() {
    return <>{"\u00A0"}</>
}

export function normalizeHotkeyCombo(hotkey?: string|string[]|string[][]): string[][] {
    if (typeof hotkey === "string") {
        return [[hotkey]]
    } else if (typeof hotkey === "undefined") {
        return [[]]
    } else if (typeof hotkey === "object") {
        if (typeof hotkey[0] === "string") { // 1-level array
            hotkey = hotkey as string[]
            return [hotkey]
        } else if (typeof hotkey[0] === "undefined") { // empty 1-level array
            return [[]]
        }
    }
    hotkey = hotkey as string[][]
    return hotkey // 2-level array already
}