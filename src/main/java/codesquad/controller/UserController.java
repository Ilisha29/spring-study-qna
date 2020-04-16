package codesquad.controller;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UserController {
    private final static Logger LOGGER = Logger.getGlobal();

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(String userId, String password, String name, String email) {
        userRepository.save(new User().builder().userId(userId).password(password).name(name).email(email).build());
        return "redirect:/users";
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("/form")
    public String show() {
        return "user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isSameId(id)) {
            throw new IllegalStateException("Go Away.");
        }
        model.addAttribute("user", sessionedUser);
        return "user/updateForm";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, User user, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isSameId(id)) {
            throw new IllegalStateException("Go Away");
        }
        if (sessionedUser.isSamePassword(user)) {
            sessionedUser.changeUserInfo(user);
            userRepository.save(sessionedUser);
            return "redirect:/users";
        }
        model.addAttribute("user", sessionedUser);
        return "user/updateForm";
    }

    //로그인 기능
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/loginForm";
        }
        if (!user.checkPassword(password)) {
            return "redirect:/users/loginForm";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        LOGGER.info("Login success");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        LOGGER.info("Logout success");
        return "redirect:/";
    }
}
