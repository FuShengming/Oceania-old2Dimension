package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CodeRepository extends JpaRepository<Code,Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into code values(?1,1,\'iTrust\',1982,3841,63)",nativeQuery = true)
    void saveDefaultCode(int userId);
}
