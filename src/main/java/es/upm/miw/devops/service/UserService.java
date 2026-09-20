package es.upm.miw.devops.service;

import es.upm.miw.devops.dto.UserActiveUpdate;
import es.upm.miw.devops.dto.UserUpdateRequest;
import es.upm.miw.devops.model.Role;
import es.upm.miw.devops.model.User;
import es.upm.miw.devops.repository.UserRepository;
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
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> findByFilter(String name, String familyName, Boolean billable) {
        return this.userRepository.findAll().stream()
                .filter(user -> name == null || name.equalsIgnoreCase(user.getName()))
                .filter(user -> familyName == null || familyName.equalsIgnoreCase(user.getFamilyName()))
                .filter(user -> billable == null || billable == user.isBillable())
                .sorted(Comparator.comparing(User::getId))
                .toList();
    }

    public void deleteById(String id) {
        if (!this.userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        this.userRepository.deleteById(id);
    }

    public User updateActive(String id, boolean active) {
        User user = this.readById(id);
        this.checkAdminDeactivation(user, active);
        user.setActive(active);
        return this.userRepository.save(user);
    }

    public List<User> updateActive(List<UserActiveUpdate> updates) {
        return updates.stream()
                .map(update -> this.updateActive(update.id(), update.active()))
                .toList();
    }

    public User update(String id, UserUpdateRequest request) {
        User existingUser = this.readById(id);
        this.checkAdminDeactivation(existingUser, request.active());
        existingUser.setName(request.name());
        existingUser.setFamilyName(request.familyName());
        existingUser.setEmail(request.email());
        existingUser.setIdentity(request.identity());
        existingUser.setAddress(request.address());
        existingUser.setCity(request.city());
        existingUser.setProvince(request.province());
        existingUser.setPostalCode(request.postalCode());
        existingUser.setActive(request.active());
        return this.userRepository.save(existingUser);
    }

    private void checkAdminDeactivation(User user, boolean active) {
        if (!active && user.getRole() == Role.ADMIN) {
            throw new AdminCannotBeDeactivatedException(user.getId());
        }
    }

    private static class UserNotFoundException extends ResponseStatusException {
        UserNotFoundException(String id) {
            super(HttpStatus.NOT_FOUND, "User with id '" + id + "' does not exist");
        }
    }

    private static class AdminCannotBeDeactivatedException extends ResponseStatusException {
        AdminCannotBeDeactivatedException(String id) {
            super(HttpStatus.CONFLICT, "User with id '" + id + "' has ADMIN role and cannot be deactivated");
        }
    }
}
