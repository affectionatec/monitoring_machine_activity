package uk.ac.cardiff.mma.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.cardiff.mma.application.equipment.entity.ServiceReminderDTO;
import uk.ac.cardiff.mma.application.equipment.repositories.ServiceReminderRepository;

@Controller
@RequestMapping(path = "/todo")
public class ServiceReminderRestController {

    @Autowired
    private ServiceReminderRepository repository;

    @GetMapping("/all")
    public @ResponseBody Iterable<ServiceReminderDTO> getAll() {
        Iterable<ServiceReminderDTO> todoList = repository.findAll();
        return repository.findAll();
    }

    @PostMapping("/add")
    public @ResponseBody Result addItem(@RequestParam String name, @RequestParam String category) {
        ServiceReminderDTO item = new ServiceReminderDTO(category, name);
        ServiceReminderDTO saved = repository.save(item);
        return new Result("Added", saved);
    }

    @PostMapping("/update")
    public @ResponseBody Result updateItem(@RequestParam long id, @RequestParam String name,
            @RequestParam String category, @RequestParam boolean isComplete) {
        ServiceReminderDTO item = new ServiceReminderDTO(category, name);
        item.setId(id);
        item.setComplete(isComplete);
        ServiceReminderDTO saved = repository.save(item);
        return new Result("Updated", saved); 
     }

    class Result {
        private String status;
        private ServiceReminderDTO item;

        public Result() {
            status = "";
            item = null;
        }
        public Result(String status, ServiceReminderDTO item) {
            this.status = status;
            this.item = item;
        }

        public ServiceReminderDTO getItem() {
            return item;
        }

        public void setItem(ServiceReminderDTO item) {
            this.item = item;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
