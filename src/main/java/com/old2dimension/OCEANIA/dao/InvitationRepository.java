package com.old2dimension.OCEANIA.dao;

import com.old2dimension.OCEANIA.po.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InvitationRepository extends JpaRepository<Invitation,Integer> {
    List<Invitation> findInvitationsByUserIdAndHasRead(int userId,int hasRead);
    Invitation findInvitationById(int id);
    List<Invitation> findInvitationsByUserId(int userId);
    int countInvitationsByUserIdAndHasRead(int userId,int hasRead);

}
