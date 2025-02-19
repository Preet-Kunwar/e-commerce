package com.learn.electronic.store.controllers;


import com.learn.electronic.store.dtos.JwtRequest;
import com.learn.electronic.store.dtos.JwtResponse;
import com.learn.electronic.store.dtos.UserDto;
import com.learn.electronic.store.security.JwtHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "APIs for Authentication!!")
public class AuthenticationController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private ModelMapper modelMapper;

    //method to generate token:
    private Logger logger= LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        logger.info("Username {} , Password {} ",request.getEmail(),request.getPassword());

        this.doAuthenticate(request.getEmail(),request.getPassword());


        ///            important   ----> Here we get error in type casting userDetails into User so we use userDetails

//        User user = (User) userDetailsService.loadUserByUsername(request.getEmail());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        System.out.println(userDetails.getUsername());

        // generate token
        String token=jwtHelper.generateToken(userDetails);
        //send karna hai response
        JwtResponse jwtResponse=JwtResponse.builder().token(token).user(modelMapper.map(userDetails, UserDto.class)).build();


        return ResponseEntity.ok(jwtResponse);
    }

    private void doAuthenticate(String email,String password){
        try{
            Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
            authenticationManager.authenticate(authentication);
        }catch (BadCredentialsException ex){
            throw new BadCredentialsException("Invalid Username and Password !!");
        }
    }

}
