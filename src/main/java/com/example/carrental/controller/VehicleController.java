//package com.example.carrental.controller;
//
//import com.example.carrental.model.AdminUser;
//import com.example.carrental.model.Car;
//import com.example.carrental.model.SUV;
//import com.example.carrental.model.User;
//import com.example.carrental.model.Vehicle;
//import com.example.carrental.service.VehicleService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import jakarta.servlet.http.HttpSession;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/vehicles")
//public class VehicleController {
//
//    @Autowired
//    private VehicleService vehicleService;
//
//    @GetMapping("/list")
//    public String listVehicles(Model model) {
//        List<Vehicle> vehicles = vehicleService.getAllVehicles();
//        model.addAttribute("vehicles", vehicles);
//        return "vehicles/list";
//    }
//
//    @GetMapping("/available")
//    public String listAvailableVehicles(Model model) {
//        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
//        model.addAttribute("vehicles", availableVehicles);
//        return "vehicles/available";
//    }
//
//    @GetMapping("/details/{vehicleId}")
//    public String vehicleDetails(@PathVariable String vehicleId, Model model) {
//        Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
//        if (vehicleOpt.isPresent()) {
//            model.addAttribute("vehicle", vehicleOpt.get());
//            return "vehicles/details";
//        } else {
//            return "redirect:/vehicles/list";
//        }
//    }
//
//    // Admin methods for vehicle management
//    @GetMapping("/admin/add")
//    public String showAddVehicleForm(Model model, HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        model.addAttribute("vehicleTypes", new String[]{"Car", "SUV"});
//        model.addAttribute("car", new Car());
//        model.addAttribute("suv", new SUV());
//        return "admin/vehicles/add";
//    }
//
//    @PostMapping("/admin/add/car")
//    public String addCar(@ModelAttribute Car car, RedirectAttributes redirectAttributes,
//                         HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        try {
//            car.setVehicleId("CAR-" + System.currentTimeMillis());
//            vehicleService.addVehicle(car);
//            redirectAttributes.addFlashAttribute("message", "Car added successfully");
//            return "redirect:/vehicles/admin/list";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/vehicles/admin/add";
//        }
//    }
//
//    @PostMapping("/admin/add/suv")
//    public String addSUV(@ModelAttribute SUV suv, RedirectAttributes redirectAttributes,
//                         HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        try {
//            suv.setVehicleId("SUV-" + System.currentTimeMillis());
//            vehicleService.addVehicle(suv);
//            redirectAttributes.addFlashAttribute("message", "SUV added successfully");
//            return "redirect:/vehicles/admin/list";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/vehicles/admin/add";
//        }
//    }
//
//    @GetMapping("/admin/list")
//    public String adminListVehicles(Model model, HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        List<Vehicle> vehicles = vehicleService.getAllVehicles();
//        model.addAttribute("vehicles", vehicles);
//        return "admin/vehicles/list";
//    }
//
//    @GetMapping("/admin/edit/{vehicleId}")
//    public String showEditVehicleForm(@PathVariable String vehicleId, Model model,
//                                      HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
//        if (vehicleOpt.isPresent()) {
//            Vehicle vehicle = vehicleOpt.get();
//            if (vehicle instanceof Car) {
//                model.addAttribute("car", vehicle);
//                model.addAttribute("vehicleType", "Car");
//            } else if (vehicle instanceof SUV) {
//                model.addAttribute("suv", vehicle);
//                model.addAttribute("vehicleType", "SUV");
//            } else {
//                model.addAttribute("vehicle", vehicle);
//                model.addAttribute("vehicleType", "Vehicle");
//            }
//            return "admin/vehicles/edit";
//        } else {
//            return "redirect:/vehicles/admin/list";
//        }
//    }
//
//    @PostMapping("/admin/edit/car/{vehicleId}")
//    public String updateCar(@PathVariable String vehicleId, @ModelAttribute Car car,
//                            RedirectAttributes redirectAttributes, HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        car.setVehicleId(vehicleId);
//        try {
//            vehicleService.updateVehicle(car);
//            redirectAttributes.addFlashAttribute("message", "Car updated successfully");
//            return "redirect:/vehicles/admin/list";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/vehicles/admin/edit/" + vehicleId;
//        }
//    }
//
//    @PostMapping("/admin/edit/suv/{vehicleId}")
//    public String updateSUV(@PathVariable String vehicleId, @ModelAttribute SUV suv,
//                            RedirectAttributes redirectAttributes, HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        suv.setVehicleId(vehicleId);
//        try {
//            vehicleService.updateVehicle(suv);
//            redirectAttributes.addFlashAttribute("message", "SUV updated successfully");
//            return "redirect:/vehicles/admin/list";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/vehicles/admin/edit/" + vehicleId;
//        }
//    }
//
//    @GetMapping("/admin/delete/{vehicleId}")
//    public String deleteVehicle(@PathVariable String vehicleId, RedirectAttributes redirectAttributes,
//                                HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        try {
//            vehicleService.deleteVehicle(vehicleId);
//            redirectAttributes.addFlashAttribute("message", "Vehicle deleted successfully");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//
//        return "redirect:/vehicles/admin/list";
//    }
//}
package com.example.carrental.controller;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.Car;
import com.example.carrental.model.SUV;
import com.example.carrental.model.User;
import com.example.carrental.model.Vehicle;
import com.example.carrental.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/list")
    public String listVehicles(Model model, @RequestParam(required = false) String sortBy,
                               @RequestParam(required = false) String order) {
        List<Vehicle> vehicles;

        // Default sorting is by availability (available first) and then by price (ascending)
        boolean ascendingPrice = !"desc".equals(order);
        boolean availableFirst = !"unavailable-first".equals(sortBy);

        if ("price".equals(sortBy)) {
            vehicles = vehicleService.getAllVehiclesSortedByPrice(ascendingPrice);
        } else if ("availability".equals(sortBy)) {
            vehicles = vehicleService.getAllVehiclesSortedByAvailability(availableFirst);
        } else {
            // Default combined sort
            vehicles = vehicleService.getAllVehiclesSortedByAvailabilityAndPrice(availableFirst, ascendingPrice);
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("currentSort", sortBy);
        model.addAttribute("currentOrder", order);
        return "vehicles/list";
    }

    @GetMapping("/available")
    public String listAvailableVehicles(Model model, @RequestParam(required = false) String sortBy,
                                        @RequestParam(required = false) String order) {
        List<Vehicle> availableVehicles;

        boolean ascendingPrice = !"desc".equals(order);

        if ("price".equals(sortBy)) {
            availableVehicles = vehicleService.getAvailableVehiclesSortedByPrice(ascendingPrice);
        } else {
            availableVehicles = vehicleService.getAvailableVehicles();
        }

        model.addAttribute("vehicles", availableVehicles);
        model.addAttribute("currentSort", sortBy);
        model.addAttribute("currentOrder", order);
        return "vehicles/available";
    }

    @GetMapping("/details/{vehicleId}")
    public String vehicleDetails(@PathVariable String vehicleId, Model model) {
        Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
        if (vehicleOpt.isPresent()) {
            model.addAttribute("vehicle", vehicleOpt.get());
            return "vehicles/details";
        } else {
            return "redirect:/vehicles/list";
        }
    }

    // Admin methods for vehicle management
    @GetMapping("/admin/add")
    public String showAddVehicleForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        model.addAttribute("vehicleTypes", new String[]{"Car", "SUV"});
        model.addAttribute("car", new Car());
        model.addAttribute("suv", new SUV());
        return "admin/vehicles/add";
    }

    @PostMapping("/admin/add/car")
    public String addCar(@ModelAttribute Car car, RedirectAttributes redirectAttributes,
                         HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        try {
            car.setVehicleId("CAR-" + System.currentTimeMillis());
            vehicleService.addVehicle(car);
            redirectAttributes.addFlashAttribute("message", "Car added successfully");
            return "redirect:/vehicles/admin/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/vehicles/admin/add";
        }
    }

    @PostMapping("/admin/add/suv")
    public String addSUV(@ModelAttribute SUV suv, RedirectAttributes redirectAttributes,
                         HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        try {
            suv.setVehicleId("SUV-" + System.currentTimeMillis());
            vehicleService.addVehicle(suv);
            redirectAttributes.addFlashAttribute("message", "SUV added successfully");
            return "redirect:/vehicles/admin/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/vehicles/admin/add";
        }
    }

    @GetMapping("/admin/list")
    public String adminListVehicles(Model model, HttpSession session,
                                    @RequestParam(required = false) String sortBy,
                                    @RequestParam(required = false) String order) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        List<Vehicle> vehicles;

        // Default sorting parameters
        boolean ascendingPrice = !"desc".equals(order);
        boolean availableFirst = !"unavailable-first".equals(sortBy);

        if ("price".equals(sortBy)) {
            vehicles = vehicleService.getAllVehiclesSortedByPrice(ascendingPrice);
        } else if ("availability".equals(sortBy)) {
            vehicles = vehicleService.getAllVehiclesSortedByAvailability(availableFirst);
        } else {
            // Default combined sort
            vehicles = vehicleService.getAllVehiclesSortedByAvailabilityAndPrice(availableFirst, ascendingPrice);
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("currentSort", sortBy);
        model.addAttribute("currentOrder", order);
        return "admin/vehicles/list";
    }

    @GetMapping("/admin/edit/{vehicleId}")
    public String showEditVehicleForm(@PathVariable String vehicleId, Model model,
                                      HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            if (vehicle instanceof Car) {
                model.addAttribute("car", vehicle);
                model.addAttribute("vehicleType", "Car");
            } else if (vehicle instanceof SUV) {
                model.addAttribute("suv", vehicle);
                model.addAttribute("vehicleType", "SUV");
            } else {
                model.addAttribute("vehicle", vehicle);
                model.addAttribute("vehicleType", "Vehicle");
            }
            return "admin/vehicles/edit";
        } else {
            return "redirect:/vehicles/admin/list";
        }
    }

    @PostMapping("/admin/edit/car/{vehicleId}")
    public String updateCar(@PathVariable String vehicleId, @ModelAttribute Car car,
                            RedirectAttributes redirectAttributes, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        car.setVehicleId(vehicleId);
        try {
            vehicleService.updateVehicle(car);
            redirectAttributes.addFlashAttribute("message", "Car updated successfully");
            return "redirect:/vehicles/admin/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/vehicles/admin/edit/" + vehicleId;
        }
    }

    @PostMapping("/admin/edit/suv/{vehicleId}")
    public String updateSUV(@PathVariable String vehicleId, @ModelAttribute SUV suv,
                            RedirectAttributes redirectAttributes, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        suv.setVehicleId(vehicleId);
        try {
            vehicleService.updateVehicle(suv);
            redirectAttributes.addFlashAttribute("message", "SUV updated successfully");
            return "redirect:/vehicles/admin/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/vehicles/admin/edit/" + vehicleId;
        }
    }

    @GetMapping("/admin/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable String vehicleId, RedirectAttributes redirectAttributes,
                                HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        try {
            vehicleService.deleteVehicle(vehicleId);
            redirectAttributes.addFlashAttribute("message", "Vehicle deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/vehicles/admin/list";
    }
}