import { normalizeHotkeyCombo } from "./HotkeyHint"

it("normalizes different array-like hotkey arguments", () => {

    // one element
    expect(normalizeHotkeyCombo("abc")).toStrictEqual([["abc"]])
    expect(normalizeHotkeyCombo(["abc"])).toStrictEqual([["abc"]])
    expect(normalizeHotkeyCombo([["abc"]])).toStrictEqual([["abc"]])

    // two elements
    expect(normalizeHotkeyCombo(["abc", "def"])).toStrictEqual([["abc", "def"]])
    expect(normalizeHotkeyCombo([["abc"], ["def"]])).toStrictEqual([["abc"], ["def"]])

    // three elements
    expect(normalizeHotkeyCombo([["Ctrl", "K"], ["R"]])).toStrictEqual([["Ctrl", "K"], ["R"]])

    // empty cases
    expect(normalizeHotkeyCombo()).toStrictEqual([[]])
    expect(normalizeHotkeyCombo([])).toStrictEqual([[]])
    expect(normalizeHotkeyCombo([[]])).toStrictEqual([[]])


})