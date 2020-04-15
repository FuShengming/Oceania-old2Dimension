package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.EdgeLabel;
import com.old2dimension.OCEANIA.po.VertexLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EdgeLabelRepository extends JpaRepository<EdgeLabel, Integer> {
    List<EdgeLabel> findEdgeLabelsByCodeId(int codeId);

    List<EdgeLabel> findEdgeLabelsByCodeIdAndUserIdAndEdgeId(int codeId, int userId, int edgeId);

    @Modifying
    @Transactional
    void deleteEdgeLabelById(int id);


    List<EdgeLabel> findEdgeLabelsByCodeIdAndUserId(int codeId, int userId);

    EdgeLabel findEdgeLabelById(int id);
}
