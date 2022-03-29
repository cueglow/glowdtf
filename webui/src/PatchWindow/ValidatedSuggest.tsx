import { FormGroup, InputGroupProps2, MenuItem } from "@blueprintjs/core";
import { Tooltip2 } from "@blueprintjs/popover2";
import { IItemRendererProps, Suggest, SuggestProps } from "@blueprintjs/select";

export function ValidatedSuggest<T>(props:
    Omit<SuggestProps<T>, "itemRenderer" | "popoverProps" | "resetOnClose" | "noResults" | "inputProps"> &
    { errorMessage: string, isOpen: boolean, keyRenderer: (item: T) => string, inputProps: Omit<InputGroupProps2, "intent" | "id">, id: string, label: string }
) {
    const { errorMessage, isOpen, inputProps, keyRenderer, id, label, ...other } = props;

    // error Message musn't be empty, otherwise Tooltip will unmount itself and its child
    const paddedErrorMessage = errorMessage || "-"

    return <Tooltip2
        /* must provide default content, otherwise will unmount child  */
        content={paddedErrorMessage}
        isOpen={isOpen}
        enforceFocus={false}
        autoFocus={false}
        placement="right"
        intent="danger">
        <FormGroup label={label} labelFor={id}>
            <Suggest
                {...other}

                itemRenderer={defaultSuggestItemRenderer(props.inputValueRenderer, keyRenderer)}
                popoverProps={{ minimal: true, }}
                resetOnClose={true}
                noResults={<MenuItem disabled={true} text="No results." />}
                inputProps={{
                    intent: isOpen ? "danger" : "none",
                    id: id,
                    ...inputProps,
                }}
            />
        </FormGroup>
    </Tooltip2>;
}

const defaultSuggestItemRenderer = <T,>(inputValueRenderer: (item: T) => string, keyRenderer: (item: T) => string) =>
    (item: T, { handleClick, modifiers, query }: IItemRendererProps) => {
        if (!modifiers.matchesPredicate) {
            return null;
        }
        return (
            <MenuItem
                active={modifiers.active}
                disabled={modifiers.disabled}
                onClick={handleClick}
                text={highlightText(inputValueRenderer(item), query)}
                key={keyRenderer(item)} />
        );
    }

function highlightText(text: string, query: string) {
    let lastIndex = 0;
    const words = query
        .split(/\s+/)
        .filter(word => word.length > 0)
        .map(escapeRegExpChars);
    if (words.length === 0) {
        return [text];
    }
    const regexp = new RegExp(words.join("|"), "gi");
    const tokens: React.ReactNode[] = [];
    while (true) {
        const match = regexp.exec(text);
        if (!match) {
            break;
        }
        const length = match[0].length;
        const before = text.slice(lastIndex, regexp.lastIndex - length);
        if (before.length > 0) {
            tokens.push(before);
        }
        lastIndex = regexp.lastIndex;
        tokens.push(<strong key={lastIndex}>{match[0]}</strong>);
    }
    const rest = text.slice(lastIndex);
    if (rest.length > 0) {
        tokens.push(rest);
    }
    return tokens;
}

function escapeRegExpChars(text: string) {
    return text.replace(/([.*+?^=!:${}()|[\]/\\])/g, "\\$1");
}