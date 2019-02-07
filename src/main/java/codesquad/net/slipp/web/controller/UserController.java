package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("")
    public String getUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "/user/list";
    }

    @PostMapping("")
    public String createUser(User user, Model model) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String getLogin() {

        return "/user/login";
    }

    @PostMapping("/login")
    public String postLogin(User user){

        return "/";
    }

    @GetMapping("/{id}")
    public ModelAndView getUserProfile(@PathVariable long id, Model model) {
        ModelAndView mav = new ModelAndView("/user/list_profile");
        mav.addObject("user", userRepository.findById(id).get());

        return mav;
    }

    @RequestMapping("/{id}/form")
    public ModelAndView getUserUpdateForm(@PathVariable long id, Model model, @RequestParam(required = false, name = "msg") String msg) {
        ModelAndView mav = new ModelAndView("/user/updateForm");
        mav.addObject("user", userRepository.findById(id).get());
        mav.addObject("msg", msg);

        return mav;
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User updatedUser, RedirectAttributes redirectAttributes, @RequestBody String modifiedPassword) {
        User user = userRepository.findById(id).get();
        if (isSame(updatedUser.getPassword(), user.getPassword())) {
            user.setPassword(modifiedPassword);
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            userRepository.save(user);
            return "redirect:/users";
        }
        redirectAttributes.addAttribute("msg", "잘못된 비밀번호입니다.");

        return "redirect:/users/" + id + "/form";
    }


    public boolean isSame(String a, String b) {

        return a.equals(b);
    }

}
