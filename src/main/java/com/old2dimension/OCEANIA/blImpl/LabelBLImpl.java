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
import java.util.List;

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
        try {
            List<VertexLabel> vertexLabels = vertexLabelRepository.findVertexLabelsByCodeIdAndUserId(
                    vertexLabelVO.getCodeId(), vertexLabelVO.getUserId());
            VertexLabel v = new VertexLabel(
                    vertexLabelVO.getUserId(), vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId(), vertexLabelVO.getTitle(), vertexLabelVO.getContent());
            if (vertexLabels.size() == 0) {
                vertexLabelRepository.save(v);
                return ResponseVO.buildSuccess(v);
            } else {
                boolean existed = false;
                VertexLabel existedLabel = null;
                for (VertexLabel cur : vertexLabels) {
                    if (vertexLabelVO.getId() == cur.getId()) {
                        existedLabel = cur;
                        existed = true;
                    }
                }
                if (existed) {
                    existedLabel.setTitle(vertexLabelVO.getTitle());
                    existedLabel.setContent(vertexLabelVO.getContent());
                    vertexLabelRepository.save(existedLabel);
                    return ResponseVO.buildSuccess(existedLabel);
                } else {
                    if (vertexLabelVO.getId() == 0) {
                        vertexLabelRepository.save(v);
                        return ResponseVO.buildSuccess(v);
                    } else {
                        return ResponseVO.buildFailure("the vertex label to update does not exist");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("note failed");
        }

    }

    public ResponseVO noteEdge(EdgeLabelVO edgeLabelVO) {
        try {
            List<EdgeLabel> edgeLabels = edgeLabelRepository.findEdgeLabelsByCodeIdAndUserId(
                    edgeLabelVO.getCodeId(), edgeLabelVO.getUserId());
            EdgeLabel e = new EdgeLabel(edgeLabelVO.getUserId(), edgeLabelVO.getEdgeId(), edgeLabelVO.getCodeId(), edgeLabelVO.getTitle(), edgeLabelVO.getContent());
            if (edgeLabels.size() == 0) {
                edgeLabelRepository.save(e);
                return ResponseVO.buildSuccess(e);
            } else {

                boolean existed = false;
                EdgeLabel existedLabel = null;
                for (EdgeLabel cur : edgeLabels) {
                    if (edgeLabelVO.getId() == cur.getId()) {
                        existedLabel = cur;
                        existed = true;
                    }
                }
                if (existed) {
                    existedLabel.setTitle(edgeLabelVO.getTitle());
                    existedLabel.setContent(edgeLabelVO.getContent());
                    existedLabel = edgeLabelRepository.save(existedLabel);
                    return ResponseVO.buildSuccess(existedLabel);
                } else {
                    if (edgeLabelVO.getId() == 0) {
                        edgeLabelRepository.save(e);
                        return ResponseVO.buildSuccess(e);
                    } else {
                        return ResponseVO.buildFailure("the edge label to update does not exist");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("note failed");
        }

    }

    public ResponseVO noteDomain(DomainLabelVO domainLabelVO) {
        try {
            List<DomainLabel> domainLabels = domainLabelRepository.findDomainLabelsByCodeIdAndUserId(
                    domainLabelVO.getCodeId(), domainLabelVO.getUserId());
            DomainLabel d = new DomainLabel(
                    domainLabelVO.getUserId(), domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex(), domainLabelVO.getTitle(), domainLabelVO.getContent());

            if (domainLabels.size() == 0 && domainLabelVO.getId() == 0) {
                domainLabelRepository.save(d);
                return ResponseVO.buildSuccess(d);
            } else {
                boolean existed = false;
                DomainLabel existedLabel = null;
                for (DomainLabel cur : domainLabels) {
                    if (domainLabelVO.getId() == cur.getId()) {
                        existedLabel = cur;
                        existed = true;
                    }
                }
                if (existed) {
                    existedLabel.setTitle(domainLabelVO.getTitle());
                    existedLabel.setContent(domainLabelVO.getContent());
                    existedLabel = domainLabelRepository.save(existedLabel);
                    return ResponseVO.buildSuccess(existedLabel);
                } else {
                    if (domainLabelVO.getId() == 0) {
                        domainLabelRepository.save(d);
                        return ResponseVO.buildSuccess(d);
                    } else {
                        return ResponseVO.buildFailure("the domain label to update does not exist");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("note failed");
        }

    }


    public ResponseVO deleteVertexLabel(VertexLabelVO vertexLabelVO) {
        try {
            VertexLabel vertexLabel = vertexLabelRepository.findVertexLabelById(vertexLabelVO.getId());

            if (vertexLabel == null) {
                return ResponseVO.buildFailure("no such label");
            } else {
                if (vertexLabel.getUserId() != vertexLabelVO.getUserId() || vertexLabel.getCodeId() != vertexLabelVO.getCodeId()) {
                    return ResponseVO.buildFailure("label match error");
                }
                System.out.println(vertexLabelVO.getId());
                vertexLabelRepository.deleteVertexLabelById(vertexLabelVO.getId());
                return ResponseVO.buildSuccess("delete label successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("delete failed");
        }

    }

    public ResponseVO deleteEdgeLabel(EdgeLabelVO edgeLabelVO) {
        try {
            EdgeLabel edgeLabel = edgeLabelRepository.findEdgeLabelById(edgeLabelVO.getId());

            if (edgeLabel == null) {
                return ResponseVO.buildFailure("no such label");
            } else {
                if (edgeLabel.getUserId() != edgeLabelVO.getUserId() || edgeLabel.getCodeId() != edgeLabelVO.getCodeId()) {
                    return ResponseVO.buildFailure("label match error");
                }
                System.out.println(edgeLabelVO.getId());
                edgeLabelRepository.deleteEdgeLabelById(edgeLabelVO.getId());
                return ResponseVO.buildSuccess("delete label successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("delete failed");
        }


    }

    public ResponseVO deleteDomainLabel(DomainLabelVO domainLabelVO) {
        try {
            DomainLabel domainLabel = domainLabelRepository.findDomainLabelById(domainLabelVO.getId());
            if (domainLabel == null) {
                return ResponseVO.buildFailure("no such label");
            } else {
                if (domainLabel.getUserId() != domainLabelVO.getUserId() || domainLabel.getCodeId() != domainLabelVO.getCodeId()) {
                    return ResponseVO.buildFailure("label match error");
                }
                domainLabelRepository.deleteDomainLabelById(domainLabelVO.getId());
                return ResponseVO.buildSuccess("delete label successfully");
            }
        } catch (Exception e) {
            return ResponseVO.buildFailure("delete failed");
        }

    }


    public ResponseVO getOneVertexLabels(VertexLabelVO vertexLabelVO) {

        try {
            List<VertexLabel> vertexLabels = vertexLabelRepository.
                    findVertexLabelsByCodeIdAndUserIdAndVertexId(vertexLabelVO.getCodeId(), vertexLabelVO.getUserId(), vertexLabelVO.getVertexId());
            return ResponseVO.buildSuccess(vertexLabels);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("get failed");
        }
    }

    public ResponseVO getOneEdgeLabels(EdgeLabelVO edgeLabelVO) {
        try {
            List<EdgeLabel> edgeLabels = edgeLabelRepository.
                    findEdgeLabelsByCodeIdAndUserIdAndEdgeId(edgeLabelVO.getCodeId(), edgeLabelVO.getUserId(), edgeLabelVO.getEdgeId());
            return ResponseVO.buildSuccess(edgeLabels);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("get failed");
        }
    }

    public ResponseVO getOneDomainLabels(DomainLabelVO domainLabelVO) {
        try {
            List<DomainLabel> domainLabel = domainLabelRepository.findDomainLabelsByCodeIdAndUserIdAndFirstEdgeIdAndNumOfVertex(
                    domainLabelVO.getCodeId(), domainLabelVO.getUserId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex());
            return ResponseVO.buildSuccess(domainLabel);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("get failed");
        }
    }


//    public ResponseVO getAllVertexLabel(UserAndCodeForm userAndCodeForm) {
//        ArrayList<VertexLabel> res = (ArrayList<VertexLabel>) vertexLabelRepository.
//                findVertexLabelsByCodeIdAndUserId(userAndCodeForm.getCodeId(), userAndCodeForm.getUserId());
//        return ResponseVO.buildSuccess(res);
//    }
//
//    public ResponseVO getAllEdgeLabel(UserAndCodeForm userAndCodeForm) {
//        ArrayList<EdgeLabel> res = (ArrayList<EdgeLabel>) edgeLabelRepository.findEdgeLabelsByCodeIdAndUserId(userAndCodeForm.getCodeId(), userAndCodeForm.getUserId());
//        return ResponseVO.buildSuccess(res);
//    }
//
//    public ResponseVO getAllDomainLabel(UserAndCodeForm userAndCodeForm) {
//        ArrayList<DomainLabel> res = (ArrayList<DomainLabel>) domainLabelRepository.findDomainLabelsByCodeIdAndUserId(userAndCodeForm.getCodeId(), userAndCodeForm.getUserId());
//        return ResponseVO.buildSuccess(res);
//    }


}
