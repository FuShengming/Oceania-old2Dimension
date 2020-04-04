package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.*;


public interface LabelBL {

    ResponseVO noteVertex(VertexLabelVO vertexLabelVO);
    ResponseVO noteEdge(EdgeLabelVO edgeLabelVO);
    ResponseVO noteDomain(DomainLabelVO domainLabelVO);

    ResponseVO deleteVertexLabel(VertexLabelVO vertexLabelVO);
    ResponseVO deleteEdgeLabel(EdgeLabelVO edgeLabelVO);
    ResponseVO deleteDomainLabel(DomainLabelVO domainLabelVO);

    ResponseVO getOneVertexLabel(VertexLabelVO vertexLabelVO);
    ResponseVO getOneEdgeLabel(EdgeLabelVO edgeLabelVO);
    ResponseVO getOneDomainLabel(DomainLabelVO domainLabelVO);

    ResponseVO getAllVertexLabel(UserAndCodeForm userAndCodeForm);
    ResponseVO getAllEdgeLabel(UserAndCodeForm userAndCodeForm);
    ResponseVO getAllDomainLabel(UserAndCodeForm userAndCodeForm);

}
