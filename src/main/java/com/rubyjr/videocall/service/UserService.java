package com.rubyjr.videocall.service;

import com.rubyjr.videocall.dto.UserDto;
import com.rubyjr.videocall.dto.requests.ContactRequest;
import com.rubyjr.videocall.exceptions.EntityAlreadyExistsException;
import com.rubyjr.videocall.exceptions.ResourceNotFoundException;
import com.rubyjr.videocall.exceptions.UserNotFoundException;
import com.rubyjr.videocall.mapper.UserMapper;
import com.rubyjr.videocall.model.User;
import com.rubyjr.videocall.model.UserFavorite;
import com.rubyjr.videocall.model.UserFavoritePK;
import com.rubyjr.videocall.repository.UserFavoriteRepository;
import com.rubyjr.videocall.repository.UserRepository;
import com.rubyjr.videocall.utilities.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Autowired
    private UserMapper userMapper;

    public void deleteAccount(Long userId){

        Optional<User> userOptional = this.userRepository.findById(userId);

        Assert.ifCondition(userOptional.isEmpty(), new RuntimeException()); //cambiar

        this.userRepository.delete(userOptional.get());
    }

    public List<UserDto> getContacts(Long userId){

        Optional<User> userOptional = this.userRepository.findByIdFetchingUserFavorites(userId);

        return userOptional.map(user -> user.getUserFavoriteListUser()
                .stream()
                    .map(userFavorite -> userMapper.toDto(userFavorite.getUserFavorite()))
                    .toList())
                .orElseGet(List::of);

    }

    public UserDto setContact(ContactRequest contactRequest, Long userId){

        Optional<User> userOptional = this.userRepository.findByEmail(contactRequest.getEmail());

        Assert.ifCondition(userOptional.isEmpty(), new UserNotFoundException("The user is not found")); // Cambiar

        User userToAssign = userOptional.get();

        UserFavoritePK userFavoritePK = new UserFavoritePK(userId, userToAssign.getId());

        Assert.ifCondition(userFavoriteRepository.existsById(userFavoritePK), new EntityAlreadyExistsException("The resource you are trying to create already exists in the database"));

        UserFavorite userFavorite = new UserFavorite(userFavoritePK);
        this.userFavoriteRepository.save(userFavorite);

        return userMapper.toDto(userToAssign);

    }

    public void deleteContact(Long userFavoriteId, Long userId){

        UserFavoritePK userFavoritePK = new UserFavoritePK(userId, userFavoriteId);

        Assert.ifCondition(!userFavoriteRepository.existsById(userFavoritePK), new ResourceNotFoundException("The resource you are trying to delete isn`t in the database"));

        UserFavorite userFavorite = new UserFavorite(userFavoritePK);
        this.userFavoriteRepository.delete(userFavorite);

    }

}
