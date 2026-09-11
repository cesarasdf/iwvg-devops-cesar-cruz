package es.upm.miw.devops.code;

import java.util.List;
import java.util.stream.Stream;

public class UsersDatabase {

    public Stream<User> findAll() {

        List<Fraction> fractions1 = List.of(
                new Fraction(0, 1),
                new Fraction(1, 1),
                new Fraction(2, 1)
        );
        List<Fraction> fractions2 = List.of(
                new Fraction(2, 1),
                new Fraction(-1, 5),
                new Fraction(2, 4),
                new Fraction(4, 3)
        );
        List<Fraction> fractions3 = List.of(
                new Fraction(1, 5),
                new Fraction(3, -6),
                new Fraction(1, 2),
                new Fraction(4, 4)
        );
        List<Fraction> fractions4 = List.of(
                new Fraction(2, 2),
                new Fraction(4, 4)
        );
        List<Fraction> fractions5 = List.of(
                new Fraction(0, 1),
                new Fraction(0, -2),
                new Fraction(0, 3)
        );

        List<Fraction> fractions6 = List.of(
                new Fraction(0, 0),
                new Fraction(1, 0),
                new Fraction(1, 1)
        );

        return Stream.of(
                new User("1", "Oscar", "Fernandez", "oscar.fernandez@miw.upm.es", "12345678A", "Calle Mayor 1",
                        "Madrid", "Madrid", "28001", fractions1),
                new User("2", "Ana", "Blanco", "ana.blanco@miw.upm.es", "23456789B", "Avenida del Puerto 22",
                        "Valencia", "Valencia", "46021", fractions2),
                new User("3", "Oscar", "López", "  ", "34567890C", "Gran Via 3", "Bilbao", "Vizcaya", "48001",
                        fractions3),
                new User("4", "Paula", "Torres", "paula.torres@miw.upm.es", "45678901D", "Rambla Nova 8",
                        "Tarragona", "Tarragona", "43003", fractions4),
                new User("5", "Antonio", "Blanco", "antonio.blanco@miw.upm.es", "56789012E", "Calle Larios 5",
                        "Malaga", "Malaga", null, fractions5),
                new User("6", "Paula", "Torres", fractions6)
        );
    }
}
