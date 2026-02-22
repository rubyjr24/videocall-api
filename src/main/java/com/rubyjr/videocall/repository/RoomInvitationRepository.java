package com.rubyjr.videocall.repository;

import com.rubyjr.videocall.model.RoomInvitation;
import com.rubyjr.videocall.model.RoomInvitationPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInvitationRepository extends JpaRepository<RoomInvitation, RoomInvitationPK> {

}