package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.DomainLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomainLabelRepository extends JpaRepository<DomainLabel, Integer> {
    DomainLabel findDomainLabelByUserIdAndCodeIdAndFirstEdgeIdAndNumOfVertex(int userId, int codeId, int firstEdgeId, int numOfVertex);

    List<DomainLabel> findDomainLabelsByUserIdAndCodeId(int userId, int codeId);
}
