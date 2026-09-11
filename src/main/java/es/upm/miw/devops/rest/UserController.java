package es.upm.miw.devops.rest;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UsersDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(UserController.USERS)
public class UserController {
    public static final String USERS = "/user";

    @GetMapping("/{id}")
    public User readById(@PathVariable String id) {
        return new UsersDatabase().findAll()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    private static class UserNotFoundException extends ResponseStatusException {
        UserNotFoundException() {
            super(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
