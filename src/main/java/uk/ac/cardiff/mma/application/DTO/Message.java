package uk.ac.cardiff.mma.application.DTO;

import uk.ac.cardiff.mma.application.equipment.entity.ConsumableDTO;

import java.util.ArrayList;
import java.util.List;

public class Message {
	private String message = "";
	private List<ConsumableDTO> consumables = new ArrayList<ConsumableDTO>();
	private String error = "";
	
	public Message(String message, List<ConsumableDTO> consumables, String error) {
		this.message = message;
		this.consumables = consumables;
		this.error = error;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<ConsumableDTO> getConsumables(){
		return this.consumables;
	}
	
	public void setConsumables(ArrayList<ConsumableDTO> consumables) {
		this.consumables = consumables;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getError() {
		return this.error;
	}
}