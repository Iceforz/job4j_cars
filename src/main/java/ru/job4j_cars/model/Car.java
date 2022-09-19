package ru.job4j_cars.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/*Опишем возможности сайта.

        На сайте должны быть объявления. В объявлении должно быть: описание, марка машины, тип кузова, фото.

        Объявление имеет статус продано или нет.*/
public class Car {

    private int id;

    private String carName;

    private String description;

    private String carType;

    private byte[] photo;

    private LocalDate created;

    private boolean status;

  public  Car() {}


    public Car(int id, String carName, String description, String carType, byte[] photo, LocalDate created, boolean status) {
    this.id = id;
    this.carName = carName;
    this.description = description;
    this.carType = carType;
    this.photo = photo;
    this.created = created;
    this.status = status;
  }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id
                &&
                Objects.equals(car, car.carName)
                &&
                Objects.equals(car, car.description)
                &&
                Objects.equals(car, car.carType)
                &&
                Arrays.equals(photo, car.photo)
                &&
                Objects.equals(car, car.created)
                &&
                Objects.equals(car, car.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carName, description, carType, photo, created, status);
    }
}


