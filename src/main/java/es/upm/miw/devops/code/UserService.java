package es.upm.miw.devops.code;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User readById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<User> findByFilter(String name, String familyName, Boolean billable) {
        return this.userRepository.findAll().stream()
                .filter(user -> name == null || name.equals(user.getName()))
                .filter(user -> familyName == null || familyName.equals(user.getFamilyName()))
                .filter(user -> billable == null || billable == user.isBillable())
                .sorted(Comparator.comparing(User::getId))
                .toList();
    }

    public void deleteById(String id) {
        if (!this.userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        this.userRepository.deleteById(id);
    }

    public User updateActive(String id, boolean active) {
        User user = this.readById(id);
        user.setActive(active);
        return this.userRepository.save(user);
    }

    private static class UserNotFoundException extends ResponseStatusException {
        UserNotFoundException() {
            super(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
