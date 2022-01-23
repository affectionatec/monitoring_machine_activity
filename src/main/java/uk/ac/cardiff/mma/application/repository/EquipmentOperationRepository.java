package uk.ac.cardiff.mma.application.repository;

import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentOperationDTO;

import java.util.List;

public interface EquipmentOperationRepository {
    public List<EquipmentOperationDTO> findAllEquipmentOperationData();
    public List<String> findAllEquipmentOperateDates();
    public List<String> findAllEquipmentOperateRangeDates(String startDate,String endDate);
    public List<EquipmentOperationDTO> findEquipmentOperationDataRangeDate(String startDate,String endDate);
    public boolean insertEquipmentOperateDates(int eid, String date);
    public boolean deleteOperateData(int id);
    public List<EquipmentDetailDTO> findAllEquipments_byName(String name);
    public boolean insertOperateData(int operationId,int itemId,String value);
    public List<EquipmentDetailDTO> findEquipmentOperationData_byNameDate(int eid,String date);

}
