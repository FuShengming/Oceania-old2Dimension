package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.GraphCalculateBL;
import com.old2dimension.OCEANIA.bl.PathBL;
import com.old2dimension.OCEANIA.blImpl.GraphCalculateImpl;
import com.old2dimension.OCEANIA.blImpl.PathBLImpl;
import com.old2dimension.OCEANIA.po.Closeness;
import com.old2dimension.OCEANIA.vo.VertexVO;
import com.old2dimension.OCEANIA.vo.ResponseVO;
import com.old2dimension.OCEANIA.vo.UserAndCodeForm;
import com.old2dimension.OCEANIA.vo.WeightForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/graph")
public class GraphCalculateController {
    @Autowired
    GraphCalculateBL graphCalculateBL;
    @Autowired
    PathBL pathBL;


    @RequestMapping(value = "/getGraph", method = RequestMethod.POST)
    public ResponseVO getGraphByUserIdAndCodeId(@RequestBody UserAndCodeForm userAndCodeForm) {
        return graphCalculateBL.getGraph(userAndCodeForm);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseVO filterByCloseness(@RequestBody ArrayList<WeightForm> weightForms) {
        return graphCalculateBL.filterByWeightForm(weightForms);
    }

    @RequestMapping(value = "/findVertex/{functionName}", method = RequestMethod.GET)
    public ResponseVO findVertex(@PathVariable("functionName") String functionName) {
        return graphCalculateBL.getAmbiguousFuncInfos(functionName);
    }

    @RequestMapping(value = "/findPath", method = RequestMethod.POST)
    public ResponseVO findPath(@RequestBody List<VertexVO> vertexVOs) {
        pathBL = new PathBLImpl((GraphCalculateImpl) graphCalculateBL);
        return pathBL.findPath(vertexVOs.get(0), vertexVOs.get(1));

    }


}
