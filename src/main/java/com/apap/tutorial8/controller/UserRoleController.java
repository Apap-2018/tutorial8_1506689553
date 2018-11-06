package com.apap.tutorial8.controller;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserRoleModel user) {
        userService.addUser(user);
        return "home";
    }

    @RequestMapping(value = "/password")
    public String updateUserPassword() {
        return "password";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePasswordSubmit(@RequestParam(value = "passLama") String passLama,
                                       @RequestParam(value = "passBaru") String passBaru,
                                       @RequestParam(value = "passKonfirm") String passKonfirm,
                                       Model model, RedirectAttributes redirect) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserRoleModel user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String pesan = "";

        if(passBaru.equals(passKonfirm)) {

            if(passwordEncoder.matches(passLama,user.getPassword())) {
                userService.updatePassword(passBaru, user);
                pesan = "S";
            } else {
                pesan = "F";
            }

        } else {
            pesan = "NM";
        }

        model.addAttribute("pesan", pesan);
        return "password";
    }





}
