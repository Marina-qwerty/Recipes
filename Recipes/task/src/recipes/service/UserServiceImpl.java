package recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.User;
import recipes.repository.UserRepository;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder encoder;

    @Override
    public User save(User userToSave) {
        User user = userRepository.findByEmail(userToSave.getEmail());
        if (user == null) {
            userToSave.setPassword(encoder.encode(userToSave.getPassword()));
            return userRepository.save(userToSave);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
    }

    @Override
    public User findUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        return user.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        return user;
    }
}
