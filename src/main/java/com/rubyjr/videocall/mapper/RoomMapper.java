package com.rubyjr.videocall.mapper;

import com.rubyjr.videocall.dto.RoomDto;
import com.rubyjr.videocall.dto.UserDto;
import com.rubyjr.videocall.model.Room;
import com.rubyjr.videocall.model.RoomInvitation;
import com.rubyjr.videocall.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface RoomMapper {

    @Mapping(source = "id", target = "roomId")
    @Mapping(source = "roomInvitationsList", target = "userInvitations")
    RoomDto toDto(Room room);

    default UserDto map(RoomInvitation invitation) {
        return invitation == null ? null :
                toUserDto(invitation.getUser());
    }

    @Mapping(source = "id", target = "userId")
    UserDto toUserDto(User user);
}