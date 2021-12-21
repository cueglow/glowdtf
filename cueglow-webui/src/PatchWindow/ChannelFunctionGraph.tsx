import { Button, Dialog } from "@blueprintjs/core";
import Cytoscape, { Core, ElementDefinition } from 'cytoscape';
import fcose from 'cytoscape-fcose';
import { useRef, useState } from "react";
import CytoscapeComponent from "react-cytoscapejs";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { DmxMode } from "src/Types/FixtureType";
import { } from 'styled-components/macro';

// register fcose layout algorithm
Cytoscape.use(fcose)

export type ChannelFunctionGraphProps = {
    dmxMode: DmxMode;
}

export const ChannelFunctionGraphWrapper = (props: ChannelFunctionGraphProps & {fixtureTypeName: string}) => {
    const [isOpen, setIsOpen] = useState(false);
    return <>
        <Button onClick={() => setIsOpen(true)}>Show ModeMaster Dependencies</Button>
        <Dialog title="ModeMaster Dependency Graph"
        isOpen={isOpen}
        onClose={() => setIsOpen(false)}
        transitionDuration={0}
        className="bp3-dark"
        style={{width: "95vw", height: "95vh", paddingBottom: "0",}}
        >
            <div css={`
                padding: ${bp.ptGridSizePx}px;
                width: 100%;
                height: 100%;
                display: flex;
                flex-direction: column;
            `}>
                <p css={`flex-grow: 0;`}>{`${props.fixtureTypeName}, ${props.dmxMode.name}`}</p>
                <ChannelFunctionGraph {...props}/>
            </div>
        </Dialog>
    </>
}

export const ChannelFunctionGraph = ({ dmxMode }: ChannelFunctionGraphProps) => {
    // TODO optimize default layout
    // TODO Make it look a bit nicer and refactor styles

    const cy = useRef<Core|null>(null);

    const deps = dmxMode.channelFunctionDependencies

    // first, get the channels of which there are ChannelFunctions in the dependency graph
    const channelInds = new Set<number>();

    deps.nodes.map((n) => parseInt(n.id)).forEach((chFInd) => {
        const chF = dmxMode.channelFunctions[chFInd]
        const chInd = chF.multiByteChannelInd
        channelInds.add(chInd)
    })

    // iterate over channels to find relevant channel functions and layout constraints
    const chFInds: number[] = []
    const placementConstraints: RelativePlacementConstraint[] = []
    const alignConstraints: AlignmentConstraint = {vertical: [[]]}

    channelInds.forEach((chInd) => {
        // channel functions
        const channel = dmxMode.multiByteChannels[chInd]
        chFInds.push(...channel.channelFunctionIndices)
        // placement constraints: All channel functions from a channel in order of their index
        channel.channelFunctionIndices.slice(1).forEach((chFInd) => {
            placementConstraints.push({ 
                top: (chFInd-1).toString(),
                bottom: chFInd.toString(),
                gap: 10,
            })
        })
        // alignment constraints: All channel functions from a channel vertically aligned
        channel.channelFunctionIndices.forEach((chFInd) => {
            // vertical was initialized above, so can't be undefined
            alignConstraints.vertical![alignConstraints.vertical!.length - 1].push(chFInd.toString())
        })
        alignConstraints.vertical?.push([])
    })
    // remove last element because it's empty
    alignConstraints.vertical?.pop()

    // assemble nodes - first normal channel functions
    const nodes: ElementDefinition[] = chFInds.map((chFInd) => {
        const chF = dmxMode.channelFunctions[chFInd]
        return {
            data: {
                id: chFInd.toString(),
                label: `${chF.name} (${chF.dmxFrom}-${chF.dmxTo})`,
                parent: dmxMode.multiByteChannels[chF.multiByteChannelInd].name
            },
            grabbable: false,
        }
    })

    // add a compound node for each channel to be referenced as parent
    channelInds.forEach((channelInd) => {
        const channel = dmxMode.multiByteChannels[channelInd]
        nodes.push({
            data: {
                id: channel.name,
            }
        })
    })

    // construct elements with edges from dependency graph
    const elements = CytoscapeComponent.normalizeElements({
        nodes: nodes,
        edges: deps.edges.map((edge) => {
            return {
                data: {
                    source: edge.source,
                    target: edge.target,
                    label: `${edge.modeFromClipped}-${edge.modeToClipped}`
                },
            }
        }),
    })

    const stylesheet = [
        {
            selector: 'node',
            style: {
                opacity: 1,
                width: 250,
                height: 40,
                backgroundOpacity: 0,
                shape: 'rectangle',
                color: "white",
                textValign: "center",
                textHalign: "center",
                textWrap: "wrap",
                textMaxWidth: 245,
                //textBorderStyle: "solid",
                //textBorderColor: "white",
                //textBorderWidth: "3",
                //textBorderOpacity: "1",
                borderWidth: 3,
                borderStyle: "solid",
                borderColor: "white",
                //textBackgroundPadding: 100,
            }
        },
        {
            selector: 'node[label]',
            style: {
                content: 'data(label)',
            }
        },
        {
            selector: 'node:parent',
            style: {
                padding: 20,
            }
        },
        {
            selector: 'edge',
            style: {
                //targetEndpoint: "90deg",
                //sourceEndpoint: "270deg",
                curveStyle: "bezier",
                width: 5,
                targetArrowShape: "triangle",
                targetArrowColor: "white",
                //targetArrowScale: 10,
                label: "data(label)",
                color: "white",
                textBackgroundColor: "black",
                textBackgroundPadding: 1,
                textBackgroundOpacity: 1,
            }
        }
    ];

    const layout = { 
        name: 'fcose', 
        animate: false, 
        relativePlacementConstraint: placementConstraints,
        alignmentConstraint: alignConstraints,
        quality: "proof",
        randomize: false,  
    }


    return <CytoscapeComponent
        elements={elements}
        stylesheet={stylesheet}
        // Custom Options of fcose aren't in the typings for layout options, so we have to use any
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        layout={layout as any}
        css={`
            flex-grow: 10;
        `}
        cy={(newCy) => cy.current = newCy}
    />
}

type RelativePlacementConstraint = {
    top: string;
    bottom: string;
    gap?: number;
} | {
    left: string;
    right: string;
    gap?: number
}

type AlignmentConstraint = {
    vertical?: string[][];
    horizontal?: string[][];
}