package uk.ac.cardiff.mma.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.cardiff.mma.application.equipment.entity.ConsumableDTO;
import uk.ac.cardiff.mma.application.equipment.entity.ConsumableOrder;
import uk.ac.cardiff.mma.application.role.entity.Role_dto;
import uk.ac.cardiff.mma.application.service.ConsumableOrderService;
import uk.ac.cardiff.mma.application.service.ConsumableServices;
import uk.ac.cardiff.mma.application.service.Role_service;
import uk.ac.cardiff.mma.application.service.Role_service_impl;

import java.util.Date;

@Controller
public class ConsumableOrderController {

    @Autowired
    private ConsumableServices services;
    @Autowired
    private ConsumableOrderService orderService;

    @Autowired
    private Role_service user_services;

    @GetMapping({"/all_consumables"})
    public String getAllConsumables(Model model) {
        model.addAttribute("consumables", services.getConsumableInfos());
        return "AllConsumables";
    }

    @GetMapping("/all_consumable_orders")
    public String getAllorders(Model model) {
        model.addAttribute("orders",orderService.findAll());
        return "AllConsumableOrders";
    }


    @GetMapping("/neworder")
    public String newOrder(Model model) {
        model.addAttribute("order",new ConsumableOrder());
        model.addAttribute("consumables",services.getConsumableInfos());
        return "ConsumableOrder";
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return currentPrincipalName;
    }



    @GetMapping("/saveorder")
    public String saveOrder(@ModelAttribute("order") ConsumableOrder order, RedirectAttributes redirectAttribute, Model model) {
        order.setOrderCreatedOn(new Date());
        ConsumableDTO item = services.findOne(order.getConsumable_id());
        int current_stock = item.getStock();

        int quantity = order.getQuantity();
        System.out.println(quantity);
        String consumable = item.getName();
        String username = getUsername();
        if(quantity <= current_stock) {
            current_stock-=quantity;
            System.out.println("after reducing: " + current_stock);

            item.setStock(current_stock);
            order.setName(consumable);
            order.setUsername(username);
            services.saveConsumable(item);
            orderService.saveOrder(order);
            model.addAttribute("order",orderService.findAll());
            model.addAttribute("success","Order created successfully!");
            model.addAttribute("alertClass","alert-success");
            return "redirect:/all_consumables";
            // go to order successful page for user or make a warning box
        }

        else {
            model.addAttribute("error","Error Quantity can not be greater than current stock! ");
            model.addAttribute("alertClass", "alert-danger");
            model.addAttribute("order",orderService.getByID(order.getOrderID()));
            return "ConsumableOrder";

        }
    }

}
