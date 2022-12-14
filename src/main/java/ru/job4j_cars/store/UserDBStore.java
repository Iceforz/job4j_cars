package ru.job4j_cars.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j_cars.model.User;

import javax.annotation.concurrent.ThreadSafe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@ThreadSafe
@Repository
public class UserDBStore {

    private static final Logger logger = LogManager.getLogger(UserDBStore.class);

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO userss(name, email, password)VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            result = Optional.of(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "SELECT * FROM userss WHERE email = ? AND password = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(
                            new User(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("email"),
                                    it.getString("password")
                            )
                    );
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
