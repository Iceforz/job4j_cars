package ru.job4j_cars.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import ru.job4j_cars.model.Car;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Repository
public class CarDBStore {
    private static final Logger logger = LogManager.getLogger(CarDBStore.class);
    private final BasicDataSource pool;

    public CarDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM cars")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cars.add(new Car(it.getInt("id"),
                            it.getString("carName"),
                            it.getString("description"),
                            it.getString("carType"),
                            it.getBytes("photo"),
                            it.getDate("created").toLocalDate(),
                    it.getBoolean("status")));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return cars;
    }


    public Car add(Car car) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO cars(carName, description, carType, photo, created, status) VALUES (?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, car.getCarName());
            ps.setString(2, car.getDescription());
            ps.setString(3, car.getCarType());
            ps.setBytes(4, car.getPhoto());
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setBoolean(6, car.isStatus());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    car.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return car;
    }

    public void update(Car car) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO  cars(carName, description, carType, photo, created, status) VALUES (?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, car.getCarName());
            ps.setString(2, car.getDescription());
            ps.setString(3, car.getCarType());
            ps.setBytes(4, car.getPhoto());
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setBoolean(6, car.isStatus());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    car.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Car findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM cars WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Car(it.getInt("id"),
                            it.getString("carName"),
                            it.getString("description"),
                            it.getString("carType"),
                            it.getBytes("photo"),
                            it.getDate("created").toLocalDate(),
                            it.getBoolean("status"));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
