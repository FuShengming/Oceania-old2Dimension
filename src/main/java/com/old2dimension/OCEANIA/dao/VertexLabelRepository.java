package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.VertexLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VertexLabelRepository extends JpaRepository<VertexLabel, Integer> {
    VertexLabel findVertexLabelByUserIdAndCodeIdAndVertexId(int userId, int codeId, int vertexId);

    List<VertexLabel> findVertexLabelsByUserIdAndCodeId(int userId, int codeId);
}
