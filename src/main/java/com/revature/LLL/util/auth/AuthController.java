package com.revature.LLL.util.auth;

import com.revature.LLL.Security.JwtGenerator;
import com.revature.LLL.User.User;
import com.revature.LLL.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //Imported two spring security beans
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService, AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {

        this.authService = authService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

//    @PostMapping
//    private ResponseEntity<AuthResponseDto> postLogin(@RequestParam String email, @RequestParam String password) throws javax.naming.AuthenticationException {
//
//
//        //Authenticates the user and compares the password against the encrypted password in the backend
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        //Stores the authentication information of the user for later user (e.g. session management, authorization)
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User user = authService.login(email, password);
//        //Get userId so that it can be saved to the JWT payload
//        int id = user.getUserId();
//        //Generate JWT and send it as part of the response
//        String token = jwtGenerator.generateToken(authentication, id);
//        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
//
//
//
//
////        return ResponseEntity.noContent()
////                .header("userId", String.valueOf(user.getUserId()))
////                .header("userType", user.getUserType().name())
////                .build();
//    }

    @PostMapping
    public ResponseEntity<?> postLogin(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByEmail(email);
            int id = user.getUserId();
            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}