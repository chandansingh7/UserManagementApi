package com.uma.usermanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.usermanagementapi.domain.User;
import com.uma.usermanagementapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenPostSaveUser_thenCreateUser() throws Exception {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        given(userService.saveUser(user)).willReturn(user);

        mockMvc.perform(post("/api/v1/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(result -> System.out.println(result.getResponse().getContentAsString()));
    }

    @Test
    public void whenGetUserById_thenUserShouldBeReturned() throws Exception {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        given(userService.getUser(1L)).willReturn(user);

        mockMvc.perform(get("/api/v1/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()));
    }

    @Test
    public void whenGetUserList_thenAllUsersShouldBeReturned() throws Exception {
        List<User> userList = Arrays.asList(
                new User(1L, "John", "Doe", "john.doe@example.com", "1234567890"),
                new User(2L, "Jane", "Doe", "jane.doe@example.com", "0987654321")
        );
        given(userService.userList()).willReturn(userList);

        mockMvc.perform(get("/api/v1/getAll"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.size()").value(userList.size()));
    }

    @Test
    public void whenDeleteUser_thenUserShouldBeDeleted() throws Exception {
        long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/v1/{id}", userId))
                .andExpect(status().isOk());
    }
}
