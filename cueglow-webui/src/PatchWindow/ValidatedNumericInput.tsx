import { FormGroup, NumericInput } from '@blueprintjs/core';
import { Tooltip2 } from '@blueprintjs/popover2';
import React from 'react';
import { Control, Controller } from 'react-hook-form';

export function ValidatedNumericInput(
    props: {
        name: string, control: Control, defaultValue?: number, label?: string,
        id?: string, min?: number, max?: number, tabIndex?: number, onEscape?: ()=>void,
    }
) {
    return <Controller name={props.name} control={props.control} defaultValue={props.defaultValue}
        render={({ field, fieldState }) =>
            <FormGroup label={props.label} labelFor={props.id}>
                <Tooltip2
                    /* need to provide some content at all times, otherwise Tooltip
                    will unmount and once it re-renders the numeric input will reset
                    to default value  */
                    content={fieldState.error?.message ?? "-"}
                    isOpen={fieldState.invalid}
                    enforceFocus={false}
                    autoFocus={false}
                    placement="right"
                    intent="danger"
                >
                    <NumericInput
                        defaultValue={props.defaultValue}
                        min={props.min} max={props.max}
                        minorStepSize={null}
                        intent={fieldState.invalid ? "danger" : "none"}
                        onValueChange={field.onChange}
                        onBlur={field.onBlur}
                        name={field.name}
                        selectAllOnFocus selectAllOnIncrement
                        id={props.id}
                        tabIndex={props.tabIndex}
                        onKeyDown={(e) => {
                            e.nativeEvent.key === "Escape" && props.onEscape && props.onEscape()
                            return
                        }}
                        />
                </Tooltip2>
            </FormGroup>} />;
}
