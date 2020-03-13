package com.old2dimension.OCEANIA.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.po.Domain;
import com.old2dimension.OCEANIA.po.DomainSet;
import com.old2dimension.OCEANIA.po.Edge;
import com.old2dimension.OCEANIA.po.Vertex;
import com.old2dimension.OCEANIA.vo.WeightForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GraphQueryResolver implements GraphQLQueryResolver {

    @Autowired
    GraphCalculateImpl graphCalculate;

    GraphQueryResolver() {
        graphCalculate = new GraphCalculateImpl();
        graphCalculate.initializeGraph("call_dependencies_update.txt");
    }

    public Vertex get_v(int id) throws GraphQLErrorException {
        if (id >= graphCalculate.allVertexes.size())
            throw new GraphQLErrorException(400, "index error");
        return graphCalculate.allVertexes.get(id);
    }

    public ArrayList<Vertex> get_all_vertices() {
        return graphCalculate.allVertexes;
    }

    public Edge get_e(int id) {
        if (id >= graphCalculate.allEdges.size())
            throw new GraphQLErrorException(400, "index error");
        return graphCalculate.allEdges.get(id);
    }

    public ArrayList<Edge> get_all_edges() {
        return graphCalculate.allEdges;
    }

    public Domain get_d(int id) {
        if (id >= graphCalculate.domainSet.getDomains().size())
            throw new GraphQLErrorException(400, "index error");
        return graphCalculate.domainSet.getDomains().get(id);
    }

    public DomainSet get_domainset() {
        return graphCalculate.domainSet;
    }

    public DomainSet set_domainset(ArrayList<WeightForm> weightForms) {
        graphCalculate.getConnectedDomains(weightForms);
        return graphCalculate.domainSet;
    }
}
