package uk.ac.cardiff.mma.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import uk.ac.cardiff.mma.application.equipment.entity.Equipment;
import uk.ac.cardiff.mma.application.service.EquipmentService;

import java.util.List;

@Controller
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;




    @GetMapping("/admin/equipment")
    public String viewEquipmentHomePage(Model model){
        return findPaginated(1,"name","asc",model);
    }

    @GetMapping("/admin/showNewEquipmentForm")
    public String addNewEquipment(Model model){
        //create model attribute to bind form data
        Equipment equipment = new Equipment();
        model.addAttribute("equipment",equipment);
        return "NewEquipment";
    }

    @PostMapping("/admin/saveEquipment")
    public String saveEquipment(@ModelAttribute("equipment") Equipment equipment){
        //save equipment to database
        equipmentService.saveEquipment(equipment);

        return "redirect:/admin/equipment";
    }

    @GetMapping("/admin/updateEquipment/{id}")
    public String updateEquipment(@PathVariable(value = "id") int id, Model model){
        Equipment equipment = equipmentService.getEquipmentById(id);


        model.addAttribute("equipment",equipment);
        return "UpdateEquipment";
    }




    @GetMapping("/admin/deleteEquipment/{id}")
    public String deleteEquipment(@PathVariable (value = "id") int id) {

        // call delete employee method
        this.equipmentService.deleteEquipmentById(id);
        return "redirect:/admin/equipment";
    }





    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Equipment> page = equipmentService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Equipment> listEquipments = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEquipments", listEquipments);
        return "Equipment";
    }

}
