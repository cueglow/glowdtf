import { useEffect, useRef, useState } from "react";
import { Tabulator, TabulatorFull } from "tabulator-tables";

interface GlowTabulatorProps extends Tabulator.Options {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    onRowSelectionChanged?: (selected: any[]) => void;
    onCellEdited?: (cell: Tabulator.CellComponent) => void;
    onCellEditCancelled?: (cell: Tabulator.CellComponent) => void;
    onValidationFailed?: (
        cell: Tabulator.CellComponent, 
        value: unknown, 
        validators: Tabulator.Validator[]
    ) => void;
}

/**
 * Our own, fragile React Wrapper around Tabulator.
 *
 * React-Tabulator doesn't work well with Tabulator 5 yet, and it has issues
 * with peer dependency versions. So I created my own wrapper. Note that none of
 * the properties will update the Table other than changes to data, so be sure
 * to pass in the right props the first time. 
 *
 * The typings aren't up to version 5 yet either, so we use patch-package to
 * slightly adjust @types/tabulator-tables. 
 * 
 * We also don't support all events yet, only the four we need. 
 */
export function GlowTabulator(props: GlowTabulatorProps) {
    const container = useRef<HTMLDivElement>(null);
    const t = useRef<{ tab: Tabulator | null }>({ tab: null });
    // keep track whether the table is built to trigger a data update
    const [built, setBuilt] = useState(false);

    // create table
    useEffect(() => {
        if (container.current) {
            t.current.tab = new TabulatorFull(container.current, props)
            t.current.tab.on("tableBuilt", () => {
                setBuilt(true)
            })
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    // register event handlers
    useEffect(() => {
        t.current.tab?.on("rowSelectionChanged", () => {
            const selected = t.current.tab?.getSelectedData();
            props.onRowSelectionChanged && props.onRowSelectionChanged(selected ?? [])
        })
        t.current.tab?.on("cellEdited", (cell) => {
            props.onCellEdited && props.onCellEdited(cell)
        })
        t.current.tab?.on("cellEditCancelled", (cell) => {
            props.onCellEditCancelled && props.onCellEditCancelled(cell)
        })
        t.current.tab?.on("validationFailed", (cell, value, validators) => {
            props.onValidationFailed && props.onValidationFailed(cell, value, validators)
        })
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    // update data
    useEffect(() => {
        props.data && built && t.current.tab?.replaceData(props.data)
    }, [props.data, built])

    return <div ref={container} />
}





