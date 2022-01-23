package uk.ac.cardiff.mma.application.service;

import uk.ac.cardiff.mma.application.equipment.entity.Equipment;
import org.springframework.data.domain.Page;
import java.util.List;

public interface EquipmentService {
    List<Equipment> getAllEquipment();
    void saveEquipment(Equipment equipment);
    Equipment getEquipmentById(int id);
    void deleteEquipmentById(int id);
    Page<Equipment> findPaginated(int pageNo, int PageSize, String sortField, String sortDirection);

}
