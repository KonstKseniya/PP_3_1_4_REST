package ru.kata.spring.boot_security.demo.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;

@Component
@Transactional
public class ApplicationStart implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationStart(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {

        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");
        entityManager.persist(userRole);
        entityManager.persist(adminRole);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user00")); // Шифруем пароль
        user.setRoles(new HashSet<>() {{
            add(userRole);
        }});

        entityManager.persist(user);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin00")); // Шифруем пароль
        admin.setRoles(new HashSet<>() {{
            add(adminRole);
        }});

        entityManager.persist(admin);
    }
}
