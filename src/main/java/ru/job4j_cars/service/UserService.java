package ru.job4j_cars.service;

import org.springframework.stereotype.Service;
import ru.job4j_cars.model.User;
import ru.job4j_cars.store.UserDBStore;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Optional;

@ThreadSafe
@Service
public class UserService {
    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }
}

