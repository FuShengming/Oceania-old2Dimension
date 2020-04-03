package com.old2dimension.OCEANIA.controller;

import com.old2dimension.OCEANIA.bl.StatisticsBL;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    StatisticsBL statisticsBL;

    @RequestMapping("countUser")
    public int getNumOfUser() {
        return statisticsBL.getNumOfUser();
    }


}
