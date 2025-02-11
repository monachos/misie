package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Subject;
import pl.atins.misie.sos.model.User;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

public class SampleData {
    private SampleData() {
    }

    public static User newUser(Consumer<User> customizationAction) {
        final User user = new User();
        user.setStudentIdNumber("%s".formatted(UUID.randomUUID()));
        user.setActive(true);
        user.setDeleted(false);
        user.setEmail("%s@example.com".formatted(UUID.randomUUID()));
        user.setPassword("qwerty");
        user.setPhone2fa("123456789");
        user.setAcceptedPrivacyPolicy(true);
        user.setAcceptedTermsOfUse(true);
        user.setName("Jan");
        user.setSurname("Kowalski");
        user.setBlockedAccount(false);
        customizationAction.accept(user);
        return user;
    }

    public static Subject newSubject(Consumer<Subject> customizationAction) {
        final var subject = new Subject();
        subject.setTime(LocalDateTime.of(2025, 1, 10, 15, 10, 0));
        subject.setDescription("Sample description");
        subject.setTitle("Algorytmy i struktury danych");
        subject.setMaxStudents(12);
        subject.setIsActive(true);
        subject.setSemester("VI");
        subject.setAcademicYear(2025);
        subject.setRegistrationStart(LocalDateTime.of(2025, 3, 1, 9, 0, 0));
        subject.setRegistrationEnd(LocalDateTime.of(2025, 3, 5, 17, 0, 0));
        subject.setRoomNumber("3.11");
        customizationAction.accept(subject);
        return subject;
    }
}
