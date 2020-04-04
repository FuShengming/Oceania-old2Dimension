package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.StatisticsBL;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    StatisticsBL statisticsBL;

    @RequestMapping("/countUser")
    public int getNumOfUser() {
        return statisticsBL.getNumOfUser();
    }

    @RequestMapping("/countCode")
    public Map<Integer, Integer> getNumOfCode() {
        return statisticsBL.getNumOfCode();
    }

    @RequestMapping("/getCodeMes/{codeId}")
    public int[] getCodeMes(@PathVariable("codeId") int codeId){
        return statisticsBL.getCodeMes(codeId);
    }

}
