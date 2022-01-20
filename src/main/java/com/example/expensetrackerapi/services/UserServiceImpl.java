package com.example.expensetrackerapi.services;

import com.example.expensetrackerapi.domain.User;
import com.example.expensetrackerapi.exception.EtAuthException;
import com.example.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        email = patternChecker(email);
        Integer count = userRepository.getCountByEmail(email);
        if(count!=1){
            throw new EtAuthException("Email-Id not registered!");
        }
        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        email = patternChecker(email);
        Integer count = userRepository.getCountByEmail(email);
        if(count>0){
            throw new EtAuthException("Email already in Use");
        }
        Integer userId = userRepository.create( firstName,lastName,email,password);
        return userRepository.findById(userId);

    }

    private String patternChecker(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email !=null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches()){
            throw new EtAuthException("Invalid email format");
        }
        return email;
    }
}
