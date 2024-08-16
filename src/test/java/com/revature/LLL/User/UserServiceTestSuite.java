package com.revature.LLL.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTestSuite {
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserService mockUserService;

    private static User testUser = new User(5, "Jack", "McDonald", "jack@mail.com", "jackpw", User.userType.OWNER);

    @Test
    public void testFindByEmailAndPassword(){
        when(mockUserRepository.findByEmailAndPassword(testUser.getEmail(), testUser.getPassword()))
                .thenReturn(Optional.of(testUser));

        final User[] result = new User[1];
        assertDoesNotThrow(()-> result[0] = mockUserService.findByEmailAndPassword(testUser.getEmail(), testUser.getPassword()));

        assertEquals(testUser, result[0]);

        verify(mockUserRepository, times(1)).findByEmailAndPassword(testUser.getEmail(), testUser.getPassword());    }
}