$(function () {

    let get_v_labels = function (vertexId) {
        $.ajax({
            type: "post",
            url: "/label/getOneVertexLabel",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                userId: 1,
                codeId: 1,
                vertexId: vertexId
            }),
            success: function (data) {
                if (data.success) {
                    console.log(data);
                    let labels = data.content;
                    let h = "";
                    labels.forEach(function (label) {
                        h += "<div class=\"card m-2\">\n" +
                            "<h5 class=\"card-header\">" + label.title + "</h5>\n" +
                            "<div class=\"card-body\">\n" +
                            "<div class=\"card-text\">\n" +
                            "<p>" + label.content.replace(/\n/g, "<br>") + "</p>\n" +
                            "</div>\n" +
                            "<div class=\"mt-3\" style=\"text-align: right\">\n" +
                            "<button class=\"btn btn-info label-edit\">Edit</button>\n" +
                            "<button class=\"btn btn-danger label-del\" " + " labelId=" + label.id + ">Delete</button>\n" +
                            "</div>\n" +
                            "</div>\n" +
                            "</div>";
                    });
                    $("#labels-container").html(h);
                    $(".label-del").on('click', function (event) {
                        let id = Number($(event.target).attr('labelId'));
                        $.ajax({
                            type: "post",
                            url: "/label/deleteVertexLabel",
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify({
                                userId: 1,
                                codeId: 1,
                                id: id
                            }),
                            success: function (data) {
                                if (data.success) {
                                    get_v_labels(vertexId);
                                } else {
                                    console.log(data.message);
                                }
                            },
                            error: function (err) {
                                console.log(err);
                            }
                        });
                    });
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };
    let get_e_labels = function (edgeId) {
        $.ajax({
            type: "post",
            url: "/label/getOneEdgeLabel",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                userId: 1,
                codeId: 1,
                edgeId: edgeId
            }),
            success: function (data) {
                if (data.success) {
                    console.log(data);
                    let labels = data.content;
                    let h = "";
                    labels.forEach(function (label) {
                        h += "<div class=\"card m-2\">\n" +
                            "<h5 class=\"card-header\">" + label.title + "</h5>\n" +
                            "<div class=\"card-body\">\n" +
                            "<div class=\"card-text\">\n" +
                            "<p>" + label.content.replace(/\n/g, "<br>") + "</p>\n" +
                            "</div>\n" +
                            "<div class=\"mt-3\" style=\"text-align: right\">\n" +
                            "<button class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#labelModal\">Edit</button>\n" +
                            "<button class=\"btn btn-danger label-del\" " + " labelId=" + label.id + ">Delete</button>\n" +
                            "</div>\n" +
                            "</div>\n" +
                            "</div>";
                    });
                    $("#labels-container").html(h);
                    $(".label-del").on('click', function (event) {
                        let id = Number($(event.target).attr('labelId'));
                        $.ajax({
                            type: "post",
                            url: "/label/deleteEdgeLabel",
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify({
                                userId: 1,
                                codeId: 1,
                                id: id
                            }),
                            success: function (data) {
                                if (data.success) {
                                    get_e_labels(edgeId);
                                } else {
                                    console.log(data.message);
                                }
                            },
                            error: function (err) {
                                console.log(err);
                            }
                        });
                    });
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };
    let get_d_labels = function (firstEdgeId, numOfVertex) {
        $.ajax({
            type: "post",
            url: "/label/getOneDomainLabel",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                userId: 1,
                codeId: 1,
                firstEdgeId: firstEdgeId,
                numOfVertex: numOfVertex
            }),
            success: function (data) {
                if (data.success) {
                    console.log(data);
                    let labels = data.content;
                    let h = "";
                    labels.forEach(function (label) {
                        h += "<div class=\"card m-2\">\n" +
                            "<h5 class=\"card-header\">" + label.title + "</h5>\n" +
                            "<div class=\"card-body\">\n" +
                            "<div class=\"card-text\">\n" +
                            "<p>" + label.content.replace(/\n/g, "<br>") + "</p>\n" +
                            "</div>\n" +
                            "<div class=\"mt-3\" style=\"text-align: right\">\n" +
                            "<button class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#labelModal\">Edit</button>\n" +
                            "<button class=\"btn btn-danger label-del\" " + " labelId=" + label.id + ">Delete</button>\n" +
                            "</div>\n" +
                            "</div>\n" +
                            "</div>";
                    });
                    $("#labels-container").html(h);
                    $(".label-del").on('click', function (event) {
                        let id = Number($(event.target).attr('labelId'));
                        $.ajax({
                            type: "post",
                            url: "/label/deleteDomainLabel",
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify({
                                userId: 1,
                                codeId: 1,
                                id: id
                            }),
                            success: function (data) {
                                if (data.success) {
                                    get_d_labels(firstEdgeId, numOfVertex);
                                } else {
                                    console.log(data.message);
                                }
                            },
                            error: function (err) {
                                console.log(err);
                            }
                        });
                    });
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };


    $("#label_submit").on('click', function () {
        let type = $("#labelModal").attr("x-id")[0];
        let x_id = Number($("#labelModal").attr("x-id").substring(1));
        if (!$("#labelModal").attr("label-id") === undefined) {
            console.log($("#labelModal").attr("label-id"))
        }

        console.log(type, x_id);
        if (type === 'n') {
            $.ajax({
                type: "post",
                url: "/label/noteVertex",
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify({
                    id: $("#labelModal").attr("label-id"),
                    userId: 1,
                    codeId: 1,
                    vertexId: x_id,
                    title: $("#title-input").val(),
                    content: $("#content-input").val()
                }),
                success: function (data) {
                    if (data.success) {
                        console.log("success");
                        $("#labelModal").modal('hide');
                        $("#title-input").val("");
                        $("#content-input").val("");
                        get_v_labels(x_id);
                    } else {
                        console.log(data.message);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            })
        } else if (type === 'e') {
            $.ajax({
                type: "post",
                url: "/label/noteEdge",
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify({
                    id: $("#labelModal").attr("label-id"),
                    userId: 1,
                    codeId: 1,
                    edgeId: x_id,
                    title: $("#title-input").val(),
                    content: $("#content-input").val()
                }),
                success: function (data) {
                    if (data.success) {
                        console.log("success");
                        $("#labelModal").modal('hide');
                        $("#title-input").val("");
                        $("#content-input").val("");
                        get_e_labels(x_id);
                    } else {
                        console.log(data.message);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            })
        } else if (type === 'd') {
            let firstEdgeId = $('#labelModal').attr("firstEdgeId");
            let numOfVertex = $('#labelModal').attr("numOfVertex");
            $.ajax({
                type: "post",
                url: "/label/noteDomain",
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify({
                    firstEdgeId: firstEdgeId,
                    numOfVertex: numOfVertex,
                    id: $("#labelModal").attr("label-id"),
                    userId: 1,
                    codeId: 1,
                    title: $("#title-input").val(),
                    content: $("#content-input").val()
                }),
                success: function (data) {
                    if (data.success) {
                        console.log("success");
                        $("#labelModal").modal('hide');
                        $("#title-input").val("");
                        $("#content-input").val("");
                        get_d_labels(firstEdgeId, numOfVertex);
                    } else {
                        console.log(data.message);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            })
        }

    });

    let update_info = function (v_num, e_num, d_num) {
        let t = "<tr><th scope=\"row\">Vertices</th><td>" + v_num.toString() + "</td></tr>" +
            "<tr><th scope=\"row\">Edges</th><td>" + e_num.toString() + "</td></tr>" +
            "<tr><th scope=\"row\">Domains</th><td>" + d_num.toString() + "</td></tr>";
        $("#info-table-body").html(t);
    };

    // constructs the suggestion engine
    let states = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        // `states` is an array of state names defined in "The Basics"
        remote: {
            url: '/graph/findVertex/%QUERY',
            wildcard: '%QUERY',
            transform: function (data) {
                console.log(data);
                let names = [];
                data.content.forEach(function (func) {
                    names.push(func.fullName);
                });
                // Map the remote source JSON array to a JavaScript object array
                return names;
            }
        }
    });

    $('.typeahead').typeahead({
        hint: true,
        highlight: true,
        minLength: 1
    }, {
        name: 'states',
        source: states
    });

    $("#node-search-btn").on("click", function () {
        if ($("#func-name-input").val() === "") {
            alert("Input can't be empty.");
            return;
        }
        $.ajax({
            type: "get",
            url: "/graph/findVertex/" + $("#func-name-input").val(),
            success: function (data) {
                console.log(data);
                if (data.content.length === 0) {
                    alert("Can't find such node.");
                    return;
                }
                if (data.content.length > 1) {
                    alert("Input is ambiguous. You can choose one in the autocomplete menu.");
                    return;
                }
                $("#searchModal").modal('hide');
                let id = 'n' + data.content[0].id.toString();
                console.log(id);
                expand_tree(id);
                cy.$('node,edge').unselect();
                let n = cy.$id(id);
                console.log(n);
                if (n.length === 0) {
                    alert("This node has been filtered out. Please adjust filter weights.")
                } else {
                    n.select();
                    cy.fit(n, $('#cy_container').height() * 0.45);
                    let info = n.data("full_info");
                    get_code(info);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

    $("#path-search-btn").on("click", function () {
        if ($("#start-node-input").val() === "" || $("#end-node-input").val() === 0) {
            alert("Input can't be empty.");
            return;
        }
        let start_id = -1;
        let end_id = -1;
        $.ajax({
            type: "get",
            url: "/graph/findVertex/" + $("#start-node-input").val(),
            success: function (data) {
                console.log(data);
                if (data.content.length === 0) {
                    alert("Can't find such start node.");
                    return;
                }
                if (data.content.length > 1) {
                    alert("Start node input is ambiguous. You can choose one in the autocomplete menu.");
                    return;
                }
                start_id = data.content[0].id;
                $.ajax({
                    type: "get",
                    url: "/graph/findVertex/" + $("#end-node-input").val(),
                    success: function (data) {
                        console.log(data);
                        if (data.content.length === 0) {
                            alert("Can't find such end node.");
                            return;
                        }
                        if (data.content.length > 1) {
                            alert("End node input is ambiguous. You can choose one in the autocomplete menu.");
                            return;
                        }
                        end_id = data.content[0].id;
                        $.ajax({
                            type: "post",
                            url: "/graph/findPath",
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify([{id: start_id}, {id: end_id}]),
                            success: function (data) {
                                console.log(data);
                                let n = data.content.pathNum;
                                let paths = data.content.pathVOS;
                                if (n === 0) {
                                    alert("No path found");
                                    return;
                                }
                                let all_edges = [];
                                let all_nodes = [];
                                paths.forEach(function (path) {
                                    path.edges.forEach(function (edge) {
                                        let id = edge.id;
                                        let e = cy.$id('e' + id.toString());
                                        if (e.length === 0) {

                                        } else {
                                            all_edges.push(e[0]);
                                            all_nodes.push(e[0].source());
                                            all_nodes.push(e[0].target());
                                        }
                                    })
                                });
                                $("#searchModal").modal('hide');
                                cy.$('node,edge').unselect();
                                all_edges.forEach(function (edge) {
                                    edge.select();
                                });
                                all_nodes.forEach(function (node) {
                                    node.select();
                                });
                                cy.fit(cy.$('node:selected'), $('#cy_container').height() * 0.25);
                            },
                            error: function (err) {
                                console.log(err);
                            }
                        });
                    },
                    error: function (err) {
                        console.log(err);
                    }
                });
            },
            error: function (err) {
                console.log(err);
            }
        });
    });
    //initialize cytoscape
    let cy = cytoscape();
    let fcose_layout = {
        name: 'fcose',
        // Represents the amount of the vertical space to put between the zero degree members during the tiling operation(can also be a function)
        tilingPaddingVertical: 100,
        // Represents the amount of the horizontal space to put between the zero degree members during the tiling operation(can also be a function)
        tilingPaddingHorizontal: 100,
        nodeRepulsion: 450000,
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
    let cose_bilkent_layout = {
        name: 'cose-bilkent',
        randomize: true,
        stop: function () {
            $('#loading').hide();
        }
    };

    //code on the right
    let get_code = function (info) {
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
                $("#code-header").text(info["belongClass"] + ":" + info["funcName"] + "(" + info["args"].join(",") + ")");
                if (data.success) {
                    $("#code-pre").html("<code>" + data.content.replace(/\n\t/g, "\n").substring(1) + "</code>");
                } else {
                    $("#code-pre").html("<code>" + data.message + "</code>");
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };

    //treeview on the left sidebar
    let remove_empty_node = function (tree) {
        tree.nodes.forEach(function (sub_tree) {
            if (sub_tree.nodes.length === 0) {
                delete sub_tree.nodes;
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
                onNodeSelected: function (event, data) {
                    console.log(event);
                    console.log(data);
                    if (data.vertexId !== -1) {
                        cy.$('node,edge').unselect();
                        let n = cy.$id('n' + data.vertexId.toString());
                        console.log(n);
                        if (n.length === 0) {
                            alert("This node has been filtered out. Please adjust filter weights.")
                        } else {
                            n.select();
                            cy.fit(n, $('#cy_container').height() * 0.45);
                            let info = n.data("full_info");
                            get_code(info);
                        }

                    }
                }
            });
        },
        error: function (err) {
            console.log(err);
        }
    });

    let expand_tree = function (id) {
        let treeViewObject = $('#tree').data('treeview');
        let allCollapsedNodes = treeViewObject.getCollapsed(),
            allExpandedNodes = treeViewObject.getExpanded(),
            allNodes = allCollapsedNodes.concat(allExpandedNodes);
        let nodeId = -1;
        allNodes.forEach(function (node) {
            if (id === 'n' + node.vertexId.toString()) {
                nodeId = node.nodeId;
            }
        });
        console.log(nodeId);
        $('#tree').treeview('collapseAll', {silent: true});
        $('#tree').treeview('revealNode', [nodeId, {silent: true}]);
        $('#tree').treeview('selectNode', [nodeId, {silent: true}]);
        $(".node-selected")[0].scrollIntoView({
            behavior: "smooth", // or "auto" or "instant"
            block: "start" // or "end"
        });
    };

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
        timeout: 10000,
        success: function (data) {
            console.log(data);
            console.log(JSON.stringify(data).length);
            let graphData = {
                nodes: [],
                edges: [],
            };
            data.content.domainSetVO.domainVOs.forEach(function (domain) {
                graphData.nodes.push({
                    data: {
                        id: 'd' + domain.id.toString(),
                        numOfVertex: domain.vertices.length,
                        firstEdgeId: domain.edgeVOS[0].id,
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
            update_info(graphData.nodes.length - data.content.domainSetVO.domainVOs.length,
                graphData.edges.length,
                data.content.domainSetVO.domainVOs.length);

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
            });
            cy.layout(cose_bilkent_layout).run();

            cy.on('tap', 'node.vertex', function (event) {
                expand_tree(event.target.data("id"));
                get_v_labels(Number(event.target.data("id").substring(1)));

                console.log('selected');
                console.log(event.target.data());
                let info = event.target.data("full_info");
                get_code(info);
            });
            cy.on('tap', 'edge', function (event) {
                get_e_labels(Number(event.target.data("id").substring(1)));
            });
            cy.on('tap', 'node.domain', function (event) {
                get_d_labels(event.target.data("firstEdgeId"), event.target.data("numOfVertex"));
            });

            cy.cxtmenu({
                selector: 'node.vertex.favor',
                commands: [
                    {
                        content: '<span class="fa fa-arrows-alt fa-2x"></span>',
                        select: function (ele) {
                            expand_tree(ele.data("id"));

                            let info = ele.data("full_info");
                            get_code(info);

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
                            cy.$('node,edge').unselect();
                            ele.select();
                            $("#labelModal").removeAttr('label-id');
                            $("#labelModal").attr('x-id', ele.data("id"));
                            $("#labelModal").modal('show');
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
                            cy.$('node,edge').unselect();
                            ele.select();
                            $("#labelModal").removeAttr('label-id');
                            $("#labelModal").attr('x-id', ele.data("id"));
                            $("#labelModal").modal('show');
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
                            expand_tree(ele.data("id"));

                            let info = ele.data("full_info");
                            get_code(info);

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
                            cy.$('node,edge').unselect();
                            ele.select();
                            $("#labelModal").removeAttr('label-id');
                            $("#labelModal").attr('x-id', ele.data("id"));
                            $("#labelModal").modal('show');
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
                            cy.$('node,edge').unselect();
                            ele.select();
                            $("#labelModal").removeAttr('label-id');
                            $("#labelModal").attr('x-id', ele.data("id"));
                            $("#labelModal").modal('show');
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
                            cy.$('node,edge').unselect();
                            ele.select();
                            $("#labelModal").removeAttr('label-id');
                            $("#labelModal").attr('x-id', ele.data("id"));
                            $("#labelModal").attr('firstEdgeId', ele.data("firstEdgeId"));
                            $("#labelModal").attr('numOfVertex', ele.data("numOfVertex"));
                            $("#labelModal").modal('show');
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
                console.log(JSON.stringify(data).length);
                let graphData = {
                    nodes: [],
                    edges: [],
                };
                data.content.domainSetVO.domainVOs.forEach(function (domain) {
                    graphData.nodes.push({
                        data: {
                            id: 'd' + domain.id.toString(),
                            numOfVertex: domain.vertices.length,
                            firstEdgeId: domain.edgeVOS[0].id,
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

                update_info(graphData.nodes.length - data.content.domainSetVO.domainVOs.length,
                    graphData.edges.length,
                    data.content.domainSetVO.domainVOs.length);

                cy.elements().remove();
                $("#filterModal").modal('hide');
                cy.json({
                    elements: {
                        nodes: graphData.nodes,
                        edges: graphData.edges,
                    }
                });
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