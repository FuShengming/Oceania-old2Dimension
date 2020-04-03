package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.EdgeLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeLabelRepository extends JpaRepository<EdgeLabel, Integer>  {
    EdgeLabel findEdgeLabelByCodeIdAndEdgeId(int codeId, int edgeId);

    void deleteEdgeLabelByCodeIdAndEdgeId(int codeId, int edgeId);

    List<EdgeLabel> findEdgeLabelsByCodeId(int codeId);
}
