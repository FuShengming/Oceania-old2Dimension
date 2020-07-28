package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.GroupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupCodeRepository extends JpaRepository<GroupCode, Integer> {
    GroupCode findGroupCodeByCodeIdAndGroupId(int codeId,int groupId);
    List<GroupCode> findAllByGroupId(int groupId);
}
