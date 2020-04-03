$(function () {
    //initialize cytoscape
    let cy = cytoscape();

    //treeview on the left sidebar
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

    //cytoscape container on the middle main div
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
            data.data.set_domainset.domains.forEach(function (domain) {
                graphData.nodes.push({
                    data: {
                        id: 'd' + domain.id.toString(),
                    },
                    classes: ['domain'],
                });
                let vertices = domain.vertices;
                vertices.forEach(function (vertex) {
                    graphData.nodes.push({
                        data: {
                            id: 'n' + vertex.id.toString(),
                            label: vertex.funcName,
                            parent: 'd' + domain.id.toString(),
                        },
                        classes: ['vertex'],
                    });
                });
                let edges = domain.edges;
                edges.forEach(function (edge) {
                    graphData.edges.push({
                        data: {
                            id: 'e' + edge.id.toString(),
                            source: 'n' + edge.start.id.toString(),
                            target: 'n' + edge.end.id.toString(),
                            closeness: edge.weights[0].weightValue
                        }
                    });
                });
            });
            // data.data.set_domainset.domains[0].vertices.forEach(function (vertex) {
            //     graphData.nodes.push({
            //         data: {
            //             id: 'n' + vertex.id.toString(),
            //             label: vertex.funcName,
            //         }
            //     });
            // });
            // data.data.set_domainset.domains[0].edges.forEach(function (edge) {
            //     graphData.edges.push({
            //         data: {
            //             id: 'e' + edge.id.toString(),
            //             source: 'n' + edge.start.id.toString(),
            //             target: 'n' + edge.end.id.toString(),
            //             closeness: edge.weights[0].weightValue
            //         }
            //     });
            // });
            cy = cytoscape({

                container: document.getElementById('cy_container'), // container to render in

                elements: {
                    nodes: graphData.nodes,
                    edges: graphData.edges,
                },

                style: [ // the stylesheet for the graph
                    {
                        selector: 'node.vertex',
                        style: {
                            'background-color': '#9EC9FF',
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
                            'label': 'data(label)',
                            'font-family': 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace',
                        }
                    },
                    {
                        selector: 'node.domain',
                        style: {
                            'background-opacity': 0.1,
                            'background-color': '#9EC9FF',
                            'border-color': '#9EC9FF'
                        }
                    },
                    {
                        selector: 'node.favor',
                        style: {
                            'background-color': '#f2c0ff',
                            'border-color': '#e295ec',
                        }
                    },
                    {
                        selector: 'edge.favor',
                        style: {
                            'line-color': '#e295ec',
                            'target-arrow-color': '#e295ec',
                        }
                    },
                    {
                        selector: 'node:selected',
                        style: {
                            'background-color': '#b8ffcb',
                            'border-color': '#33e17e',
                        }
                    },
                    {
                        selector: 'edge:selected',
                        style: {
                            'line-color': '#33e17e',
                            'target-arrow-color': '#33e17e',
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

                    name: 'fcose',
                    idealEdgeLength: 150,

                    stop: function () {
                        $('#loading').hide();
                    },
                }
            });

            cy.cxtmenu({
                selector: 'node.vertex.favor, edge.favor',
                commands: [
                    {
                        content: '<span class="fa fa-arrows-alt fa-2x"></span>',
                        select: function (ele) {
                            cy.$('node,edge').unselect();
                            console.log('this id', ele.id());
                            ele.select();
                            let neighborhoods = ele.neighborhood();
                            neighborhoods.forEach(function (nb) {
                                console.log('neighbor id', nb.id());
                                nb.select();
                            });
                            cy.fit(cy.$('node:selected'), $('#cy_container').height() * 0.25);
                        }
                    },

                    {
                        content: '<span style="color:#faff62;" class="fa fa-lightbulb-o fa-2x"></span>',
                        select: function (ele) {
                            ele.removeClass('favor');
                            ele.removeData('favor');
                        },
                        // enabled: false
                    },

                    {
                        content: '<span class="fa fa-bookmark fa-2x"></span>',
                        select: function (ele) {
                            console.log(ele.position());
                        }
                    }
                ],
                menuRadius: 70,
                indicatorSize: 12,
                minSpotlightRadius: 12,
            });

            cy.cxtmenu({
                selector: 'node.vertex[^favor]',
                commands: [
                    {
                        content: '<span class="fa fa-arrows-alt fa-2x"></span>',
                        select: function (ele) {
                            cy.$('node,edge').unselect();
                            console.log('this id', ele.id());
                            ele.select();
                            let neighborhoods = ele.neighborhood();
                            neighborhoods.forEach(function (nb) {
                                console.log('neighbor id', nb.id());
                                nb.select();
                            });
                            cy.fit(cy.$('node:selected'), $('#cy_container').height() * 0.25);
                        }
                    },

                    {
                        content: '<span class="fa fa-lightbulb-o fa-2x"></span>',
                        select: function (ele) {
                            ele.addClass('favor');
                            ele.data('favor', true);
                        },
                    },

                    {
                        content: '<span class="fa fa-bookmark fa-2x"></span>',
                        select: function (ele) {
                            console.log(ele.position());
                        }
                    }
                ],
                menuRadius: 70,
                indicatorSize: 12,
                minSpotlightRadius: 12,
            });

            cy.cxtmenu({
                selector: 'edge[^favor]',
                commands: [
                    {
                        content: '<span class="fa fa-arrows-alt fa-2x"></span>',
                        select: function (ele) {
                            cy.$('node,edge').unselect();
                            console.log('this id', ele.id());
                            ele.select();
                            let neighborhoods = ele.neighborhood();
                            neighborhoods.forEach(function (nb) {
                                console.log('neighbor id', nb.id());
                                nb.select();
                            });
                            cy.fit(cy.$('node:selected'), $('#cy_container').height() * 0.25);
                        }
                    },

                    {
                        content: '<span class="fa fa-lightbulb-o fa-2x"></span>',
                        select: function (ele) {
                            ele.addClass('favor');
                            ele.data('favor', true);
                            ele.source().addClass('favor');
                            ele.source().data('favor', true);
                            ele.target().addClass('favor');
                            ele.target().data('favor', true);
                        },
                        // enabled: false
                    },

                    {
                        content: '<span class="fa fa-bookmark fa-2x"></span>',
                        select: function (ele) {
                            console.log(ele.position());
                        }
                    }
                ],
                menuRadius: 70,
                indicatorSize: 12,
                minSpotlightRadius: 12,
            });

            cy.cxtmenu({
                selector: 'node.domain',
                commands: [
                    {
                        content: '<span class="fa fa-arrows-alt fa-2x"></span>',
                        select: function (ele) {
                            cy.fit(ele);
                        }
                    },

                    {
                        content: '<span class="fa fa-bookmark fa-2x"></span>',
                        select: function (ele) {
                            console.log(ele.position());
                        }
                    }
                ],
                menuRadius: 70,
                indicatorSize: 12,
                minSpotlightRadius: 12,
            });

            console.log(JSON.stringify(cy.json()).length);
        },
        error: function (err) {
            console.log(err);
        }
    });

    //buttons on the middle main div
    $("#layout-btn").on("click", function () {
        cy.fit();
    });
    //code on the right sidebar
    //labels on the right sidebar
});