package uk.ac.cardiff.mma.application.repository;

import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;

import java.util.List;

public interface EquipmentDetailRepository {

    public Object findAllEquipmentDetails();
    public List<EquipmentDetailDTO> findAllEquipmentDetails_byName(String name);

}
