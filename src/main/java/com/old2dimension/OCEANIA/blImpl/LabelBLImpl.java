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


    public ResponseVO noteVertex(VertexLabelVO vertexLabelVO) {
        VertexLabel vertexLabel = vertexLabelRepository.findVertexLabelByUserIdAndCodeIdAndVertexId(
                vertexLabelVO.getUserId(), vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId());
        vertexLabel.setContent(vertexLabelVO.getContent());
        vertexLabelRepository.save(vertexLabel);

        System.out.println("成功注释点");
        return ResponseVO.buildSuccess("create/update VertexLabel successfully");
    }

    public ResponseVO noteEdge(EdgeLabelVO edgeLabelVO) {
        EdgeLabel edgeLabel = edgeLabelRepository.findEdgeLabelByUserIdAndCodeIdAndEdgeId(
                edgeLabelVO.getUserId(), edgeLabelVO.getCodeId(), edgeLabelVO.getEdgeId());
        edgeLabel.setContent(edgeLabelVO.getContent());
        edgeLabelRepository.save(edgeLabel);

        System.out.println("成功注释边");
        return ResponseVO.buildSuccess("create/update EdgeLabel successfully");
    }

    public ResponseVO noteDomain(DomainLabelVO domainLabelVO) {
        DomainLabel domainLabel = domainLabelRepository.findDomainLabelByUserIdAndCodeIdAndFirstEdgeIdAndNumOfVertex(
                domainLabelVO.getUserId(), domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex());
        domainLabel.setContent(domainLabelVO.getContent());
        domainLabelRepository.save(domainLabel);

        System.out.println("成功注释域");
        return ResponseVO.buildSuccess("create/update DomainLabel successfully");
    }


    public ResponseVO deleteVertexLabel(VertexLabelVO vertexLabelVO) {
        if (vertexLabelVO.getContent() == null) {
            return ResponseVO.buildFailure("The vertex has no label");
        } else {
            VertexLabel vertexLabel = vertexLabelRepository.findVertexLabelByUserIdAndCodeIdAndVertexId(
                    vertexLabelVO.getUserId(), vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId());
            vertexLabel.setContent(null);
            vertexLabelRepository.save(vertexLabel);

            System.out.println("成功删除点注释");
            return ResponseVO.buildSuccess("delete VertexLabel successfully");
        }
    }

    public ResponseVO deleteEdgeLabel(EdgeLabelVO edgeLabelVO) {
        if (edgeLabelVO.getContent() == null) {
            return ResponseVO.buildFailure("The edge has no label");
        } else {
            EdgeLabel edgeLabel = edgeLabelRepository.findEdgeLabelByUserIdAndCodeIdAndEdgeId(
                    edgeLabelVO.getUserId(), edgeLabelVO.getCodeId(), edgeLabelVO.getEdgeId());
            edgeLabel.setContent(null);
            edgeLabelRepository.save(edgeLabel);

            System.out.println("成功删除边注释");
            return ResponseVO.buildSuccess("delete EdgeLabel successfully");
        }
    }

    public ResponseVO deleteDomainLabel(DomainLabelVO domainLabelVO) {
        if (domainLabelVO.getContent() == null) {
            return ResponseVO.buildFailure("The domain has no label");
        } else {
            DomainLabel domainLabel = domainLabelRepository.findDomainLabelByUserIdAndCodeIdAndFirstEdgeIdAndNumOfVertex(
                    domainLabelVO.getUserId(), domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex());
            domainLabel.setContent(null);
            domainLabelRepository.save(domainLabel);

            System.out.println("成功删除域注释");
            return ResponseVO.buildSuccess("delete DomainLabel successfully");
        }
    }


    public ResponseVO getOneVertexLabel(VertexLabelVO vertexLabelVO) {
        VertexLabel vertexLabel = vertexLabelRepository.findVertexLabelByUserIdAndCodeIdAndVertexId(
                vertexLabelVO.getUserId(), vertexLabelVO.getCodeId(), vertexLabelVO.getVertexId());
        if (vertexLabel == null) {
            return ResponseVO.buildFailure("No such vertexLabel");
        } else {
            return ResponseVO.buildSuccess(vertexLabel);
        }
    }

    public ResponseVO getOneEdgeLabel(EdgeLabelVO edgeLabelVO) {
        EdgeLabel edgeLabel = edgeLabelRepository.findEdgeLabelByUserIdAndCodeIdAndEdgeId(
                edgeLabelVO.getUserId(), edgeLabelVO.getCodeId(), edgeLabelVO.getEdgeId());
        if (edgeLabel == null) {
            return ResponseVO.buildFailure("No such edgeLabel");
        } else {
            return ResponseVO.buildSuccess(edgeLabel);
        }
    }

    public ResponseVO getOneDomainLabel(DomainLabelVO domainLabelVO) {
        DomainLabel domainLabel = domainLabelRepository.findDomainLabelByUserIdAndCodeIdAndFirstEdgeIdAndNumOfVertex(
                domainLabelVO.getUserId(), domainLabelVO.getCodeId(), domainLabelVO.getFirstEdgeId(), domainLabelVO.getNumOfVertex());
        if (domainLabel == null) {
            return ResponseVO.buildFailure("No such domainLabel");
        } else {
            return ResponseVO.buildSuccess(domainLabel);
        }
    }


    public ResponseVO getAllVertexLabel(UserAndCodeForm userAndCodeForm) {
        ArrayList<VertexLabel> res = (ArrayList<VertexLabel>) vertexLabelRepository.findVertexLabelsByUserIdAndCodeId(
                userAndCodeForm.getUserId(), userAndCodeForm.getCodeId());
        System.out.println("所有点注释");
        return ResponseVO.buildSuccess(res);
    }

    public ResponseVO getAllEdgeLabel(UserAndCodeForm userAndCodeForm) {
        ArrayList<EdgeLabel> res = (ArrayList<EdgeLabel>) edgeLabelRepository.findEdgeLabelsByUserIdAndCodeId(
                userAndCodeForm.getUserId(), userAndCodeForm.getCodeId());
        System.out.println("所有边注释");
        return ResponseVO.buildSuccess(res);
    }

    public ResponseVO getAllDomainLabel(UserAndCodeForm userAndCodeForm) {
        ArrayList<DomainLabel> res = (ArrayList<DomainLabel>) domainLabelRepository.findDomainLabelsByUserIdAndCodeId(
                userAndCodeForm.getUserId(), userAndCodeForm.getCodeId());
        System.out.println("所有域注释");
        return ResponseVO.buildSuccess(res);
    }


}
