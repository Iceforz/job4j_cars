package ru.job4j_cars.service;

import org.springframework.stereotype.Service;
import ru.job4j_cars.model.Car;
import ru.job4j_cars.store.CarDBStore;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class CarService {
    private final CarDBStore store;

    public CarService(CarDBStore store) {
        this.store = store;
    }

    public void add(Car car) {
        store.add(car);
    }

    public Car findById(int id) {
        return store.findById(id);
    }

    public void update(Car car) {
        store.update(car);
    }

    public Collection<Car> findAll() {
        List<Car> cars = store.findAll();
        return cars;
    }
}

