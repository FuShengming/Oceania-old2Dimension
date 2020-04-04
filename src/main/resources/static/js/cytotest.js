$(function () {
    // const cy = cytoscape({
    //
    //     container: document.getElementById('cy_container'), // container to render in
    //
    //     elements: {
    //         nodes: [
    //             // {
    //             //     data: {id: 'n1', label: 'I'}
    //             // },
    //             // {
    //             //     data: {id: 'n2', label: 'II'}
    //             // },
    //         ],
    //
    //         edges: [
    //             // {
    //             //     data: {
    //             //         id: 'e1',
    //             //         source: 'n1',
    //             //         target: 'n2'
    //             //     }
    //             // },
    //         ],
    //     },
    //
    //     style: [ // the stylesheet for the graph
    //         {
    //             selector: 'node',
    //             style: {
    //                 'background-color': '#666',
    //                 'label': 'data(label)'
    //             }
    //         },
    //
    //         {
    //             selector: 'edge',
    //             style: {
    //                 'width': 3,
    //                 'line-color': '#ccc',
    //                 'target-arrow-color': '#ccc',
    //                 'target-arrow-shape': 'triangle'
    //             }
    //         }
    //     ],
    //
    //     layout: {
    //         name: 'grid',
    //         rows: 1
    //     }
    //
    // });

    $.ajax({
        type: "post",
        url: "/graphql",
        dataType: "json",
        data: JSON.stringify({
            query: `
            query
            {
                set_domainset(wf: [{weightName :"closeness",weightValue: 0.15}])
                {
                    domains
                    {
                        id
                        vertices
                        {
                            id
                            funcName
                        }
                        edges
                        {
                            id
                            start
                            {
                                id
                            }
                            end
                            {
                                id
                            }
                            weights
                            {
                                weightValue
                            }
                        }
                    }
                }
            }`
        }),
        success: function (data) {
            console.log(data);
            let graphData = {
                nodes: [],
                edges: [],
            };
            data.data.set_domainset.domains[0].vertices.forEach(function (vertex) {
                graphData.nodes.push({
                    data: {
                        id: 'n' + vertex.id.toString(),
                        label: vertex.funcName,
                    }
                });
            });
            data.data.set_domainset.domains[0].edges.forEach(function (edge) {
                graphData.edges.push({
                    data: {
                        id: 'e' + edge.id.toString(),
                        source: 'n' + edge.start.id.toString(),
                        target: 'n' + edge.end.id.toString(),
                        closeness: edge.weights[0].weightValue
                    }
                });
            });
            const cy = cytoscape({

                container: document.getElementById('cy_container'), // container to render in

                elements: {
                    nodes: graphData.nodes,
                    edges: graphData.edges,
                },

                style: [ // the stylesheet for the graph
                    {
                        selector: 'node',
                        style: {
                            'background-color': '#9EC9FF',
                            'label': 'data(label)',
                            'border-width': 1,
                            'border-color': '#5B8FF9'
                        }
                    },
                    {
                        selector: 'edge',
                        style: {
                            'width': 3,
                            'opacity': 'data(closeness)',
                            'line-color': '#5B8FF9',
                            'curve-style': 'straight',
                            'target-endpoint':'outside-to-node',
                            'target-arrow-shape': 'vee',
                            'target-arrow-color': '#5B8FF9',
                            'target-arrow-fill': 'filled',
                            'arrow-scale': 2
                        }
                    },
                ],

                layout: {
                    name: 'concentric',
                    minNodeSpacing: 50,
                    concentric: function( node ){
                        return node.degree();
                    },
                    levelWidth: function( nodes ){ // the letiation of concentric values in each level
                        return 0.5;
                    },

                    // name: 'cose',
                    // animate: true,
                    // nodeDimensionsIncludeLabels: true,
                    // padding: 100
                }

            });
            // cy.startBatch();
            // cy.endBatch();
            // cy.data('elements',data.data.get_domainset.domains[0]);
            // console.log(cy.data('elements'));
            // data.data.get_domainset.domains[0].vertices.forEach(function (vertex) {
            //     graphData.nodes.push({
            //         id: vertex.id.toString(),
            //         label: vertex.funcName,
            //     });
            // });
            // data.data.get_domainset.domains[0].edges.forEach(function (edge) {
            //     graphData.edges.push({
            //         source: edge.start.id.toString(),
            //         target: edge.end.id.toString(),
            //     });
            // });
            // console.log(graphData);
            // graph.data(graphData); // 加载数据
            // graph.render(); // 渲染
        },
        error: function (err) {
            console.log(err);
        }
    });
});