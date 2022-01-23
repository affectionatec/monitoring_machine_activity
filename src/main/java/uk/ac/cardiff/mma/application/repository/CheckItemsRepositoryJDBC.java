package uk.ac.cardiff.mma.application.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.CheckItemsDTO;
import uk.ac.cardiff.mma.application.DTO.ChemicalDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.model.CheckItemsMapper;
import uk.ac.cardiff.mma.application.model.EquipmentListMapper;

import java.util.List;

@Repository
public class CheckItemsRepositoryJDBC implements CheckItemsRepository{

    JdbcTemplate template;

    @Autowired
    public CheckItemsRepositoryJDBC(JdbcTemplate template){
        this.template = template;
    }



    @Override
    public List<CheckItemsDTO> findAllItems(){
        return template.query(
                "SELECT id, name,mode,value FROM OperationCheckItems;",
                new CheckItemsMapper());
    };

    @Override
    public List<CheckItemsDTO> findAllItems_byName(String name){
        return template.query(
                "SELECT id, name,mode,value FROM OperationCheckItems where name=?;",
                new String[]{name},new CheckItemsMapper());
    };

    @Override
    public boolean insertCheckItem(String name,String mode, String value){
        int rows = template.update(
                "INSERT INTO OperationCheckItems (name,mode,value) VALUES (?,?,?)",
                new Object[]{name,mode,value});
        return rows>0;
    }

    @Override
    public boolean updateCheckItem(int id, String name,String mode, String value){
        int rows = template.update(
                "update OperationCheckItems set name='" + name +"', mode='" + mode +"', value='" + value+"'where id='" + id +"'");
        return rows>0;
    }

    @Override
    public boolean deleteCheckItem(int id, String name){
        int rows = template.update(
                "delete from OperationCheckItems where id='" + id +"' and name='" + name +"'");
        return rows>0;
    }
}
