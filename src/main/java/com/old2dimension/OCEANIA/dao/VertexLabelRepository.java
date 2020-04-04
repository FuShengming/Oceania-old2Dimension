package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.VertexLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VertexLabelRepository extends JpaRepository<VertexLabel, Integer> {
    VertexLabel findVertexLabelByCodeIdAndVertexId(int codeId, int vertexId);

    void deleteVertexLabelByCodeIdAndVertexId(int codeId, int vertexId);

    List<VertexLabel> findVertexLabelsByCodeId(int codeId);


}
