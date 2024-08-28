package com.revature.LLL.User;

import com.revature.LLL.User.dtos.UserResponseDTO;
import com.revature.LLL.util.exceptions.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTestSuite {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private UserController userController;

    private static final User testUser = new User(5, "Jack", "McDonald", "jack@mail.com", "jackpw");

    @Test
    public void whenGetUserByUserIdForVetsAndUserIsFoundThenReturnUser() {
        when(mockUserService.findById(testUser.getUserId())).thenReturn(testUser);

        ResponseEntity<User> response = userController.getUserByUserId(testUser.getUserId());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(testUser, response.getBody());
        verify(mockUserService, times(1)).findById(testUser.getUserId());
    }

    @Test
    public void whenGetUserByUserIdForVetsAndUserNotFoundThenThrowDataNotFoundException() {
        when(mockUserService.findById(testUser.getUserId())).thenThrow(new DataNotFoundException("No such user with that id found"));

        ResponseEntity<User> response = userController.getUserByUserId(testUser.getUserId());

        assertEquals(404, response.getStatusCode().value());
        verify(mockUserService, times(1)).findById(testUser.getUserId());
    }

    @Test
    public void whenGetUserByUserIdForVetsAndRequesterNotVetThenThrowUnauthorizedException() {
        ResponseEntity<User> response = userController.getUserByUserId(testUser.getUserId());

        assertEquals(403, response.getStatusCode().value());
        verify(mockUserService, times(0)).findById(testUser.getUserId());
    }

    @Test
    public void whenGetAllFarmersForVetsAndFarmersAreFoundThenReturnListOfUserResponseDTO() {
        List<UserResponseDTO> farmers = List.of(new UserResponseDTO(testUser));
        when(mockUserService.findAllFarmers()).thenReturn(farmers);

        ResponseEntity<List<UserResponseDTO>> response = userController.getAllFarmers();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(farmers, response.getBody());
        verify(mockUserService, times(1)).findAllFarmers();
    }

    @Test
    public void whenGetAllFarmersForVetsAndNoFarmersFoundThenThrowDataNotFoundException() {
        when(mockUserService.findAllFarmers()).thenThrow(new DataNotFoundException("No farmers found."));

        ResponseEntity<List<UserResponseDTO>> response = userController.getAllFarmers();

        assertEquals(404, response.getStatusCode().value());
        verify(mockUserService, times(1)).findAllFarmers();
    }

    @Test
    public void whenGetAllFarmersForVetsAndRequesterNotVetThenThrowUnauthorizedException() {
        ResponseEntity<List<UserResponseDTO>> response = userController.getAllFarmers();

        assertEquals(403, response.getStatusCode().value());
        verify(mockUserService, times(0)).findAllFarmers();
    }
}