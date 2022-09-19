package ru.job4j_cars.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j_cars.model.Car;
import ru.job4j_cars.model.User;
import ru.job4j_cars.service.CarService;

import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ThreadSafe
@Controller
public class CarController {

    private final CarService carService;

    public CarController(CarService service) {
        this.carService = service;

    }

    @GetMapping("/cars")
    public String cars(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("cars", carService.findAll());
        return "cars";
    }

    @GetMapping("/formAddCar")
    public String addCar(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        return "addCar";
    }

    @PostMapping("/createCar")
    public String createCar(@ModelAttribute Car car,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        car.setPhoto(file.getBytes());
        carService.add(car);
        return "redirect:/car";
    }

    @GetMapping("/formUpdateCar/{carId}")
    public String formUpdateCar(Model model, @PathVariable("carId") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("car", carService.findById(id));
        return "updateCar";
    }

    @PostMapping("/updateCar")
    public String updateCar(@ModelAttribute Car car,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        car.setPhoto(file.getBytes());
        carService.update(car);
        return "redirect:/cars";
    }

    @GetMapping("/photoCar/{carId}")
    public ResponseEntity<Resource> download(@PathVariable("carId") Integer carId) {
        Car car = carService.findById(carId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(car.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(car.getPhoto()));
    }
}
