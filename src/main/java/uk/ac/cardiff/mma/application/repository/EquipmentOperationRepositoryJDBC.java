package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentOperationDTO;
import uk.ac.cardiff.mma.application.model.EquipmentListMapper;
import uk.ac.cardiff.mma.application.model.EquipmentOperationMapper;

import java.util.List;

@Repository
public class EquipmentOperationRepositoryJDBC implements EquipmentOperationRepository{

    JdbcTemplate template;

    @Autowired
    public EquipmentOperationRepositoryJDBC(JdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<EquipmentOperationDTO> findAllEquipmentOperationData() {

        return template.query(
                "SELECT EquipmentOperationData.id as id,EquipmentOperationData.date as date, Equipment.`name` as equipmentName, OperationCheckItems.`name` as itemName,OperationData.`value` as value FROM OperationData JOIN OperationCheckItems on OperationData.itemID=OperationCheckItems.id JOIN EquipmentOperationData on OperationData.operationId=EquipmentOperationData.id JOIN Equipment ON Equipment.id=EquipmentOperationData.equipmentID order by 2,3;",
                new EquipmentOperationMapper());
    }

    @Override
    public List<String> findAllEquipmentOperateDates() {
        return template.queryForList(
                " select DISTINCT date FROM EquipmentOperationData",String.class);
    }

    @Override
    public List<String> findAllEquipmentOperateRangeDates(String startDate,String endDate) {
        return template.queryForList(
                " select DISTINCT date FROM EquipmentOperationData where date>='"+startDate+ "' and date<='"+ endDate+"'",String.class);
    }

    @Override
    public List<EquipmentOperationDTO> findEquipmentOperationDataRangeDate(String startDate,String endDate) {

        return template.query(
                "SELECT EquipmentOperationData.id as id,EquipmentOperationData.date as date, Equipment.`name` as equipmentName, OperationCheckItems.`name` as itemName,OperationData.`value` as value FROM OperationData JOIN OperationCheckItems on OperationData.itemID=OperationCheckItems.id JOIN EquipmentOperationData on OperationData.operationId=EquipmentOperationData.id JOIN Equipment ON Equipment.id=EquipmentOperationData.equipmentID where date>=? and date<=? ORDER BY 2,3;",
                new Object[] {startDate,endDate},new EquipmentOperationMapper());
    }

    @Override
    public boolean insertEquipmentOperateDates(int eid, String date){
        int rows = template.update(
                "INSERT INTO EquipmentOperationData ( equipmentID,  date) VALUES (?,?)",
                new Object[]{eid, date});
        return rows>0;
    }

    @Override
    public boolean insertOperateData(int operationId,int itemId,String value){
        int rows = template.update(
                "INSERT INTO OperationData ( operationId, itemID, value) VALUES (?,?,?)",
                new Object[]{operationId, itemId, value});
        return rows>0;
    }

    @Override
    public boolean deleteOperateData(int id){
        int rows = template.update(
                "delete from OperationData where operationId='" + id +"'");
        return rows>0;
    }

    @Override
    public List<EquipmentDetailDTO> findAllEquipments_byName(String name) {

        return template.query(
                "SELECT id, name FROM Equipment where name=?;",
                new String[] {name},new EquipmentListMapper());
    }

    @Override
    public List<EquipmentDetailDTO> findEquipmentOperationData_byNameDate(int eid,String date) {

        return template.query(
                "SELECT id,date name FROM EquipmentOperationData where equipmentID=? and date=? ;",
                new Object[] {eid,date},new EquipmentListMapper());
    }


}
