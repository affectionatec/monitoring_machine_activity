package uk.ac.cardiff.mma.application.repository;

import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.DTO.LaundryDetailDTO;

import java.util.List;

public interface LaundryRepository {
    public List<LaundryDetailDTO> findAllLaundryInfo();
    public List<LaundryDetailDTO> findAllLaundryInfo_byBarcode(String barcode);
    public List<LaundryDetailDTO> findAllSuitInfo_byBarcode(String barcode);
    public List<LaundryDetailDTO> findAllHoodInfo_byBarcode(String barcode);
    public List<LaundryDetailDTO> findAllBootsInfo_byBarcode(String barcode);
    public boolean insertSuit(String status,String size,int laundryId);
    public boolean insertBoots(String status,String size,int laundryId);
    public boolean insertHood(String status,String size,int laundryId);
    public boolean updateSuit(int id,String status,String size);
    public boolean updateBoots(int id,String status,String size);
    public boolean updateHood(int id,String status,String size);
    public boolean updateLaundry(String barcode,String status,String comment);
    public boolean deleteLaundry(String barcode);
    public boolean deleteSuit(int id);
    public boolean deleteBoots(int id);
    public boolean deleteHood(int id);
}
