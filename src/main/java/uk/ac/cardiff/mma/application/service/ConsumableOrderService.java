package uk.ac.cardiff.mma.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.mma.application.equipment.entity.ConsumableOrder;
import uk.ac.cardiff.mma.application.equipment.repositories.ConsumableOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumableOrderService {
    @Autowired
    private ConsumableOrderRepository repository;


    public void saveOrder(ConsumableOrder order){
        repository.save(order);
    }


    public Optional<ConsumableOrder> getByID(int orderID){
        return repository.findById(orderID);
    }


    public List<ConsumableOrder> findAll(){
        return repository.findAll();
    }


    public ConsumableOrder findByConsumableId(int id){
        return repository.findByConsumable_id(id);
    }

}
