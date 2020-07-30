package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceRepository extends JpaRepository<WorkSpace, Integer> {
    @Query(value = "select * from work_space where user_id = ?1  and code_id=?2 order by work_space_date desc limit 1", nativeQuery = true)
    public WorkSpace findLatestWorkSpace(int userId, int codeId);

}
