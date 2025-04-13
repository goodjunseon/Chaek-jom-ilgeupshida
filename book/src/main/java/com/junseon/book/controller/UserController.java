package com.junseon.book.controller;

import com.junseon.book.domain.dto.LoginResultDTO;
import com.junseon.book.domain.dto.UserSaveDTO;
import com.junseon.book.domain.enums.LoginStatus;
import com.junseon.book.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
    public String login(@ModelAttribute UserSaveDTO userSaveDTO, HttpSession session, Model model) {
        LoginResultDTO loginResultDTO = userService.login(userSaveDTO);
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


}
