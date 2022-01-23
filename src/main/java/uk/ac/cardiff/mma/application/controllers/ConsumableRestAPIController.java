package uk.ac.cardiff.mma.application.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.cardiff.mma.application.equipment.entity.ConsumableDTO;
import uk.ac.cardiff.mma.application.DTO.Message;
import uk.ac.cardiff.mma.application.service.ConsumableServices;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/consumable")
public class ConsumableRestAPIController {
	
	@Autowired
	ConsumableServices consumableServices;
	
	@PostMapping("/create")
	public ResponseEntity<Message> addNewConsumable(@RequestBody ConsumableDTO consumableDTO) {
		try {
			ConsumableDTO returnedConsumableDTO = consumableServices.saveConsumable(consumableDTO);
			
			return new ResponseEntity<Message>(new Message("Upload Successfully!", 
											Arrays.asList(returnedConsumableDTO), ""), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Fail to post a new Consumable!",
											null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@GetMapping("/retrieveinfos")
	public ResponseEntity<Message> retrieveConsumableInfo() {
		
		try {
			List<ConsumableDTO> consumableDTOInfos = consumableServices.getConsumableInfos();
			
			return new ResponseEntity<Message>(new Message("Get Consumable' Infos!",
					consumableDTOInfos, ""), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Fail!",
												null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findone/{id}")
	public ResponseEntity<Message> getCustomerById(@PathVariable long id) {
		try {
			Optional<ConsumableDTO> optCustomer = consumableServices.getConsumableById(id);
			
			if(optCustomer.isPresent()) {
				return new ResponseEntity<Message>(new Message("Successfully! Retrieve a Consumable by id = " + id,
															Arrays.asList(optCustomer.get()), ""), HttpStatus.OK);
			} else {
				return new ResponseEntity<Message>(new Message("Failure -> NOT Found a Consumable by id = " + id,
						null, ""), HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Failure",
					null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updatebyid/{id}")
	public ResponseEntity<Message> updateCustomerById(@RequestBody ConsumableDTO _consumableDTO,
																	@PathVariable long id) {
		try {
			if(consumableServices.checkExistedConsumable(id)) {
				ConsumableDTO consumableDTO = consumableServices.getConsumableById(id).get();
				
				//set new values for customer
				consumableDTO.setName(_consumableDTO.getName());
				consumableDTO.setDescription(_consumableDTO.getDescription());
				consumableDTO.setType(_consumableDTO.getType());
				consumableDTO.setStock(_consumableDTO.getStock());
	
				// save the change to database
				consumableServices.updateConsumable(consumableDTO);
				
				return new ResponseEntity<Message>(new Message("Successfully! Updated a Consumable "
																		+ "with id = " + id,
																	Arrays.asList(consumableDTO), ""), HttpStatus.OK);
			}else {
				return new ResponseEntity<Message>(new Message("Failer! Can NOT Found a Consumable "
						+ "with id = " + id,
					null, ""), HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Failure",
					null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public ResponseEntity<Message> deleteCustomerById(@PathVariable long id) {
		try {
			// checking the existed of a Customer with id
			if(consumableServices.checkExistedConsumable(id)) {
				consumableServices.deleteConsumableById(id);
				
				return new ResponseEntity<Message> (new Message("Successfully! Delete a Consumable with id = " + id,
														null, ""), HttpStatus.OK);
			}else {
				return new ResponseEntity<Message>(new Message("Failer! Can NOT Found a Consumable "
														+ "with id = " + id, null, ""), HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<Message>(new Message("Failure",
					null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}