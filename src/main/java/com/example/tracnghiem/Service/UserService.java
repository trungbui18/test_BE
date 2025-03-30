package com.example.tracnghiem.Service;

import com.example.tracnghiem.DTO.RegisterDTO;
import com.example.tracnghiem.DTO.UserDTO;
import com.example.tracnghiem.Model.Role;
import com.example.tracnghiem.Model.User;
import com.example.tracnghiem.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }
    public String registerUser(RegisterDTO registerDTO) {
        if (!registerDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            return "Email không hợp lệ!";
        }
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()){
            return "Email đã được đăng ký!";
        }
        if (registerDTO.getPassword().length()<8){
            return "Mật khẩu không dưới 8 ký tự!";
        }
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            return "Mật khẩu và nhập lại mật khẩu không trùng khớp!";
        }
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setUsername(registerDTO.getUsername());
        user.setRole(Role.USER);
        userRepository.save(user);
        return "Đăng Ký Thành Công!";
    }
    public UserDTO login(String email, String password) {
        User user= userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Không Đúng Tài Khoản Mật Khẩu"));
        if (password.equals(user.getPassword())){
            throw new RuntimeException("Không Đúng Tài Khoản Mật Khẩu!");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

}
