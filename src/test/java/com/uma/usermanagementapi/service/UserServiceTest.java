package com.uma.usermanagementapi.service;

import com.uma.usermanagementapi.domain.User;
import com.uma.usermanagementapi.handler.ExceptionHandler;
import com.uma.usermanagementapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetUser_thenRetrieveUser() throws ExceptionHandler {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        when(userRepository.getReferenceById(1L)).thenReturn(user);

        User found = userService.getUser(1L);

        assertNotNull(found);
        assertEquals("John", found.getFirstName());
        assertEquals("john.doe@example.com", found.getEmail());
    }

    @Test
    public void whenSaveUser_thenUserIsSaved() {
        User user = new User(1L, "Jane", "Doe", "jane.doe@example.com", "0987654321");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Jane", savedUser.getFirstName());
        assertEquals("jane.doe@example.com", savedUser.getEmail());
    }

    @Test
    public void whenUserList_thenReturnListOfUsers() {
        List<User> users = Arrays.asList(
                new User(1L, "User1", "Lastname1", "user1@example.com", "1111111111"),
                new User(2L, "User2", "Lastname2", "user2@example.com", "2222222222")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.userList();

        assertNotNull(userList);
        assertEquals(2, userList.size());
        assertEquals("User1", userList.get(0).getFirstName());
        assertEquals("user2@example.com", userList.get(1).getEmail());
    }

    @Test
    public void whenDeleteUser_thenUserIsDeleted() {
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }
}
