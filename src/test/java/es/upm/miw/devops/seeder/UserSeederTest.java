package es.upm.miw.devops.seeder;

import es.upm.miw.devops.model.Role;
import es.upm.miw.devops.model.User;
import es.upm.miw.devops.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserSeederTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserSeeder userSeeder = new UserSeeder(this.userRepository);

    @Test
    void testRunSeedsUsersWhenRepositoryIsEmpty() {
        when(this.userRepository.count()).thenReturn(0L);

        this.userSeeder.run();

        ArgumentCaptor<List<User>> usersCaptor = ArgumentCaptor.forClass(List.class);
        verify(this.userRepository, times(1)).saveAll(usersCaptor.capture());

        List<User> seededUsers = usersCaptor.getValue();
        assertThat(seededUsers)
                .extracting(User::getId)
                .containsExactly("1", "2", "3", "4", "5", "6");
        assertThat(seededUsers)
                .filteredOn(user -> user.getId().equals("1"))
                .extracting(User::getRole)
                .containsExactly(Role.ADMIN);
        assertThat(seededUsers)
                .filteredOn(user -> !user.getId().equals("1"))
                .extracting(User::getRole)
                .containsOnly(Role.USER);
    }

    @Test
    void testRunDoesNothingWhenRepositoryIsNotEmpty() {
        when(this.userRepository.count()).thenReturn(6L);

        this.userSeeder.run();

        verify(this.userRepository, never()).saveAll(anyList());
    }
}
