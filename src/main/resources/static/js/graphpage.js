$(function () {
    let tree_json = [
        {
            "text": "com.old2dimension.OCEANIA",
            "nodes": [
                {
                    "text": "bl",
                    "nodes": [
                        {
                            "text": "GraphCalculate",
                            "nodes": [
                                {
                                    "text": "getConnectedDomains()"
                                }, {
                                    "text": "getAmbiguousFuncInfos()"
                                }
                            ]

                        }, {
                            "text": "PathBL",
                            "nodes": [
                                {
                                    "text": "findPath()"
                                }
                            ]
                        }
                        , {
                            "text": "UserBL",
                            "nodes": [
                                {
                                    "text": "getAllUser()"
                                }, {
                                    "text": "login()"
                                }, {
                                    "text": "signUp()"
                                },
                            ]
                        }
                    ]
                }, {
                    "text": "blImpl",
                    "nodes": [
                        {
                            "text": "GraphCalculateImpl",
                            "nodes": [
                                {
                                    "text": "&lt;init&gt;()"
                                }, {
                                    "text": "getConnectedDomains()"
                                }, {
                                    "text": "getAmbiguousFuncInfos()"
                                }, {
                                    "text": "initializeGraph()"
                                }, {
                                    "text": "str2Vertex()"
                                }, {
                                    "text": "calculateCloseness()"
                                }, {
                                    "text": "filterByWeights()"
                                }, {
                                    "text": "findEdge()"
                                }, {
                                    "text": "generateDomain()"
                                }
                            ]

                        }, {
                            "text": "PathBLImpl",
                            "nodes": [
                                {
                                    "text": "findPath()"
                                }
                            ]
                        }
                        , {
                            "text": "UserBL",
                            "nodes": [
                                {
                                    "text": "getAllUser()"
                                }, {
                                    "text": "login()"
                                }, {
                                    "text": "signUp()"
                                },
                            ]
                        }
                    ]
                },
            ]
        }];
    $('#tree').treeview({
        data: tree_json,
        backColor: "#f8f9fa",
        color: "#000000",
        showBorder: false,
        expandIcon: "fa fa-caret-right",
        collapseIcon: "fa fa-caret-down",
        highlightSelected: true,
    });
    $("nav#tree").children().css("padding", 0);

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
                            'target-endpoint': 'outside-to-node',
                            'target-arrow-shape': 'vee',
                            'target-arrow-color': '#5B8FF9',
                            'target-arrow-fill': 'filled',
                            'arrow-scale': 2
                        }
                    },
                    {
                        selector: 'node[label]',
                        style: {
                            'font-family': 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace',
                        }
                    },
                ],

                layout: {
                    // name: 'concentric',
                    // minNodeSpacing: 50,
                    // concentric: function (node) {
                    //     return node.degree();
                    // },
                    // levelWidth: function (nodes) { // the letiation of concentric values in each level
                    //     return 0.5;
                    // },

                    // name: 'cose',
                    // animate: true,
                    // nodeDimensionsIncludeLabels: true,
                    // padding: 100

                    name: 'fcose'
                }

            });
        },
        error: function (err) {
            console.log(err);
        }
    });
});