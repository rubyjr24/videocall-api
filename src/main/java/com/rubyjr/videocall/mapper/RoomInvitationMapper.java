package com.rubyjr.videocall.mapper;


import com.rubyjr.videocall.dto.RoomInvitationDto;
import com.rubyjr.videocall.model.RoomInvitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomInvitationMapper {

    @Mapping(source = "room", target = "room")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "joinedAt", target = "joinedAt")
    RoomInvitationDto toDto(RoomInvitation invitation);

    @Mapping(source = "room", target = "room")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "joinedAt", target = "joinedAt")
    RoomInvitation toEntity(RoomInvitationDto dto);

}
