package com.techelevator.controller;

import javax.validation.Valid;

import com.techelevator.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.dao.UserDao;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;

import java.security.Principal;
import java.util.List;
import java.util.Random;


@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);
        
        User user = userDao.findByUsername(loginDto.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponse(jwt, user), httpHeaders, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDTO newUser) {
        try {
            System.out.println("try "+ newUser);
            User user = userDao.findByUsername(newUser.getUsername());
            throw new UserAlreadyExistsException();
        } catch (UsernameNotFoundException e) {
            System.out.println("catch " + e + " newuser: "+ newUser);
            Random random = new Random();
            String family_id = String.valueOf(random.nextInt(100000000));
            userDao.create(family_id, newUser.getFirst_name(), newUser.getLast_name(), newUser.getEmail(), newUser.getUsername(), newUser.getPassword(), newUser.getRole());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/parent/dashboard/{id}", method = RequestMethod.POST)
    public void registerFamilyUser(@Valid @RequestBody RegisterFamilyUserDTO newUser, @PathVariable long id) {
//        try {
//            User user = userDao.findByUsername(newUser.getUsername());
//            throw new UserAlreadyExistsException();
//        } catch (UsernameNotFoundException e) {
        System.out.println(newUser + "  " + id);
            User user = userDao.getUserById(id);
            String family_id = user.getFamily_id();
            userDao.create(family_id, newUser.getFirst_name(), newUser.getLast_name(),
                    newUser.getEmail(), newUser.getUsername(), newUser.getPassword(), newUser.getRole());
        }
//    }

    @RequestMapping(value = "/parent/dashboard/{id}", method = RequestMethod.GET)
    public List<User> getFamilyUsers(@PathVariable Long id) {
        User user = userDao.getUserById(id);
        String family_id = user.getFamily_id();
        return userDao.getUsersByFamilyId(family_id);
    }

//    Is this needed?
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable String id) {
        return null;
    }

    @RequestMapping(value = "/user/dashboard/{id}", method = RequestMethod.GET)
    public List<UserDashInfo> getFamilyDashboardUsers(@PathVariable String id) {
        return userDao.getUserDashboardInfoByFamilyId(id);
    }



    /**
     * Object to return as body in JWT Authentication.
     */
    static class LoginResponse {

        private String token;
        private User user;

        LoginResponse(String token, User user) {
            this.token = token;
            this.user = user;
        }

        @JsonProperty("token")
        String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }

        @JsonProperty("user")
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
    }
}
