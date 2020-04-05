package com.old2dimension.OCEANIA.blImpl;

import com.old2dimension.OCEANIA.bl.LabelBL;
import com.old2dimension.OCEANIA.dao.DomainLabelRepository;
import com.old2dimension.OCEANIA.dao.EdgeLabelRepository;
import com.old2dimension.OCEANIA.dao.VertexLabelRepository;
import com.old2dimension.OCEANIA.po.DomainLabel;
import com.old2dimension.OCEANIA.po.EdgeLabel;
import com.old2dimension.OCEANIA.po.VertexLabel;
import com.old2dimension.OCEANIA.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LabelBLImpl implements LabelBL {
    @Autowired
    VertexLabelRepository vertexLabelRepository;
    @Autowired
    EdgeLabelRepository edgeLabelRepository;
    @Autowired
    DomainLabelRepository domainLabelRepository;

    public void setVertexLabelRepository(VertexLabelRepository vertexLabelRepository) {
        this.vertexLabelRepository = vertexLabelRepository;
    }

    public void setEdgeLabelRepository(EdgeLabelRepository edgeLabelRepository) {
        this.edgeLabelRepository = edgeLabelRepository;
    }

    public void setDomainLabelRepository(DomainLabelRepository domainLabelRepository) {
        this.domainLabelRepository = domainLabelRepository;
    }

    public ResponseVO noteVertex(VertexLabelVO vertexLabelVO) {
        try{
            VertexLabel vertexLabel = vertexLabelRepository.findVertexLabelByCodeIdAndVertexId(
                    vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId());
            if (vertexLabel == null) {
                VertexLabel v = new VertexLabel(
                        vertexLabelVO.getUserId(), vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId(), vertexLabelVO.getContent());
                vertexLabelRepository.save(v);
                return ResponseVO.buildSuccess(v);
            } else {
                vertexLabel.setContent(vertexLabelVO.getContent());
                vertexLabelRepository.save(vertexLabel);
                return ResponseVO.buildSuccess(vertexLabel);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("note failed");
        }

    }

    public ResponseVO noteEdge(EdgeLabelVO edgeLabelVO) {
        try {
            EdgeLabel edgeLabel = edgeLabelRepository.findEdgeLabelByCodeIdAndEdgeId(
                    edgeLabelVO.getCodeId(), edgeLabelVO.getEdgeId());
            if (edgeLabel == null) {
                EdgeLabel e = new EdgeLabel(
                        edgeLabelVO.getUserId(), edgeLabelVO.getCodeId(), edgeLabelVO.getEdgeId(), edgeLabelVO.getContent());
                edgeLabelRepository.save(e);
                return ResponseVO.buildSuccess(e);
            } else {
                edgeLabel.setContent(edgeLabelVO.getContent());
                edgeLabelRepository.save(edgeLabel);
                return ResponseVO.buildSuccess(edgeLabel);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("note failed");
        }

    }

    public ResponseVO noteDomain(DomainLabelVO domainLabelVO) {
        try {
            DomainLabel domainLabel = domainLabelRepository.findDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(
                    domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex());
            if (domainLabel == null) {
                DomainLabel d = new DomainLabel(
                        domainLabelVO.getUserId(), domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex(), domainLabelVO.getContent());
                domainLabelRepository.save(d);
                return ResponseVO.buildSuccess(d);
            } else {
                domainLabel.setContent(domainLabelVO.getContent());
                domainLabelRepository.save(domainLabel);
                return ResponseVO.buildSuccess(domainLabel);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("note failed");
        }

    }


    public ResponseVO deleteVertexLabel(VertexLabelVO vertexLabelVO) {
        try {
            VertexLabel vertexLabel = vertexLabelRepository.findVertexLabelByCodeIdAndVertexId(
                    vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId());
            if (vertexLabel == null) {
                return ResponseVO.buildFailure("no such label");
            } else {
                vertexLabelRepository.deleteVertexLabelByCodeIdAndVertexId(
                        vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId());
                return ResponseVO.buildSuccess("delete label successfully");
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure("delete failed");
        }

    }

    public ResponseVO deleteEdgeLabel(EdgeLabelVO edgeLabelVO) {
        try {
            EdgeLabel edgeLabel = edgeLabelRepository.findEdgeLabelByCodeIdAndEdgeId(
                    edgeLabelVO.getCodeId(), edgeLabelVO.getEdgeId());
            if (edgeLabel == null) {
                return ResponseVO.buildFailure("no such label");
            } else {
                edgeLabelRepository.deleteEdgeLabelByCodeIdAndEdgeId(
                        edgeLabelVO.getCodeId(), edgeLabelVO.getEdgeId());
                return ResponseVO.buildSuccess("delete label successfully");
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure("delete failed");
        }

    }

    public ResponseVO deleteDomainLabel(DomainLabelVO domainLabelVO) {
        try {
            DomainLabel domainLabel = domainLabelRepository.findDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(
                    domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex());
            if (domainLabel == null) {
                return ResponseVO.buildFailure("no such label");
            } else {
                domainLabelRepository.deleteDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(
                        domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex());
                return ResponseVO.buildSuccess("delete label successfully");
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure("delete failed");
        }

    }


    public ResponseVO getOneVertexLabel(VertexLabelVO vertexLabelVO) {
        try {
            VertexLabel vertexLabel = vertexLabelRepository.findVertexLabelByCodeIdAndVertexId(
                    vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId());
            if (vertexLabel == null) {
                return ResponseVO.buildFailure("No such vertexLabel");
            } else {
                return ResponseVO.buildSuccess(vertexLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("get failed");
        }
    }

    public ResponseVO getOneEdgeLabel(EdgeLabelVO edgeLabelVO) {
        try {
            EdgeLabel edgeLabel = edgeLabelRepository.findEdgeLabelByCodeIdAndEdgeId(
                    edgeLabelVO.getCodeId(), edgeLabelVO.getEdgeId());
            if (edgeLabel == null) {
                return ResponseVO.buildFailure("No such vertexLabel");
            } else {
                return ResponseVO.buildSuccess(edgeLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("get failed");
        }
    }

    public ResponseVO getOneDomainLabel(DomainLabelVO domainLabelVO) {
        try {
            DomainLabel domainLabel = domainLabelRepository.findDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(
                    domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex());
            if (domainLabel == null) {
                return ResponseVO.buildFailure("No such vertexLabel");
            } else {
                return ResponseVO.buildSuccess(domainLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("get failed");
        }
    }


    public ResponseVO getAllVertexLabel(UserAndCodeForm userAndCodeForm) {
        ArrayList<VertexLabel> res = (ArrayList<VertexLabel>) vertexLabelRepository.findVertexLabelsByCodeId(userAndCodeForm.getCodeId());
        return ResponseVO.buildSuccess(res);
    }

    public ResponseVO getAllEdgeLabel(UserAndCodeForm userAndCodeForm) {
        ArrayList<EdgeLabel> res = (ArrayList<EdgeLabel>) edgeLabelRepository.findEdgeLabelsByCodeId(userAndCodeForm.getCodeId());
        return ResponseVO.buildSuccess(res);
    }

    public ResponseVO getAllDomainLabel(UserAndCodeForm userAndCodeForm) {
        ArrayList<DomainLabel> res = (ArrayList<DomainLabel>) domainLabelRepository.findDomainLabelsByCodeId(userAndCodeForm.getCodeId());
        return ResponseVO.buildSuccess(res);
    }


}
