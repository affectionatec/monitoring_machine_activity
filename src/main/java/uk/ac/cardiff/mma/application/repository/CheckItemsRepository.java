package uk.ac.cardiff.mma.application.repository;

import uk.ac.cardiff.mma.application.DTO.CheckItemsDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;

import java.util.List;

public interface CheckItemsRepository {
    public List<CheckItemsDTO> findAllItems();
    public boolean insertCheckItem(String name,String mode, String value);
    public boolean updateCheckItem(int id, String name,String mode, String value);
    public List<CheckItemsDTO> findAllItems_byName(String name);
    public boolean deleteCheckItem(int id, String name);
}
