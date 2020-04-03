package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.DomainLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomainLabelRepository extends JpaRepository<DomainLabel, Integer> {
    DomainLabel findDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(int codeId, int firstEdgeId, int numOfVertex);

    void deleteDomainLabelByCodeIdAndFirstEdgeIdAndNumOfVertex(int codeId, int firstEdgeId, int numOfVertex);

    List<DomainLabel> findDomainLabelsByCodeId(int codeId);
}
