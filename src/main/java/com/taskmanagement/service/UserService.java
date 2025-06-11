package com.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import com.taskmanagement.dto.UserCreationRequestDto;
import com.taskmanagement.mapper.UserMapper;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(UserCreationRequestDto userCreationRequestDto) {
        User user = userMapper.toUser(userCreationRequestDto);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

