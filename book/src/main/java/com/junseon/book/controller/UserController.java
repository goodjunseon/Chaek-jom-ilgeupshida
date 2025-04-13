package com.junseon.book.controller;

import com.junseon.book.domain.dto.*;
import com.junseon.book.domain.enums.LoginStatus;
import com.junseon.book.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute UserSaveDTO userSaveDTO) {
        userService.save(userSaveDTO);
        System.out.println("UserController.save");
        System.out.println("userSaveDTO = " + userSaveDTO);
        return "login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDTO userLoginDTO, HttpSession session, Model model) {
        LoginResultDTO loginResultDTO = userService.login(userLoginDTO);
        if (loginResultDTO.getStatus() == LoginStatus.SUCCESS){
            session.setAttribute("loginEmail", loginResultDTO.getUserSaveDTO().getEmail());
            return "main";
        } else {
            if (loginResultDTO.getStatus() == LoginStatus.EMAIL_ERROR){
                model.addAttribute("loginError", "이메일이 일치하지 않습니다.");
            } else if (loginResultDTO.getStatus() == LoginStatus.PASSWORD_ERROR) {
                model.addAttribute("loginError", "비밀번호가 일치하지 않습니다.");
            }
            return "login";
        }
    }

    @GetMapping("/find-email")
    public String findEmailForm() {
        return "findEmail";
    }

    @GetMapping("/find-password")
    public String findPasswordForm() {
        return "findPassword";
    }

    @PostMapping("/find-email")
    public String findEmail(@ModelAttribute UserFindEmailDTO userFindEmailDTO, Model model) {
    // 이름, 전화번호 받아와서 일치하면 email 값 전달하기
        String email = userService.findEmail(userFindEmailDTO);

        if (email != null) {
            model.addAttribute("foundEmail", "가입된 이메일: " + email);
        } else {
            model.addAttribute("findEmailError", "일치하는 회원 정보를 찾을 수 없습니다.");
        }
        return "findEmail";
    }

    @PostMapping("find-password")
    public String findPassword(@ModelAttribute UserFindPasswordDTO userFindPasswordDTO, Model model){
        String password = userService.findPassword(userFindPasswordDTO);

        if(password != null) {
            model.addAttribute("foundPassword", "가입된 비밀번호: " + password);
        } else {
            model.addAttribute("findPasswordError", "일치하는 회원 정보를 찾을 수 없습니다.");
        }
        return "findPassword";

    }

    @GetMapping("/check-id")
    @ResponseBody
    public String checkDuplicateId(@RequestParam("email") String email) {
        // userRepository의 existsByEmail 메서드를 통해 이메일이 존재하는 확인
        boolean isDuplicate = userService.isEmailDuplicated(email);
        return isDuplicate ? "duplicate" : "ok";
    }

}
