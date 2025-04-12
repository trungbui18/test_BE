package com.example.tracnghiem.Service;

import com.example.tracnghiem.Config.PasswordEncoder;
import com.example.tracnghiem.DTO.*;
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
        if (registerDTO.getPassword().matches(".*\\s.*")){
            return "Mật khẩu không được có khoảng trắng!";
        }
        if (registerDTO.getPassword().length()<8){
            return "Mật khẩu không dưới 8 ký tự!";
        }
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            return "Mật khẩu và nhập lại mật khẩu không trùng khớp!";
        }
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        String password = PasswordEncoder.encodeMD5(registerDTO.getPassword());
        user.setPassword(password);
        user.setUsername(registerDTO.getUsername());
        user.setRole(Role.USER);
        userRepository.save(user);
        return "Đăng Ký Thành Công!";
    }
    public LoginResponse login(String email, String password) {
        User user= userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Không Đúng Tài Khoản Mật Khẩu"));
        String encodedPassword = PasswordEncoder.encodeMD5(password);
        if (!encodedPassword.equals(user.getPassword())){
            throw new RuntimeException("Không Đúng Tài Khoản Mật Khẩu!");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return new LoginResponse(user.getId(),user.getUsername(),user.getEmail(),user.getRole());
    }
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User user=userRepository.findById(changePasswordDTO.getIdUser()).orElseThrow(()->new RuntimeException("Không đúng User"));
        String encodedOldPassword = PasswordEncoder.encodeMD5(changePasswordDTO.getOldPassword());
        if (!encodedOldPassword.equals(user.getPassword())){
            throw new RuntimeException("Mật Khẩu Tài Khoản Không Đúng!");
        }
        if (changePasswordDTO.getNewPassword().matches(".*\\s.*")){
            throw new RuntimeException( "Mật khẩu không được có khoảng trắng!");
        }
        if (changePasswordDTO.getNewPassword().length()<8){
            throw new RuntimeException("Mật khẩu không dưới 8 ký tự!");
        }
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())){
            throw new RuntimeException("Mật khẩu và nhập lại mật khẩu không trùng khớp!");
        }
        user.setPassword(PasswordEncoder.encodeMD5(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }
    public void changeInformationUser(ChangeProfileDTO changeProfileDTO){
        User user=userRepository.findById(changeProfileDTO.getIdUser()).orElseThrow(()->new RuntimeException("Không Tìm Thấy User!"));
        if (!changeProfileDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            throw new RuntimeException("Email không hợp lệ!");
        }
        if (userRepository.findByEmail(changeProfileDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email đã được đăng ký!");
        }
        user.setUsername(changeProfileDTO.getUsername());
        user.setEmail(changeProfileDTO.getEmail());
        userRepository.save(user);
    }
}