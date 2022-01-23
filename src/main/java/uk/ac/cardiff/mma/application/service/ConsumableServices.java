package uk.ac.cardiff.mma.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.mma.application.equipment.entity.ConsumableDTO;
import uk.ac.cardiff.mma.application.equipment.repositories.ConsumableRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumableServices {
	
	@Autowired
	ConsumableRepository repository;
	
	public ConsumableDTO saveConsumable(ConsumableDTO consumableDTO) {
		return repository.save(consumableDTO);
	}
	
	public List<ConsumableDTO> getConsumableInfos(){
		return repository.findAll();
	}
	
	public Optional<ConsumableDTO> getConsumableById(long id) {
		return repository.findById(id);
	}
	
	public boolean checkExistedConsumable(long id) {
		if(repository.existsById((long) id)) {
			return true;
		}
		return false;
	}

	public ConsumableDTO findOne(long ID) {
		return repository.findByConsumableID(ID);
	}

	
	public ConsumableDTO updateConsumable(ConsumableDTO consumableDTO) {
		return repository.save(consumableDTO);
	}
	
	public void deleteConsumableById(long id) {
		repository.deleteById(id);
	}
}