package uk.ac.cardiff.mma.application.repository;

import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentUsageDTO;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;

import java.util.List;

public interface EquipmentUsageRepository {
    public List<EquipmentDetailDTO> findAllEquipments();
    public List<OccupiedEquipmentDTO> queryUsedEquipment();
    public List<OccupiedEquipmentDTO> queryBookEquipment();
    public List<EquipmentUsageDTO> queryDateEquipment_byName(String name);
    public List<OccupiedEquipmentDTO> queryUsedEquipment_byName(String name);
    public List<OccupiedEquipmentDTO> queryBookedEquipment_byName(String name);
}
