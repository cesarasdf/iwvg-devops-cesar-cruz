package es.upm.miw.devops.code;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users;

    public UserService() {
        this.users = new ArrayList<>(new UsersDatabase().findAll().toList());
    }

    public User readById(String id) {
        return this.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    public List<User> findByFilter(String name, String familyName, Boolean billable) {
        return this.users.stream()
                .filter(user -> name == null || name.equals(user.getName()))
                .filter(user -> familyName == null || familyName.equals(user.getFamilyName()))
                .filter(user -> billable == null || billable == user.isBillable())
                .toList();
    }

    public void deleteById(String id) {
        if (!this.users.removeIf(user -> user.getId().equals(id))) {
            throw new UserNotFoundException();
        }
    }

    private static class UserNotFoundException extends ResponseStatusException {
        UserNotFoundException() {
            super(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
