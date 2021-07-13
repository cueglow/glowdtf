
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
                return <kbd className="hotkey-hint">{key}</kbd>
            } else {
                return <>+<kbd className="hotkey-hint">{key}</kbd></>
            }
        })
        if (index === 0) {
            return comboElements
        } else {
            return <> <NonBreakingSpace /> {comboElements} </>
        }
    })
    return <>{hotkeyElements}</>
}

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