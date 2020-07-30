package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.LabelBL;
import com.old2dimension.OCEANIA.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired
    LabelBL labelBL;

    public void setLabelBL(LabelBL labelBL) {
        this.labelBL = labelBL;
    }

    @RequestMapping(value = "/noteVertex", method = RequestMethod.POST)
    public ResponseVO noteVertex(@RequestBody VertexLabelVO vertexLabelVO) {
        return labelBL.noteVertex(vertexLabelVO);
    }

    @RequestMapping(value = "/noteEdge", method = RequestMethod.POST)
    public ResponseVO noteEdge(@RequestBody EdgeLabelVO edgeLabelVO) {
        return labelBL.noteEdge(edgeLabelVO);
    }

    @RequestMapping(value = "/noteDomain", method = RequestMethod.POST)
    public ResponseVO noteDomain(@RequestBody DomainLabelVO domainLabelVO) {
        return labelBL.noteDomain(domainLabelVO);
    }

    @RequestMapping(value = "/deleteVertexLabel", method = RequestMethod.POST)
    public ResponseVO deleteVertexLabel(@RequestBody VertexLabelVO vertexLabelVO) {
        return labelBL.deleteVertexLabel(vertexLabelVO);
    }

    @RequestMapping(value = "/deleteEdgeLabel", method = RequestMethod.POST)
    public ResponseVO deleteEdgeLabel(@RequestBody EdgeLabelVO edgeLabelVO) {
        return labelBL.deleteEdgeLabel(edgeLabelVO);
    }

    @RequestMapping(value = "/deleteDomainLabel", method = RequestMethod.POST)
    public ResponseVO deleteDomainLabel(@RequestBody DomainLabelVO domainLabelVO) {
        return labelBL.deleteDomainLabel(domainLabelVO);
    }

    @RequestMapping(value = "/getOneVertexLabel", method = RequestMethod.POST)
    public ResponseVO getOneVertexLabel(@RequestBody VertexLabelVO vertexLabelVO) {
        return labelBL.getOneVertexLabels(vertexLabelVO);
    }

    @RequestMapping(value = "/getOneEdgeLabel", method = RequestMethod.POST)
    public ResponseVO getOneEdgeLabel(@RequestBody EdgeLabelVO edgeLabelVO) {
        return labelBL.getOneEdgeLabels(edgeLabelVO);
    }

    @RequestMapping(value = "/getOneDomainLabel", method = RequestMethod.POST)
    public ResponseVO getOneDomainLabel(@RequestBody DomainLabelVO domainLabelVO) {
        return labelBL.getOneDomainLabels(domainLabelVO);
    }

//    @RequestMapping(value = "/getAllVertexLabels",method = RequestMethod.POST)
//    public ResponseVO getAllVertexLabels(@RequestBody UserAndCodeForm userAndCodeForm){
//        return labelBL.getAllVertexLabel(userAndCodeForm);
//    }
//
//    @RequestMapping(value = "/getAllEdgeLabels",method = RequestMethod.POST)
//    public ResponseVO getAllEdgeLabels(@RequestBody UserAndCodeForm userAndCodeForm){
//        return labelBL.getAllEdgeLabel(userAndCodeForm);
//    }
//
//    @RequestMapping(value = "/getAllDomainLabels",method = RequestMethod.POST)
//    public ResponseVO getAllDomainLabels(@RequestBody UserAndCodeForm userAndCodeForm){
//        return labelBL.getAllDomainLabel(userAndCodeForm);
//    }


}
