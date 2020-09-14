package com.web.demo.controllers;

import com.web.demo.entities.Member;
import com.web.demo.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        List<Member> members = (List<Member>) userRepository.findAll();
        model.put("users", members);
        model.put("currentMemberId", user.getUsername());
        return "homepage";
    }

    @GetMapping("/admin")
    public String adminPage(@AuthenticationPrincipal User user,Map<String, Object> model) {
        model.put("currentAdminId", user.getUsername());
        return "adminpage";
    }

    @GetMapping("/member/new")
    public String memberJoinForm(Member memberForm) {
        return "add-user";
    }

//    @PostMapping("/member/new")
//    public String memberJoin(User userForm) {
//        /* PasswordEncoder로 비밀번호 암호화 */
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        userForm.setPassword(encoder.encode(userForm.getPassword()));
//        userRepository.save(userForm);
//        return "redirect:/login";
//    }

    @PostMapping("/adduser")
    public String addUser(@Valid Member memberForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        userRepository.save(memberForm);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/index";
    }
}
