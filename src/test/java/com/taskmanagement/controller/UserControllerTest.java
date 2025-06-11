package com.taskmanagement.controller;

import com.taskmanagement.model.User;
import com.taskmanagement.service.UserService;
import com.taskmanagement.dto.UserCreationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserCreationRequestDto userCreationRequestDto;
    private User user;

    @BeforeEach
    void setUp() {
        userCreationRequestDto = new UserCreationRequestDto();
        userCreationRequestDto.setEmail("testuser@example.com");

        user = new User();
        user.setId(1L);
        user.setEmail("testuser@example.com");
    }

    @Test
    void createUser_shouldReturnCreatedStatus() {
        when(userService.createUser(any(UserCreationRequestDto.class)))
                .thenReturn(user);

        ResponseEntity<User> response = userController.createUser(userCreationRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getId(), response.getBody().getId());
        assertEquals(user.getEmail(), response.getBody().getEmail());

        verify(userService, times(1)).createUser(any(UserCreationRequestDto.class));
    }

    @Test
    void getAllUsers_shouldReturnOk_whenUsersExist() {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getAllUsers_shouldReturnEmptyList_whenNoUsersExist() {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_shouldReturnOk_whenUserExists() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getId(), response.getBody().getId());
        assertEquals(user.getEmail(), response.getBody().getEmail());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserById_shouldReturnNotFound_whenUserDoesNotExist() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void deleteUser_shouldReturnNoContent_whenUserDeletedSuccessfully() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void deleteUser_shouldReturnNotFound_whenUserDoesNotExist() {
        doThrow(new RuntimeException()).when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userService, times(1)).deleteUser(1L);
    }
}

