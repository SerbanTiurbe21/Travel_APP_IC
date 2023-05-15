package com.example.travelApp;

import com.example.travelApp.entities.User;
import com.example.travelApp.repositories.UserRepository;
import com.example.travelApp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp(){
        user = new User();
    }

    private void setData(){
        user.setId(1);
        user.setUserId("User1");
        user.setName("Denard");
        user.setPassword("denard2001");
        user.setEmail("denardlaza@yahoo.com");
    }

    @BeforeEach
    public void tearDown(){
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldInsertCorrectlyWhenValidDataIsProvided(){
        setData();
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.save(user);
        assertEquals(user, createdUser);
    }

    @Test
    public void shouldDeleteFromDatabaseWhenCorrectIdIsPassed(){
        setData();
        int id = user.getId();
        doAnswer((Answer<Void>) invocation -> null).when(userRepository).deleteById(anyInt());
        userService.deleteById(id);
        verify(userRepository, times(1)).deleteById(anyInt());
    }

//    @Test
//    public void shouldUpdateUserWhenCorrectUserArgumentIsPassed(){
//        setData();
//        User updatedUser = new User(1, "testId", "updatedName", "updatedPass", "updated@test.com");
//        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
//        User result = userService.updateUser(user);
//        assertEquals(updatedUser, result);
//    }

    @Test
    public void shouldReturnUserWhenCorrectEmailIsProvided(){
        setData();
        String email = "denardlaza@yahoo.com";
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
        Optional<User> result = userService.findByEmail(email);
        assertEquals(optionalUser, result);
    }

    @Test
    public void shouldReturnUserWhenCorrectUsernameIsProvided(){
        setData();
        String username = "Denard";
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByName(anyString())).thenReturn(optionalUser);
        Optional<User> result = userService.findByName(username);
        assertEquals(optionalUser, result);
    }

    @Test
    public void shouldReturnUserWhenCorrectIdIsProvided(){
        setData();
        int id = 1;
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        Optional<User> result = userService.findById(id);
        assertEquals(optionalUser, result);
    }
}