package com.junseon.book.controller;

import com.junseon.book.domain.entity.User;
import com.junseon.book.domain.enums.Role;
import com.junseon.book.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;


    @GetMapping("/login")
    public String loginForm(){

        return"admin/login";
    }

    @PostMapping("/login")
    public String adminLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpSession session, Model model) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty() || !user.get().getPassword().equals(password) || user.get().getRole() != Role.ADMIN) {
            model.addAttribute("loginError", "관리자 인증 실패");
            return "admin/login";
        }

        session.setAttribute("adminUser", user.get()); // 세션 분리
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session) {
        User admin = (User) session.getAttribute("adminUser");

        if (admin == null || admin.getRole() != Role.ADMIN) {
            return "redirect:/admin/login";
        }

        return "admin/dashboard";
    }
}
