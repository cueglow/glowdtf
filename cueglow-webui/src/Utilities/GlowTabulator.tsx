import { ReactTabulator } from "react-tabulator";
import { IProps } from "react-tabulator/lib/ConfigUtils";

/**
 * More strongly typed wrapper around ReactTabulator
 */
export function GlowTabulator(props: GlowTabulatorProps) {
    return (
        <ReactTabulator {...props}/>
    )
}

/**
 * Overwrite some any-typed properties with types from @types/tabulator-tables
 */
 interface GlowTabulatorProps extends IProps {
    options?: Tabulator.Options;
    data: unknown[];
    columns: Tabulator.ColumnDefinition[];
}