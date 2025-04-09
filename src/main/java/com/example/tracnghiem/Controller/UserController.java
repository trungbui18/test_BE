package com.example.tracnghiem.Controller;

import com.example.tracnghiem.DTO.ChangePasswordDTO;
import com.example.tracnghiem.DTO.ChangeProfileDTO;
import com.example.tracnghiem.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PutMapping("information")
    public ResponseEntity<?> UpdateInformationUser(@RequestBody ChangeProfileDTO changeProfileDTO) {
        try{
            userService.changeInformationUser(changeProfileDTO);
            return ResponseEntity.ok().body("Thay Đổi Thông Tin Thành Công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("changePassword")
    public ResponseEntity<?> ChangePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            userService.changePassword(changePasswordDTO);
            return ResponseEntity.ok().body("Thay Đổi Mật Khẩu Thành Công!");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
