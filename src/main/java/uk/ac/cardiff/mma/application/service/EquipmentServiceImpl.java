package uk.ac.cardiff.mma.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.mma.application.equipment.entity.Equipment;
import uk.ac.cardiff.mma.application.equipment.repositories.EquipmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService{

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    public List<Equipment> getAllEquipment(){
        return equipmentRepository.findAll();
    }

    @Override
    public void saveEquipment(Equipment equipment){
        this.equipmentRepository.save(equipment);
    }


    @Override
    public Equipment getEquipmentById(int id){
        Optional<Equipment> optional = equipmentRepository.findById(id);
        Equipment equipment = null;
        if (optional.isPresent()){
            equipment = optional.get();

        }else{
            throw  new RuntimeException("Equipment not found for id ::" + id);
        }
        return equipment;
    }

    @Override
    public void deleteEquipmentById(int id){
        this.equipmentRepository.deleteById(id);

    }
    @Override
    public Page<Equipment> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.equipmentRepository.findAll(pageable);
    }

}
