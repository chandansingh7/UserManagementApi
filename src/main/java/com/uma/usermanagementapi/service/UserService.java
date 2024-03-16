package com.uma.usermanagementapi.service;

import com.uma.usermanagementapi.domain.User;
import com.uma.usermanagementapi.handler.ExceptionHandler;
import com.uma.usermanagementapi.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) throws ExceptionHandler{
        try {
            return userRepository.getReferenceById(id);
        } catch (ExceptionHandler exceptionHandler){
            throw  new ExceptionHandler("error while calling user" + exceptionHandler);
        }
    }

    public User getUserByUserDetails(User user){
        return user;
        //User user1 = userRepository.getReferenceById(user.getEmail());
    }
    public User saveUser(User user){
        try {
            return userRepository.save(user);
        }catch (ExceptionHandler exceptionHandler){
            throw  new ExceptionHandler("error while creating user" + exceptionHandler);
        }
    }

    public List<User> userList(){
        return userRepository.findAll();
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}
