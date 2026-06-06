package com.rubyjr.videocall.repository;

import com.rubyjr.videocall.model.RoomInvitation;
import com.rubyjr.videocall.model.RoomInvitationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomInvitationRepository extends JpaRepository<RoomInvitation, RoomInvitationPK> {

    @Query("SELECT e FROM RoomInvitation e WHERE e.id.userId = :userId AND e.leftAt IS NULL")
    List<RoomInvitation> findByLeftAt(@Param("userId") Long userId);

}