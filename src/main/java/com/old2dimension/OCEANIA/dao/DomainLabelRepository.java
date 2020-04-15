package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.DomainLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DomainLabelRepository extends JpaRepository<DomainLabel, Integer> {
    List<DomainLabel> findDomainLabelsByCodeIdAndUserIdAndFirstEdgeIdAndNumOfVertex(int codeId, int userId, int firstEdgeId, int numOfVertex);


    List<DomainLabel> findDomainLabelsByCodeId(int codeId);

    @Modifying
    @Transactional
    void deleteDomainLabelById(int id);

    DomainLabel findDomainLabelById(int id);

    List<DomainLabel> findDomainLabelsByCodeIdAndUserId(int codeId, int userId);
}
