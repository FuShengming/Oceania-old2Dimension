package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.DomainLabel;
import com.old2dimension.OCEANIA.po.EdgeLabel;
import com.old2dimension.OCEANIA.po.VertexLabel;
import com.old2dimension.OCEANIA.vo.*;

import java.util.List;


public interface LabelBL {

    ResponseVO noteVertex(VertexLabelVO vertexLabelVO);

    ResponseVO noteEdge(EdgeLabelVO edgeLabelVO);

    ResponseVO noteDomain(DomainLabelVO domainLabelVO);

    ResponseVO deleteVertexLabel(VertexLabelVO vertexLabelVO);

    ResponseVO deleteEdgeLabel(EdgeLabelVO edgeLabelVO);

    ResponseVO deleteDomainLabel(DomainLabelVO domainLabelVO);

    ResponseVO getOneVertexLabels(VertexLabelVO vertexLabelVO);

    ResponseVO getOneEdgeLabels(EdgeLabelVO edgeLabelVO);

    ResponseVO getOneDomainLabels(DomainLabelVO domainLabelVO);

    List<VertexLabel> getAllVertexLabel(UserAndCodeForm userAndCodeForm);
    List<EdgeLabel> getAllEdgeLabel(UserAndCodeForm userAndCodeForm);
    List<DomainLabel> getAllDomainLabel(UserAndCodeForm userAndCodeForm);

    List<VertexLabel> saveAllVertexLabel(List<VertexLabel> vertexLabels);
    List<EdgeLabel> saveAllEdgeLabel(List<EdgeLabel> edgeLabels);
    List<DomainLabel> saveAllDomainLabel(List<DomainLabel> domainLabels);

    void deleteAllVertexLabel(List<VertexLabel> vertexLabels);
    void deleteAllEdgeLabel(List<EdgeLabel> edgeLabels);
    void deleteAllDomainLabel(List<DomainLabel> domainLabels);

    int countVertexLabel(int userId, int codeId);
    int countEdgeLabel(int userId, int codeId);
    int countDomainLabel(int userId, int codeId);


}
