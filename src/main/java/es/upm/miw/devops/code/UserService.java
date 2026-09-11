package es.upm.miw.devops.code;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    public User readById(String id) {
        return new UsersDatabase().findAll()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    private static class UserNotFoundException extends ResponseStatusException {
        UserNotFoundException() {
            super(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
