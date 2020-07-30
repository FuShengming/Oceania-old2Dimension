package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.VertexLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VertexLabelRepository extends JpaRepository<VertexLabel, Integer> {
    List<VertexLabel> findVertexLabelsByCodeIdAndUserIdAndVertexId(int codeId, int userId, int vertexId);

    @Modifying
    @Transactional
    void deleteVertexLabelById(int id);


    List<VertexLabel> findVertexLabelsByCodeIdAndUserId(int codeId, int userId);

    List<VertexLabel> findVertexLabelsByCodeId(int codeId);

    VertexLabel findVertexLabelById(int id);

    int countByUserIdAndCodeId(int userId,int codeId);




    List<VertexLabel> findVertexLabelsByCodeIdAndVertexId(int codeId, int vertexId);
}
