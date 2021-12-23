import React, { FunctionComponent } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import styled from "styled-components/macro";

const Wrapper = styled.div<{growLeft?: number}>`
    position: absolute;
    top: ${bp.vars.ptNavbarHeight};
    bottom: 0px;
    width: 100%;
    padding: ${bp.vars.ptGridSize};
    padding-right: 0;
    display: flex;
    flex-direction: row;

    div:first-child {
        flex-grow: ${props => props.growLeft ?? 1};
    }
`

const Child = styled.div`
    min-width: 0;
    flex-basis: 0;
    flex-grow: 1;
    padding-right: ${bp.vars.ptGridSize};
    overflow: auto;
`

export type LeftRightSplitProps = {
    growLeft?: number;
}

/**
 * Top-level Element that evenly distribute the children from left to right with
 * flexbox.
 */
export const LeftRightSplit: FunctionComponent<LeftRightSplitProps> = ({ children, growLeft }) => {
    return (
        <Wrapper growLeft={growLeft}>
            {React.Children.map(children, child => {
                return <Child>
                    {child}
                </Child>
            })}
        </Wrapper>
    )
}