package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.EquipmentOperationDTO;
import uk.ac.cardiff.mma.application.DTO.LaundryDetailDTO;
import uk.ac.cardiff.mma.application.model.EquipmentOperationMapper;
import uk.ac.cardiff.mma.application.model.LaundryListMapper;
import uk.ac.cardiff.mma.application.model.LaundryMapper;

import java.util.List;

@Repository
public class LaundryRepositoryJDBC implements LaundryRepository {

    JdbcTemplate template;

    @Autowired
    public LaundryRepositoryJDBC(JdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<LaundryDetailDTO> findAllLaundryInfo() {

        return template.query(
                "select Laundry.id as laundry_id, barcode,status,comment from Laundry;",
                new LaundryListMapper());
    }

    @Override
    public List<LaundryDetailDTO> findAllLaundryInfo_byBarcode(String barcode) {

        return template.query(
                "select Laundry.id as laundry_id, barcode,status,comment from Laundry where barcode=?;",
                new String[] {barcode},new LaundryListMapper());
    }

    @Override
    public List<LaundryDetailDTO> findAllSuitInfo_byBarcode(String barcode) {

        return template.query(
                "select Suit.id,Suit.size as size,Suit.`status` as status from Laundry JOIN Suit on Suit.laundryId=Laundry.id where Laundry.barcode=?;",
                new String[] {barcode},new LaundryMapper());
    }

    @Override
    public List<LaundryDetailDTO> findAllHoodInfo_byBarcode(String barcode) {

        return template.query(
                "select Hood.id,Hood.size as size,Hood.`status` as status from Laundry JOIN Hood on Hood.laundryId=Laundry.id where Laundry.barcode=?;",
                new String[] {barcode},new LaundryMapper());
    }

    @Override
    public List<LaundryDetailDTO> findAllBootsInfo_byBarcode(String barcode) {

        return template.query(
                "select Boots.id ,Boots.size as size,Boots.`status` As status from Laundry JOIN Boots on Laundry.id=Boots.laundryId where Laundry.barcode=?;",
                new String[] {barcode},new LaundryMapper());
    }



    @Override
    public boolean insertSuit(String status,String size,int laundryId){
        int rows = template.update(
                "INSERT INTO Suit ( status,size,laundryId) VALUES (?,?,?)",
                new Object[]{status,size,laundryId});
        return rows>0;
    }

    @Override
    public boolean insertBoots(String status,String size,int laundryId){
        int rows = template.update(
                "INSERT INTO Boots ( status,size,laundryId) VALUES (?,?,?)",
                new Object[]{status,size,laundryId});
        return rows>0;
    }

    @Override
    public boolean insertHood(String status,String size,int laundryId){
        int rows = template.update(
                "INSERT INTO Hood ( status,size,laundryId) VALUES (?,?,?)",
                new Object[]{status,size,laundryId});
        return rows>0;
    }


    @Override
    public boolean updateSuit(int id,String status,String size){
        int rows = template.update(
                "UPDATE Suit set size='" + size + "' , status='" + status + "' where id='" + id + "'");
        return rows>0;
    }

    @Override
    public boolean updateBoots(int id,String status,String size){
        int rows = template.update(
                "UPDATE Boots set size='" + size + "' , status='" + status + "' where id='" + id + "'");
        return rows>0;
    }

    @Override
    public boolean updateHood(int id,String status,String size){
        int rows = template.update(
                "UPDATE Hood set size='" + size + "' , status='" + status + "' where id='" + id + "'");
        return rows>0;
    }

    @Override
    public boolean updateLaundry(String barcode,String status,String comment){
        int rows = template.update(
                "UPDATE Laundry set comment='" + comment + "' , status='" + status + "' where barcode='" + barcode + "'");
        return rows>0;
    }

    @Override
    public boolean deleteLaundry(String barcode){
        int rows = template.update(
                "delete from Laundry where barcode='" + barcode +"'");
        return rows>0;
    }

    @Override
    public boolean deleteSuit(int id){
        int rows = template.update(
                "delete from Suit where id='" + id +"'");
        return rows>0;
    }

    @Override
    public boolean deleteBoots(int id){
        int rows = template.update(
                "delete from Boots where id='" + id +"'");
        return rows>0;
    }

    @Override
    public boolean deleteHood(int id){
        int rows = template.update(
                "delete from Hood where id='" + id +"'");
        return rows>0;
    }




}
