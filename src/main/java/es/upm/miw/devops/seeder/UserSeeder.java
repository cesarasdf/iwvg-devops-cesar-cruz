package es.upm.miw.devops.seeder;

import es.upm.miw.devops.model.Role;
import es.upm.miw.devops.model.User;
import es.upm.miw.devops.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the users table with a baseline dataset the first time the application starts against an empty database.
 * Active only for the dev, pre and prod profiles: the test profile already populates data through data.sql.
 */
@Component
@Profile({"dev", "pre", "prod"})
public class UserSeeder implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSeeder.class);

    private final UserRepository userRepository;

    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (this.userRepository.count() > 0) {
            LOGGER.info("Users table already contains data, skipping seeding");
            return;
        }
        LOGGER.info("Seeding initial users");
        this.userRepository.saveAll(this.initialUsers());
    }

    private List<User> initialUsers() {
        User admin = new User("1", "Oscar", "Fernandez", "oscar.fernandez@miw.upm.es", "12345678A",
                "Calle Mayor 1", "Madrid", "Madrid", "28001");
        admin.setRole(Role.ADMIN);

        User ana = new User("2", "Ana", "Blanco", "ana.blanco@miw.upm.es", "23456789B",
                "Avenida del Puerto 22", "Valencia", "Valencia", "46021");

        User oscar = new User("3", "Oscar", "López", "  ", "34567890C",
                "Gran Via 3", "Bilbao", "Vizcaya", "48001");

        User paulaTorres = new User("4", "Paula", "Torres", "paula.torres@miw.upm.es", "45678901D",
                "Rambla Nova 8", "Tarragona", "Tarragona", "43003");

        User antonio = new User("5", "Antonio", "Blanco", "antonio.blanco@miw.upm.es", "56789012E",
                "Calle Larios 5", "Malaga", "Malaga", null);

        User paulaTorres2 = new User("6", "Paula", "Torres");

        return List.of(admin, ana, oscar, paulaTorres, antonio, paulaTorres2);
    }
}
