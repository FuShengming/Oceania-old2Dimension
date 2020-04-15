package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Code;
import com.old2dimension.OCEANIA.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {


    public Code findCodeByIdAndUserId(int id, int userId);

    List<Code> findCodesByUserId(int userId);

    public Code findCodeById(int id);

}
