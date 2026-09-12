package es.upm.miw.devops.rest;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UserController.USERS)
public class UserController {
    public static final String USERS = "/user";
    public static final String SEARCH = "/search";
    public static final String ID_ACTIVE = "/{id}/active";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User readById(@PathVariable String id) {
        return this.userService.readById(id);
    }

    @GetMapping(SEARCH)
    public List<User> findByFilter(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String familyName,
                                   @RequestParam(required = false) Boolean billable) {
        return this.userService.findByFilter(name, familyName, billable);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        this.userService.deleteById(id);
    }

    @PutMapping(ID_ACTIVE)
    public User updateActive(@PathVariable String id, @RequestBody boolean active) {
        return this.userService.updateActive(id, active);
    }
}
