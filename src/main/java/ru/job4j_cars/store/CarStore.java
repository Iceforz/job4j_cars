package ru.job4j_cars.store;

import org.springframework.stereotype.Repository;
import ru.job4j_cars.model.Car;

import javax.annotation.concurrent.ThreadSafe;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CarStore {
    private final Map<Integer, Car> cars = new ConcurrentHashMap<>();

    private static final AtomicInteger CARS_ID = new AtomicInteger(3);

    public Collection<Car> findAll() {
        return cars.values();
    }

    public void add(Car car) {
        car.setId(CARS_ID.incrementAndGet());
        car.setCreated(LocalDate.now());
        cars.put(car.getId(), car);
    }

    public Car findById(int id) {
        return cars.get(id);
    }

    public void update(Car car) {
        cars.replace(car.getId(), car);
    }
}