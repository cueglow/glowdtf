import cytoscape, { Core, ElementDefinition } from "cytoscape"
import CytoscapeComponent from "react-cytoscapejs"
import { DmxMode } from "src/Types/FixtureType"
import { } from 'styled-components/macro'
import Cytoscape from 'cytoscape';
import fcose from 'cytoscape-fcose';
import { useRef } from "react";
import { useEffect } from "react";

export type ChannelFunctionGraphProps = {
    dmxMode: DmxMode;
}

Cytoscape.use(fcose)
fcose(cytoscape)

export const ChannelFunctionGraph = ({ dmxMode }: ChannelFunctionGraphProps) => {
    // TODO Refactor this messy code below
    // TODO move to its own window
    // TODO optimize default layout
    // TODO Make it look a bit nicer...

    const cy = useRef<Core|null>(null);

    const deps = dmxMode.channelFunctionDependencies
    // first, get the channels of which there are ChannelFunctions in the dependency graph
    const channelInds = new Set<number>();
    deps.nodes.map((n) => parseInt(n.id)).forEach((chFInd) => {
        const chF = dmxMode.channelFunctions[chFInd]
        channelInds.add(chF.multiByteChannelInd)
    })

    const chFInds: number[] = []
    const constraints: RelativePlacementConstraint[] = []
    const alignConstraints: AlignmentConstraint = {vertical: [[]]}

    for (let channelInd of channelInds) {
        const channel = dmxMode.multiByteChannels[channelInd]
        chFInds.push(...channel.channelFunctionIndices)

        channel.channelFunctionIndices.slice(1).forEach((chFInd) => {
            constraints.push({ 
                top: (chFInd-1).toString(),
                bottom: chFInd.toString(),
                gap: 10,
            })
        })

        
        alignConstraints.vertical?.push([])
        channel.channelFunctionIndices.forEach((chFInd) => {
            // vertical was initialized above
            alignConstraints.vertical![alignConstraints.vertical!.length - 1].push(chFInd.toString())
        })
    }
    alignConstraints.vertical?.shift()
    console.log("alignConstraints", alignConstraints)

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

    channelInds.forEach((channelInd) => {
        const channel = dmxMode.multiByteChannels[channelInd]
        nodes.push({
            data: {
                id: channel.name,
            }
        })
    })

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
    /* const elements = [
        { data: { id: 'one', label: 'Node 1111111 1111 11111111 1111 11111' }, position: { x: 200, y: 150 } },
        { data: { id: 'two', label: 'Node 2222222' }, position: { x: 100, y: 100 } },
        {e
          data: { source: 'one', target: 'two', label: 'Edge from Node1 to Node2' }
        }
      ]*/

    //const stylesheet = cytoscape().style('node { background-color: cyan; }');
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
        relativePlacementConstraint: constraints,
        alignmentConstraint: alignConstraints,
        quality: "proof",
    }


    return <CytoscapeComponent
        elements={elements}
        stylesheet={stylesheet}
        // Custom Options of fcose aren't in the typings for layout options, so we have to use any
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        layout={layout as any}
        css={`
            height: 700px;
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