package com.taskmanagement.service;

import com.taskmanagement.dto.UserCreationRequestDto;
import com.taskmanagement.mapper.UserMapper;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserCreationRequestDto userCreationRequestDto;

    @BeforeEach
    void setUp() {
        user = new User(1L, "john.doe@example.com");
        userCreationRequestDto = new UserCreationRequestDto("john.doe@example.com");
    }

    @Test
    void createUser_shouldReturnUser_whenUserIsCreated() {
        when(userMapper.toUser(userCreationRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(userCreationRequestDto);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getAllUsers_shouldReturnUsersList_whenUsersExist() {
        List<User> users = Arrays.asList(user);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(users.size(), result.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getEmail(), result.get().getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_shouldReturnEmptyOptional_whenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1L);

        assertFalse(result.isPresent());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExists() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_shouldNotDeleteUser_whenUserDoesNotExist() {
        doThrow(new IllegalArgumentException("User not found")).when(userRepository).deleteById(1L);

        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }
}
