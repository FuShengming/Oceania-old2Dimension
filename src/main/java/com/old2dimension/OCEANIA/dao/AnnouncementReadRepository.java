package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.AnnouncementRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnnouncementReadRepository extends JpaRepository<AnnouncementRead,Integer> {
    List<AnnouncementRead> findAnnouncementReadsByUserIdAndHasRead(int userId,int hasRead);
    AnnouncementRead findAnnouncementReadByUserIdAndAnnouncementId(int userId,int announcementId);
}
