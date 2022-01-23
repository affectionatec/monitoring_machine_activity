package uk.ac.cardiff.mma.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.equipment.entity.ServiceReminderDTO;
import uk.ac.cardiff.mma.application.model.ServiceReminderViewModel;
import uk.ac.cardiff.mma.application.equipment.repositories.ServiceReminderRepository;

@Controller
public class ServiceReminderController {



	@Autowired
    private ServiceReminderRepository repository;

//	@GetMapping("/admin/service_reminder")
//	public String index(Model model) {
//		Iterable<ServiceReminderDTO> todoList = repository.findAll();
//		model.addAttribute("items", new ServiceReminderViewModel(todoList));
//		model.addAttribute("newitem", new ServiceReminderDTO());
//    	return "ServiceReminder";
//	}


	@PostMapping("/add_services_reminder")
	public String add(@ModelAttribute ServiceReminderDTO requestItem) {
		ServiceReminderDTO item = new ServiceReminderDTO(requestItem.getCategory(),requestItem.getName());
		repository.save(item);
	  	return "redirect:/admin/service_reminder";
	}

	@PostMapping("/update_services_reminder")
	public String update(@ModelAttribute ServiceReminderViewModel requestItems) {
		for (ServiceReminderDTO requestItem : requestItems.getTodoList() ) {
			ServiceReminderDTO item = new ServiceReminderDTO(requestItem.getCategory(), requestItem.getName());
			item.setComplete(requestItem.isComplete());
			item.setId(requestItem.getId());
			repository.save(item);
		}
		return "redirect:/admin/service_reminder";
	}

  
}