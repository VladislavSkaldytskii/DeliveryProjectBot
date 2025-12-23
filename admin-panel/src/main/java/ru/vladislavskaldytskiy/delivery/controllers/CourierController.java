package ru.vladislavskaldytskiy.delivery.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vladislavskaldytskiy.delivery.models.entities.Courier;
import ru.vladislavskaldytskiy.delivery.services.ClientService;
import ru.vladislavskaldytskiy.delivery.services.CourierService;
import ru.vladislavskaldytskiy.delivery.utils.ControllerUtils;

@Controller
@RequestMapping("/couriers")
public class CourierController {

    private final CourierService courierService;
    private final ClientService clientService;

    public CourierController(CourierService courierService, ClientService clientService) {
        this.courierService = courierService;
        this.clientService = clientService;
    }

    @GetMapping
    public String showAllCouriers(Model model) {
        model.addAttribute("couriers", courierService.findAll());
        return "main/couriers/all";
    }

    @GetMapping("/edit/{courier}")
    public String showEditCourier(Model model, @PathVariable Courier courier) {
        model.addAttribute("courier", courier);
        return "main/couriers/edit";
    }

    @PostMapping("/update")
    public String updateCourier(@Valid Courier courier, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute("courier", courier);
            return "main/couriers/edit";
        }

        courierService.update(courier);
        return "redirect:/couriers/edit/" + courier.getId();
    }

    @GetMapping("/create")
    public String showCreateCourierForm(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "main/couriers/create";
    }

    @PostMapping("/create")
    public String createCourier(@RequestParam Long chatId) {
        courierService.save(chatId);
        return "redirect:/couriers";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        courierService.deleteById(id);
        return "redirect:/couriers";
    }
}
