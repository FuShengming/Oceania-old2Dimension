package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.vo.ResponseVO;

import java.util.Map;

public interface StatisticsBL {

    int getNumOfUser();

    Map<Integer, Integer> getNumOfCode();

    int[] getCodeMes(int codeId);

    ResponseVO getAllMes();
}
