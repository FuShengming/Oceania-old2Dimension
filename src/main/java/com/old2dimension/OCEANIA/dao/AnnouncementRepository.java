package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
    List<Announcement> findAnnouncementsByGroupId(int groupId);
    Announcement findAnnouncementById(int id);
}
