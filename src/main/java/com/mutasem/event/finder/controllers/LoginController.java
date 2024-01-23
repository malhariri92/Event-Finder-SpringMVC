package com.mutasem.event.finder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.mutasem.event.finder.models.WebUser;
import com.mutasem.event.finder.models.User;
import com.mutasem.event.finder.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("webUser", new WebUser());

        return "register/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        String userName = theWebUser.getEmail();
        // form validation
        if (theBindingResult.hasErrors()){
            return "register/registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            theWebUser.setEmail("");
            model.addAttribute("webUser", theWebUser);
            model.addAttribute("registrationError", "Email is already in use.");

            return "register/registration-form";
        }

        // create user account and store in the database
        userService.save(theWebUser);
        redirectAttributes.addFlashAttribute("registered", true);
        return "redirect:/login";
    }
}
