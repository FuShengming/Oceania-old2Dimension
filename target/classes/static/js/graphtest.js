$(function () {
    $("#jQuery_div").text("jQuery works");
    $.ajax({
        type: "post",
        url: "/graphql",
        dataType: "json",
        data: JSON.stringify({
            query: `
            query
            {
                get_domainset
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
            // console.log(data.data.get_domainset.domains[1]);
            let graphData = {
                nodes: [],
                edges: [],
            };
            // console.log(graphData);
            // console.log(data.data.get_domainset.domains[1].edges);
            data.data.get_domainset.domains[0].vertices.forEach(function (vertex) {
                graphData.nodes.push({
                    id: vertex.id.toString(),
                    label: vertex.funcName,
                });
            });
            data.data.get_domainset.domains[0].edges.forEach(function (edge) {
                graphData.edges.push({
                    source: edge.start.id.toString(),
                    target: edge.end.id.toString(),
                });
            });
            console.log(graphData);
            graph.data(graphData); // 加载数据
            graph.render(); // 渲染
        },
        error: function (err) {
            console.log(err);
        }
    });
    console.log(G6.Global.version);
    const data = {
        nodes: [
            {id: '1167', label: "getIcAddress3"},
            {id: '1168', label: "getIcCity"},
            {id: '1169', label: "getIcState"},
            {id: '1170', label: "getIcZip"},
            {id: '1171', label: "getIcZip2"},
            {id: '1172', label: "getIcZip1"},
        ],
        edges: [
            {source: '1167', target: '1168'},
            {source: '1167', target: '1169'},
            {source: '1167', target: '1170'},
            {source: '1170', target: '1171'},
            {source: '1170', target: '1172'},
        ],
    };
    console.log(data);
    const graph = new G6.Graph({
        container: "graph_container",
        width: 3600,
        height: 2700,
        layout: {
            type: "circular",
            startRadius: 100, // 可选
            endRadius: 1200, // 可选
            clockwise: false, // 可选
            divisions: 20, // 可选
            ordering: 'topology', // 可选
            angleRatio: 8, // 可选
            // type: 'concentric',
            // linkDistance: 200,         // 可选，边长
            // preventOverlap: true,     // 可选，必须配合 nodeSize
            // nodeSize: 5,             // 可选
            // sweep: 10,                // 可选
            // equidistant: false,       // 可选
            // startAngle: 0,            // 可选
            // clockwise: false,         // 可选
            // maxLevelDiff: 0.5,         // 可选
            // sortBy: 'degree'          // 可选
        },
        modes: {
            default: ['drag-canvas', 'zoom-canvas']
        },
        animate: true,
        defaultNode: {
            type: "circle",
            size: 5,
            color: "#5B8FF9",
            style: {
                fill: "#9EC9FF",
                lineWidth: 3
            },
            labelCfg: {
                style: {
                    fill: "#000",
                    opacity: 0,
                    fontSize: 5
                }
            }
        },
        defaultEdge: {
            style: {
                stroke: "#e2e2e2",
                lineWidth: 1
            }
        }
    });

    graph.data(data);
    graph.render();
});