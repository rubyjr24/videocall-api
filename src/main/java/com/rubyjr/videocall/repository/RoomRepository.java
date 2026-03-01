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
        WHERE ri.id.userId = :userId
            AND ri.isOwner = :isOwner
    """)
    Optional<List<Room>> findAllRoomsByUserIdFechingRoomInvitations(@Param("userId") Long userId, @Param("isOwner") boolean isOwner);

    @Query("""
        SELECT DISTINCT r
        FROM Room r
            JOIN r.roomInvitationList ri
        WHERE r.id = :roomId
    """)
    Optional<Room> findByIdFechingRoomInvitations(@Param("roomId") Long roomId);

}