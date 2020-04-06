$(function () {
    //initialize cytoscape
    let cy = cytoscape();
    let fcose_layout = {
        name: 'fcose',
        idealEdgeLength: 150,
        stop: function () {
            $('#loading').hide();
        }
    };
    let concentric_layout = {
        name: 'concentric',
        minNodeSpacing: 50,
        concentric: function (node) {
            return node.degree();
        },
        levelWidth: function (nodes) { // the letiation of concentric values in each level
            return 0.5;
        },
        stop: function () {
            $('#loading').hide();
        }
    };
    //treeview on the left sidebar
    let remove_empty_node = function (tree) {
        console.log(tree);
        tree.nodes.forEach(function (sub_tree) {
            console.log(sub_tree);
            console.log(sub_tree.nodes.length);
            if (sub_tree.nodes.length === 0) {
                delete sub_tree.nodes;
                console.log("delete one");
            } else {
                remove_empty_node(sub_tree);
            }
        });
    };
    $.ajax({
        type: "post",
        url: "/code/getCodeStructure",
        dataType: "json",
        contentType: 'application/json',
        data: JSON.stringify({
            userId: 1,
            codeId: 1
        }),
        success: function (data) {
            let tree_json = [data.content];
            tree_json.forEach(function (tree) {
                remove_empty_node(tree);
            });
            console.log(tree_json);
            $('#tree').treeview({
                data: tree_json,
                backColor: "#f8f9fa",
                color: "#000000",
                showBorder: false,
                expandIcon: "fa fa-caret-right",
                collapseIcon: "fa fa-caret-down",
                highlightSelected: true,
            });
        },
        error: function (err) {
            console.log(err);
        }
    });
    // let tree_json = [
    //     {
    //         "text": "com.old2dimension.OCEANIA",
    //         "nodes": [
    //             {
    //                 "text": "bl",
    //                 "nodes": [
    //                     {
    //                         "text": "GraphCalculate",
    //                         "nodes": [
    //                             {
    //                                 "text": "getConnectedDomains()"
    //                             }, {
    //                                 "text": "getAmbiguousFuncInfos()"
    //                             }
    //                         ]
    //
    //                     }, {
    //                         "text": "PathBL",
    //                         "nodes": [
    //                             {
    //                                 "text": "findPath()"
    //                             }
    //                         ]
    //                     }
    //                     , {
    //                         "text": "UserBL",
    //                         "nodes": [
    //                             {
    //                                 "text": "getAllUser()"
    //                             }, {
    //                                 "text": "login()"
    //                             }, {
    //                                 "text": "signUp()"
    //                             },
    //                         ]
    //                     }
    //                 ]
    //             }, {
    //                 "text": "blImpl",
    //                 "nodes": [
    //                     {
    //                         "text": "GraphCalculateImpl",
    //                         "nodes": [
    //                             {
    //                                 "text": "&lt;init&gt;()"
    //                             }, {
    //                                 "text": "getConnectedDomains()"
    //                             }, {
    //                                 "text": "getAmbiguousFuncInfos()"
    //                             }, {
    //                                 "text": "initializeGraph()"
    //                             }, {
    //                                 "text": "str2Vertex()"
    //                             }, {
    //                                 "text": "calculateCloseness()"
    //                             }, {
    //                                 "text": "filterByWeights()"
    //                             }, {
    //                                 "text": "findEdge()"
    //                             }, {
    //                                 "text": "generateDomain()"
    //                             }
    //                         ]
    //
    //                     }, {
    //                         "text": "PathBLImpl",
    //                         "nodes": [
    //                             {
    //                                 "text": "findPath()"
    //                             }
    //                         ]
    //                     }
    //                     , {
    //                         "text": "UserBL",
    //                         "nodes": [
    //                             {
    //                                 "text": "getAllUser()"
    //                             }, {
    //                                 "text": "login()"
    //                             }, {
    //                                 "text": "signUp()"
    //                             },
    //                         ]
    //                     }
    //                 ]
    //             },
    //         ]
    //     }];
    // $('#tree').treeview({
    //     data: tree_json,
    //     backColor: "#f8f9fa",
    //     color: "#000000",
    //     showBorder: false,
    //     expandIcon: "fa fa-caret-right",
    //     collapseIcon: "fa fa-caret-down",
    //     highlightSelected: true,
    // });
    // $("nav#tree").children().css("padding", 0);

    //cytoscape container on the middle main div

    $.ajax({
        type: "post",
        url: "/graph/getGraph",
        dataType: "json",
        contentType: 'application/json',
        data: JSON.stringify({
            userId: 1,
            codeId: 1
        }),
        success: function (data) {
            console.log(data);
            let graphData = {
                nodes: [],
                edges: [],
            };
            data.content.domainSetVO.domainVOs.forEach(function (domain) {
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
                            full_info: {
                                belongPackage: vertex.belongPackage,
                                belongClass: vertex.belongClass,
                                funcName: vertex.funcName,
                                args: vertex.args,
                            }
                        },
                        classes: ['vertex'],
                    });
                });
                let edges = domain.edgeVOS;
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
            console.log(graphData);

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

                    name: 'fcose',
                    idealEdgeLength: 150,

                    stop: function () {
                        $('#loading').hide();
                    },
                }
            });

            cy.on('tap', 'node.vertex', function (event) {
                console.log('selected');
                console.log(event.target.data());
                let info = event.target.data("full_info");
                $.ajax({
                    type: "post",
                    url: "/code/getFuncCode",
                    dataType: "json",
                    contentType: 'application/json',
                    data: JSON.stringify({
                        userId: 1,
                        codeId: 1,
                        vertexVO: info,
                    }),
                    success: function (data) {
                        console.log(data);
                        $("#code-header").text(info["belongClass"] + ": " + info["funcName"] + " (" + info["args"].join(", ") + ")");
                        $("#code-pre").html("<code>" + data.content + "</code>");
                    },
                    error: function (err) {
                        console.log(err);
                    }
                });
            });

            cy.cxtmenu({
                selector: 'node.vertex.favor',
                commands: [
                    {
                        content: '<span class="fa fa-arrows-alt fa-2x"></span>',
                        select: function (ele) {
                            let info = ele.data("full_info");
                            $.ajax({
                                type: "post",
                                url: "/code/getFuncCode",
                                dataType: "json",
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    userId: 1,
                                    codeId: 1,
                                    vertexVO: info,
                                }),
                                success: function (data) {
                                    console.log(data);
                                    $("#code-header").text(info["belongClass"] + ": " + info["funcName"] + " (" + info["args"].join(", ") + ")");
                                    $("#code-pre").html("<code>" + data.content + "</code>");
                                },
                                error: function (err) {
                                    console.log(err);
                                }
                            });

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
                selector: 'edge.favor',
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
                            let info = ele.data("full_info");
                            $.ajax({
                                type: "post",
                                url: "/code/getFuncCode",
                                dataType: "json",
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    userId: 1,
                                    codeId: 1,
                                    vertexVO: info,
                                }),
                                success: function (data) {
                                    console.log(data);
                                    $("#code-header").text(info["belongClass"] + ": " + info["funcName"] + " (" + info["args"].join(", ") + ")");
                                    $("#code-pre").html("<code>" + data.content + "</code>");
                                },
                                error: function (err) {
                                    console.log(err);
                                }
                            });

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
                            ele.source().select();
                            ele.target().select();
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
    // $.ajax({
    //     type: "post",
    //     url: "/graphql",
    //     dataType: "json",
    //     data: JSON.stringify({
    //         query: `
    //         query
    //         {
    //             set_domainset(wf: [{weightName :"closeness",weightValue: 0.15}])
    //             {
    //                 domains
    //                 {
    //                     id
    //                     vertices
    //                     {
    //                         id
    //                         funcName
    //                     }
    //                     edges
    //                     {
    //                         id
    //                         start
    //                         {
    //                             id
    //                         }
    //                         end
    //                         {
    //                             id
    //                         }
    //                         weights
    //                         {
    //                             weightValue
    //                         }
    //                     }
    //                 }
    //             }
    //         }`
    //     }),
    //     success: function (data) {
    //         console.log(data);
    //         let graphData = {
    //             nodes: [],
    //             edges: [],
    //         };
    //         data.data.set_domainset.domains.forEach(function (domain) {
    //             graphData.nodes.push({
    //                 data: {
    //                     id: 'd' + domain.id.toString(),
    //                 },
    //                 classes: ['domain'],
    //             });
    //             let vertices = domain.vertices;
    //             vertices.forEach(function (vertex) {
    //                 graphData.nodes.push({
    //                     data: {
    //                         id: 'n' + vertex.id.toString(),
    //                         label: vertex.funcName,
    //                         parent: 'd' + domain.id.toString(),
    //                     },
    //                     classes: ['vertex'],
    //                 });
    //             });
    //             let edges = domain.edges;
    //             edges.forEach(function (edge) {
    //                 graphData.edges.push({
    //                     data: {
    //                         id: 'e' + edge.id.toString(),
    //                         source: 'n' + edge.start.id.toString(),
    //                         target: 'n' + edge.end.id.toString(),
    //                         closeness: edge.weights[0].weightValue
    //                     }
    //                 });
    //             });
    //         });
    //         // data.data.set_domainset.domains[0].vertices.forEach(function (vertex) {
    //         //     graphData.nodes.push({
    //         //         data: {
    //         //             id: 'n' + vertex.id.toString(),
    //         //             label: vertex.funcName,
    //         //         }
    //         //     });
    //         // });
    //         // data.data.set_domainset.domains[0].edges.forEach(function (edge) {
    //         //     graphData.edges.push({
    //         //         data: {
    //         //             id: 'e' + edge.id.toString(),
    //         //             source: 'n' + edge.start.id.toString(),
    //         //             target: 'n' + edge.end.id.toString(),
    //         //             closeness: edge.weights[0].weightValue
    //         //         }
    //         //     });
    //         // });
    //         cy = cytoscape({
    //
    //             container: document.getElementById('cy_container'), // container to render in
    //
    //             elements: {
    //                 nodes: graphData.nodes,
    //                 edges: graphData.edges,
    //             },
    //
    //             style: [ // the stylesheet for the graph
    //                 {
    //                     selector: 'node.vertex',
    //                     style: {
    //                         'background-color': '#9EC9FF',
    //                         'border-width': 1,
    //                         'border-color': '#5B8FF9'
    //                     }
    //                 },
    //                 {
    //                     selector: 'edge',
    //                     style: {
    //                         'width': 3,
    //                         'opacity': 'data(closeness)',
    //                         'line-color': '#5B8FF9',
    //                         'curve-style': 'straight',
    //                         'target-endpoint': 'outside-to-node',
    //                         'target-arrow-shape': 'vee',
    //                         'target-arrow-color': '#5B8FF9',
    //                         'target-arrow-fill': 'filled',
    //                         'arrow-scale': 2
    //                     }
    //                 },
    //                 {
    //                     selector: 'node[label]',
    //                     style: {
    //                         'label': 'data(label)',
    //                         'font-family': 'SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace',
    //                     }
    //                 },
    //                 {
    //                     selector: 'node.domain',
    //                     style: {
    //                         'background-opacity': 0.1,
    //                         'background-color': '#9EC9FF',
    //                         'border-color': '#9EC9FF'
    //                     }
    //                 },
    //                 {
    //                     selector: 'node.favor',
    //                     style: {
    //                         'background-color': '#f2c0ff',
    //                         'border-color': '#e295ec',
    //                     }
    //                 },
    //                 {
    //                     selector: 'edge.favor',
    //                     style: {
    //                         'line-color': '#e295ec',
    //                         'target-arrow-color': '#e295ec',
    //                     }
    //                 },
    //                 {
    //                     selector: 'node:selected',
    //                     style: {
    //                         'background-color': '#b8ffcb',
    //                         'border-color': '#33e17e',
    //                     }
    //                 },
    //                 {
    //                     selector: 'edge:selected',
    //                     style: {
    //                         'line-color': '#33e17e',
    //                         'target-arrow-color': '#33e17e',
    //                     }
    //                 },
    //             ],
    //
    //             layout: {
    //                 // name: 'concentric',
    //                 // minNodeSpacing: 50,
    //                 // concentric: function (node) {
    //                 //     return node.degree();
    //                 // },
    //                 // levelWidth: function (nodes) { // the letiation of concentric values in each level
    //                 //     return 0.5;
    //                 // },
    //
    //                 // name: 'cose',
    //                 // animate: true,
    //                 // nodeDimensionsIncludeLabels: true,
    //                 // padding: 100
    //
    //                 name: 'fcose',
    //                 idealEdgeLength: 150,
    //
    //                 stop: function () {
    //                     $('#loading').hide();
    //                 },
    //             }
    //         });
    //
    //         cy.cxtmenu({
    //             selector: 'node.vertex.favor, edge.favor',
    //             commands: [
    //                 {
    //                     content: '<span class="fa fa-arrows-alt fa-2x"></span>',
    //                     select: function (ele) {
    //                         cy.$('node,edge').unselect();
    //                         console.log('this id', ele.id());
    //                         ele.select();
    //                         let neighborhoods = ele.neighborhood();
    //                         neighborhoods.forEach(function (nb) {
    //                             console.log('neighbor id', nb.id());
    //                             nb.select();
    //                         });
    //                         cy.fit(cy.$('node:selected'), $('#cy_container').height() * 0.25);
    //                     }
    //                 },
    //
    //                 {
    //                     content: '<span style="color:#faff62;" class="fa fa-lightbulb-o fa-2x"></span>',
    //                     select: function (ele) {
    //                         ele.removeClass('favor');
    //                         ele.removeData('favor');
    //                     },
    //                     // enabled: false
    //                 },
    //
    //                 {
    //                     content: '<span class="fa fa-bookmark fa-2x"></span>',
    //                     select: function (ele) {
    //                         console.log(ele.position());
    //                     }
    //                 }
    //             ],
    //             menuRadius: 70,
    //             indicatorSize: 12,
    //             minSpotlightRadius: 12,
    //         });
    //
    //         cy.cxtmenu({
    //             selector: 'node.vertex[^favor]',
    //             commands: [
    //                 {
    //                     content: '<span class="fa fa-arrows-alt fa-2x"></span>',
    //                     select: function (ele) {
    //                         cy.$('node,edge').unselect();
    //                         console.log('this id', ele.id());
    //                         ele.select();
    //                         let neighborhoods = ele.neighborhood();
    //                         neighborhoods.forEach(function (nb) {
    //                             console.log('neighbor id', nb.id());
    //                             nb.select();
    //                         });
    //                         cy.fit(cy.$('node:selected'), $('#cy_container').height() * 0.25);
    //                     }
    //                 },
    //
    //                 {
    //                     content: '<span class="fa fa-lightbulb-o fa-2x"></span>',
    //                     select: function (ele) {
    //                         ele.addClass('favor');
    //                         ele.data('favor', true);
    //                     },
    //                 },
    //
    //                 {
    //                     content: '<span class="fa fa-bookmark fa-2x"></span>',
    //                     select: function (ele) {
    //                         console.log(ele.position());
    //                     }
    //                 }
    //             ],
    //             menuRadius: 70,
    //             indicatorSize: 12,
    //             minSpotlightRadius: 12,
    //         });
    //
    //         cy.cxtmenu({
    //             selector: 'edge[^favor]',
    //             commands: [
    //                 {
    //                     content: '<span class="fa fa-arrows-alt fa-2x"></span>',
    //                     select: function (ele) {
    //                         cy.$('node,edge').unselect();
    //                         console.log('this id', ele.id());
    //                         ele.select();
    //                         let neighborhoods = ele.neighborhood();
    //                         neighborhoods.forEach(function (nb) {
    //                             console.log('neighbor id', nb.id());
    //                             nb.select();
    //                         });
    //                         cy.fit(cy.$('node:selected'), $('#cy_container').height() * 0.25);
    //                     }
    //                 },
    //
    //                 {
    //                     content: '<span class="fa fa-lightbulb-o fa-2x"></span>',
    //                     select: function (ele) {
    //                         ele.addClass('favor');
    //                         ele.data('favor', true);
    //                         ele.source().addClass('favor');
    //                         ele.source().data('favor', true);
    //                         ele.target().addClass('favor');
    //                         ele.target().data('favor', true);
    //                     },
    //                     // enabled: false
    //                 },
    //
    //                 {
    //                     content: '<span class="fa fa-bookmark fa-2x"></span>',
    //                     select: function (ele) {
    //                         console.log(ele.position());
    //                     }
    //                 }
    //             ],
    //             menuRadius: 70,
    //             indicatorSize: 12,
    //             minSpotlightRadius: 12,
    //         });
    //
    //         cy.cxtmenu({
    //             selector: 'node.domain',
    //             commands: [
    //                 {
    //                     content: '<span class="fa fa-arrows-alt fa-2x"></span>',
    //                     select: function (ele) {
    //                         cy.fit(ele);
    //                     }
    //                 },
    //
    //                 {
    //                     content: '<span class="fa fa-bookmark fa-2x"></span>',
    //                     select: function (ele) {
    //                         console.log(ele.position());
    //                     }
    //                 }
    //             ],
    //             menuRadius: 70,
    //             indicatorSize: 12,
    //             minSpotlightRadius: 12,
    //         });
    //
    //         console.log(JSON.stringify(cy.json()).length);
    //     },
    //     error: function (err) {
    //         console.log(err);
    //     }
    // });


    //buttons on the middle main div
    $("#layout-btn").on("click", function () {
        cy.fit();
    });
    $("#filter_search").on("click", function () {
        $('#loading').show();
        $.ajax({
            type: "post",
            url: "graph/filter",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify([{
                "weightName": "closeness",
                "weightValue": $("#range_value").val()
            }]),
            success: function (data) {
                console.log(data);
                let graphData = {
                    nodes: [],
                    edges: [],
                };
                data.content.domainSetVO.domainVOs.forEach(function (domain) {
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
                    let edges = domain.edgeVOS;
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
                cy.elements().remove();
                cy.json({
                    elements: {
                        nodes: graphData.nodes,
                        edges: graphData.edges,
                    }
                });
                $("#filterModal").modal('hide');
                cy.layout(fcose_layout).run();
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

    //code on the right sidebar
    //labels on the right sidebar
});