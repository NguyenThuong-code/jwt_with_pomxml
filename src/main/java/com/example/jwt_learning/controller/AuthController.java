package com.example.jwt_learning.controller;

import com.example.jwt_learning.dto.request.SignInForm;
import com.example.jwt_learning.dto.request.SignUpForm;
import com.example.jwt_learning.dto.reseponse.JwtResponse;
import com.example.jwt_learning.dto.reseponse.ResponseMessage;
import com.example.jwt_learning.models.Role;
import com.example.jwt_learning.models.RoleName;
import com.example.jwt_learning.models.Users;
import com.example.jwt_learning.security.jwt.JwtProvider;
import com.example.jwt_learning.security.userprinciple.UserPrinciple;
import com.example.jwt_learning.service.IRoleService;
import com.example.jwt_learning.service.IUserService;
import com.example.jwt_learning.service.impl.RoleServiceImpl;
import com.example.jwt_learning.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.SecondaryTable;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm){
        if(userService.existByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("The username is existed"), HttpStatus.OK);
        }
        if(userService.existByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("The email is existed"), HttpStatus.OK);
        }
        Users users = new Users(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role ->{
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow( ()-> new RuntimeException("Role PM not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        users.setRoles(roles);
        userService.save(users);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }
    @PostMapping("/signin")
    public  ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(),signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple =(UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getUsername(), userPrinciple.getAuthorities()));//

    }
}
