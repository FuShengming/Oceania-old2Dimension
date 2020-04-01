package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.GraphCalculateBL;
import com.old2dimension.OCEANIA.bl.PathBL;
import com.old2dimension.OCEANIA.po.Closeness;
import com.old2dimension.OCEANIA.vo.VertexVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/graph")
public class GraphCalculateController {
    @Autowired
    GraphCalculateBL graphCalculateBL;
    @Autowired
    PathBL pathBL;


    @RequestMapping(value = "/getGraph" ,method = RequestMethod.POST)
    public ResponseVO getGraphByUserIdAndCodeId(@RequestBody UserAndCodeForm userAndCodeForm){
        return null;
    }

    @RequestMapping(value = "/filter" ,method = RequestMethod.POST)
    public ResponseVO filterByCloseness(@RequestBody Closeness closeness){
        return null;
    }

    @RequestMapping(value ="/findVertex/{functionName}" ,method = RequestMethod.GET)
    public ResponseVO findVertex(@PathVariable("functionName") String functionName){
        return null;
    }

    @RequestMapping(value = "/findPath",method = RequestMethod.POST)
    public ResponseVO findPath(@RequestBody List<VertexVO> vertexVOs){
        return null;
    }




}
