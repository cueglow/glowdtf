import React, { FunctionComponent } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import styled from "styled-components/macro";

const Wrapper = styled.div`
    position: absolute;
    top: ${bp.vars.ptNavbarHeight};
    bottom: 0px;
    width: 100%;
    padding: ${bp.vars.ptGridSize};
    padding-right: 0;
    display: flex;
    flex-direction: row;
`

const Child = styled.div`
    min-width: 0;
    flex-basis: 0;
    flex-grow: 1;
    margin-right: ${bp.vars.ptGridSize};
    overflow: auto;
`

/**
 * Top-level Element that evenly distribute the children from left to right with
 * flexbox.
 */
export const LeftRightSplit: FunctionComponent = ({ children }) => {
    return (
        <Wrapper>
            {React.Children.map(children, child => {
                return <Child>
                    {child}
                </Child>
            })}
        </Wrapper>
    )
}