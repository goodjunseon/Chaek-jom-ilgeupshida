package com.junseon.root.domain.user.controller;

import com.junseon.root.domain.user.dto.request.*;
import com.junseon.root.domain.user.dto.response.LoginResponse;
import com.junseon.root.domain.user.dto.response.MyPageResponse;
import com.junseon.root.global.exception.UnreturnedBookExistsException;
//import com.junseon.root.user.dto.*;
import com.junseon.root.domain.user.entity.User;
import com.junseon.root.global.enums.LoginStatus;
import com.junseon.root.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/save")
    public String saveForm() {
        return "user/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute SignupRequest signupRequest) {
        userService.save(signupRequest);
        System.out.println("UserController.save");
        System.out.println("userSaveDTO = " + signupRequest);
        return "user/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    // 로그인 컨트롤러
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, HttpSession session, Model model) {
        LoginResponse loginResultDTO = userService.login(loginRequest);

        if (loginResultDTO.getStatus() == LoginStatus.SUCCESS){
//            session.setAttribute("loginUser", userLoginDTO);
            Long userId = loginResultDTO.getLoginRequest().getUserId();
            User user = userService.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
            session.setAttribute("loginUser", user);
            return "redirect:/user/" + userId + "/dashboard";
        } else {
            if (loginResultDTO.getStatus() == LoginStatus.EMAIL_ERROR){
                model.addAttribute("loginError", "이메일이 일치하지 않습니다.");
            } else if (loginResultDTO.getStatus() == LoginStatus.PASSWORD_ERROR) {
                model.addAttribute("loginError", "비밀번호가 일치하지 않습니다.");
            }
            return "user/login";
        }
    }

    @GetMapping("/{userId}/dashboard")
    public String userDashboard(@PathVariable("userId") Long userId, Model model, HttpSession session) {
        // 1. 세션에서 로그인 사용자 정보 가져오기
        User user = (User) session.getAttribute("loginUser");

        // 2. 세션이 없거나 userId가 일치하지 않으면 로그인 페이지로 리다이렉트
        if (user == null || !user.getUserId().equals(userId)) {
            return "redirect:/user/login";
        }

        //3. 디버깅용 로그
        System.out.println("로그인 유저 ID: " + user.getUserId());
        System.out.println("로그인 유저 이름: " + user.getName());
        System.out.println("로그인 유저 이메일: " + user.getEmail());

        model.addAttribute("user", user);
        return "user/dashboard"; // templates/user/dashboard.html
    }

    @GetMapping("/find-email")
    public String findEmailForm() {
        return "user/findEmail";
    }

    @GetMapping("/find-password")
    public String findPasswordForm() {
        return "user/findPassword";
    }

    @PostMapping("/find-email")
    public String findEmail(@ModelAttribute EmailRecoveryRequest emailRecoveryRequest, Model model) {
    // 이름, 전화번호 받아와서 일치하면 email 값 전달하기
        String email = userService.findEmail(emailRecoveryRequest);

        if (email != null) {
            model.addAttribute("foundEmail", "가입된 이메일: " + email);
        } else {
            model.addAttribute("findEmailError", "일치하는 회원 정보를 찾을 수 없습니다.");
        }
        return "user/findEmail";
    }

    @PostMapping("find-password")
    public String findPassword(@ModelAttribute PasswordRecoveryRequest passwordRecoveryRequest, Model model){
        String password = userService.findPassword(passwordRecoveryRequest);

        if(password != null) {
            model.addAttribute("foundPassword", "가입된 비밀번호: " + password);
        } else {
            model.addAttribute("findPasswordError", "일치하는 회원 정보를 찾을 수 없습니다.");
        }
        return "user/findPassword";

    }

    @GetMapping("/check-id")
    @ResponseBody
    public String checkDuplicateId(@RequestParam("email") String email) {
        // userRepository의 existsByEmail 메서드를 통해 이메일이 존재하는 확인
        boolean isDuplicate = userService.isEmailDuplicated(email);
        return isDuplicate ? "duplicate" : "ok";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("/{userId}/myPage")
    public String showMyPage(@PathVariable("userId") Long userId, HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null || userId == null || !userId.equals(loginUser.getUserId())) {
            return "redirect:/user/login";
        }

        MyPageResponse dto = MyPageResponse.from(loginUser);
        model.addAttribute("user", dto);
        return "user/myPage";
    }


    @PostMapping("/{userId}/myPage")
    public String userUpdate(@PathVariable ("userId") Long userId, @ModelAttribute ProfileUpdateRequest profileUpdateRequest, HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null || !userId.equals(loginUser.getUserId())) {
            return "redirect:/user/login";
        }

        User updateUser = userService.UserUpdateByUser(userId, profileUpdateRequest);
        session.setAttribute("loginUser",updateUser);

        return "redirect:/user/" + userId + "/myPage"; // GET으로 리다이렉트
    }

    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null || !userId.equals(loginUser.getUserId())) {
            return "redirect:/user/login";
        }

        userService.UserDeleteByUser(userId);
        session.invalidate(); // 세션 제거

        return "redirect:/"; // 홈 or 로그인 페이지로
    }

    @ExceptionHandler(UnreturnedBookExistsException.class)
    public String handleUnreturnedBookExists(UnreturnedBookExistsException e,
                                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alertMessage", e.getMessage());
        return "redirect:/user/" + e.getUserId() + "/myPage"; // 사용자 마이페이지로 리다이렉트
    }



}
