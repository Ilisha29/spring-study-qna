package codesquad.service;

import codesquad.dto.User;
import java.util.List;

public interface UserService {

  public void addUser(User user);

  public User findUser(String userId);

  public List<User> getUsers();
}
