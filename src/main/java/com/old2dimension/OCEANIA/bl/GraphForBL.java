package com.old2dimension.OCEANIA.bl;

import com.old2dimension.OCEANIA.po.AdjacencyMatrix;
import org.springframework.stereotype.Component;

@Component
public interface GraphForBL {

    public AdjacencyMatrix getAdjacencyMatrix();
}
