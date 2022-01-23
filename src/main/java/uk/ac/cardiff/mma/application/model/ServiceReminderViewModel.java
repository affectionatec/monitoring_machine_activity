package uk.ac.cardiff.mma.application.model;

import uk.ac.cardiff.mma.application.equipment.entity.ServiceReminderDTO;

import javax.validation.Valid;
import java.util.ArrayList;

public class ServiceReminderViewModel {

	@Valid
	private ArrayList<ServiceReminderDTO> todoList = new ArrayList<ServiceReminderDTO>();
	
	public ServiceReminderViewModel() {}
	
	public ServiceReminderViewModel(Iterable<ServiceReminderDTO> items) {
		items.forEach(todoList:: add);
	}

	public ServiceReminderViewModel(ArrayList<ServiceReminderDTO> todoList) {
		this.todoList = todoList;
	}

	public ArrayList<ServiceReminderDTO> getTodoList() {
		return todoList;
	}

	public void setTodoList(ArrayList<ServiceReminderDTO> todoList) {
		this.todoList = todoList;
	}
}