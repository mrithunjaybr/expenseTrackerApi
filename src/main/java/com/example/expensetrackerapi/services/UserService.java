package com.example.expensetrackerapi.services;

import com.example.expensetrackerapi.domain.User;
import com.example.expensetrackerapi.exception.EtAuthException;

public interface UserService {
    User validateUser(String email,String password) throws  EtAuthException;

    User registerUser(String firstName,String lastName, String email, String password) throws EtAuthException;
}
