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

    @RequestMapping(value = "/noteVertex",method = RequestMethod.POST)
    public ResponseVO noteVertex(@RequestBody VertexLabelVO vertexLabelVO){
        return null;
    }

    @RequestMapping(value = "/noteEdge",method = RequestMethod.POST)
    public ResponseVO noteEdge(@RequestBody EdgeLabelVO vertexLabelVO){
        return null;
    }

    @RequestMapping(value = "/noteDomain",method = RequestMethod.POST)
    public ResponseVO noteDomain(@RequestBody DomainLabelVO vertexLabelVO){
        return null;
    }

    @RequestMapping(value = "/deleteVertexLabel",method = RequestMethod.POST)
    public ResponseVO deleteVertexLabel(@RequestBody VertexLabelVO vertexLabelVO){
        return null;
    }

    @RequestMapping(value = "/deleteEdgeLabel",method = RequestMethod.POST)
    public ResponseVO deleteEdgeLabel(@RequestBody EdgeLabelVO edgeLabelVO){
        return null;
    }

    @RequestMapping(value = "/deleteDomainLabel",method = RequestMethod.POST)
    public ResponseVO deleteDomainLabel(@RequestBody DomainLabelVO domainLabelVO){
        return null;
    }

    @RequestMapping(value = "/getOneVertexLabel",method = RequestMethod.POST)
    public ResponseVO getOneVertexLabel(@RequestBody VertexLabelVO vertexLabelVO){
        return null;
    }

    @RequestMapping(value = "/getOneEdgeLabel",method = RequestMethod.POST)
    public ResponseVO getOneEdgeLabel(@RequestBody EdgeLabelVO edgeLabelVO){
        return null;
    }

    @RequestMapping(value = "/getOneDomainLabel",method = RequestMethod.POST)
    public ResponseVO getOneDomainLabel(@RequestBody DomainLabelVO domainLabelVO){
        return null;
    }

    @RequestMapping(value = "/getAllVertexLabels",method = RequestMethod.POST)
    public ResponseVO getAllVertexLabels(@RequestBody UserAndCodeForm userAndCodeForm){
        return null;
    }

    @RequestMapping(value = "/getAllEdgeLabels",method = RequestMethod.POST)
    public ResponseVO getAllEdgeLabels(@RequestBody UserAndCodeForm userAndCodeForm){
        return null;
    }

    @RequestMapping(value = "/getAllDomainLabels",method = RequestMethod.POST)
    public ResponseVO getAllDomainLabels(@RequestBody UserAndCodeForm userAndCodeForm){
        return null;
    }


}
