package com.old2dimension.OCEANIA.bl;

import java.util.Map;

public interface StatisticsBL {

    int getNumOfUser();

    Map<Integer, Integer> getNumOfCode();

    int[] getCodeMes(int codeId);

}
