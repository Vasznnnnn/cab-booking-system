package com.myapps.cabs.service;

import com.myapps.cabs.model.User;
import com.myapps.cabs.model.UserAddress;


public interface UserService {

    boolean authenticateUser(User user);

    boolean processOtp(String otp, String email);

    boolean saveUserLocation(UserAddress address);

}
