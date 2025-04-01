package com.example.tracnghiem.Controller;

import com.example.tracnghiem.DTO.LoginDTO;
import com.example.tracnghiem.DTO.RegisterDTO;
import com.example.tracnghiem.DTO.UserDTO;
import com.example.tracnghiem.Model.User;
import com.example.tracnghiem.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        String result= userService.registerUser(registerDTO);
        if (result.equals("Đăng Ký Thành Công!")){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            UserDTO userDTO=userService.login(loginDTO.getEmail(),loginDTO.getPassword());
            return ResponseEntity.ok(userDTO);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
