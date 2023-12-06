package com.myapps.cabs.controller;

import com.myapps.cabs.exception.UserException;
import com.myapps.cabs.model.User;
import com.myapps.cabs.model.UserAddress;
import com.myapps.cabs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.myapps.cabs.constants.Constants.INVALID_USER;


@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.POST, value = "/auth", consumes = "application/json")
    public ResponseEntity<Boolean> authenticateUserLogin(@RequestBody User user) {
        try {
            if (user != null) {
                return new ResponseEntity<>(userService.authenticateUser(user), HttpStatus.OK);
            }

        } catch (UserException exception) {
            throw new UserException(INVALID_USER, exception);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/processOtp", consumes = "application/json")
    public ResponseEntity<Boolean> process(@RequestParam(name = "otp") String otp, @RequestParam("userEmail") String userEmail) {
        try {
            if (otp != null) {
                return new ResponseEntity<>(userService.processOtp(otp, userEmail), HttpStatus.OK);
            }

        } catch (UserException exception) {
            throw new UserException(INVALID_USER, exception);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserLocation", consumes = "application/json")
    public ResponseEntity<Boolean> process(@RequestBody UserAddress address) {
        try {
            if (address != null) {
                return new ResponseEntity<>(userService.saveUserLocation(address), HttpStatus.OK);
            }

        } catch (UserException exception) {
            throw new UserException(INVALID_USER, exception);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

