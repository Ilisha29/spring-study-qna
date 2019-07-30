package codesquad.Controller;

import codesquad.VO.User;
import codesquad.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserServiceImp userService;

  @PostMapping("/create")
  public String createUser(User user) {

    userService.addUser(user);

    return "redirect:/user/list";
  }

  @GetMapping("/list")
  public String showUserList(Model model) {

    model.addAttribute("users", userService.getUsers());

    return "user/list";
  }

  @GetMapping("/profile/{userId}")
  public String showProfile(@PathVariable("userId") String userId, Model model) {

    model.addAttribute("user", userService.findUser(userId));

    return "user/profile";
  }
}
