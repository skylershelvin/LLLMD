package com.revature.LLL.User;

import com.revature.LLL.User.dtos.UserResponseDTO;
import com.revature.LLL.util.exceptions.DataNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTestSuite {
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserService mockUserService;

    private static User testUser = new User(5, "Jack", "McDonald", "jack@mail.com", "jackpw");

    @Test
    public void testFindByEmailAndPassword(){
        when(mockUserRepository.findByEmailAndPassword(testUser.getEmail(), testUser.getPassword()))
                .thenReturn(Optional.of(testUser));

        final User[] result = new User[1];
        assertDoesNotThrow(()-> result[0] = mockUserService.findByEmailAndPassword(testUser.getEmail(), testUser.getPassword()));

        assertEquals(testUser, result[0]);

        verify(mockUserRepository, times(1)).findByEmailAndPassword(testUser.getEmail(), testUser.getPassword());    }

    @Test
    public void whenFindAllFarmersThenReturnListOfUserResponseDTO() {
        List<User> farmers = List.of(new User(1, "John", "Doe", "john@example.com", "password"));
        when(mockUserRepository.findAll()).thenReturn(farmers);
        List<UserResponseDTO> result = mockUserService.findAllFarmers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockUserRepository, times(1)).findAll();
    }

    @Test
    public void whenFindAllFarmersThenThrowDataNotFoundException() {
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(DataNotFoundException.class, () -> mockUserService.findAllFarmers());
        verify(mockUserRepository, times(1)).findAll();
    }
}

