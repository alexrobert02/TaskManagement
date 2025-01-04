package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.dto.UserCreationRequestDto;
import org.example.taskmanagement.dto.UserDto;
import org.example.taskmanagement.mapper.UserMapper;
import org.example.taskmanagement.model.User;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Create a new user
    public User createUser(UserCreationRequestDto userCreationRequestDto) {
        User user = userMapper.toUser(userCreationRequestDto);
        //return userMapper.toUserDto(userCreated);
        return userRepository.save(user);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update user information
    public User updateUser(Long userId, User updatedUser) {
        // Check if the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(updatedUser.getEmail());

        // Save the updated user
        return userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User convertToEntity(UserDto userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .build();
    }
}

