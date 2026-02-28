package com.rubyjr.videocall.repository;

import com.rubyjr.videocall.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
        SELECT DISTINCT r
        FROM Room r
            JOIN r.roomInvitationList ri
        WHERE ri.user.id = :user
            AND ri.isOwner = true
    """)
    Optional<List<Room>> findAllRoomsByUserIdFechingRoomInvitations(@Param("user") Long user);

}