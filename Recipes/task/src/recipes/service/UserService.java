package recipes.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import recipes.entities.User;

public interface UserService extends UserDetailsService {

    User save(User userToSave);

    User findUserById(long id);

}
