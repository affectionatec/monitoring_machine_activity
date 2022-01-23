package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentUsageDTO;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;
import uk.ac.cardiff.mma.application.model.EquipmentListMapper;
import uk.ac.cardiff.mma.application.model.EquipmentUsageMapper;
import uk.ac.cardiff.mma.application.model.UsedEquipmentMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class EquipmentUsageRepositoryJDBC implements EquipmentUsageRepository {

    JdbcTemplate template;

    @Autowired
    public EquipmentUsageRepositoryJDBC(JdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<OccupiedEquipmentDTO> queryUsedEquipment() {
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String toDay = dateFormat.format(now);
        return template.query("select b.id,E.name,b.username,b.date,b.time from Booking b join Equipment E on E.id = b.equipmentID where b.used=1;"
         ,new UsedEquipmentMapper());
    }

    @Override
    public List<OccupiedEquipmentDTO> queryBookEquipment() {

        return template.query("select b.id,E.name,b.username,b.date,b.time from Booking b join Equipment E on E.id = b.equipmentID;"
                 ,new UsedEquipmentMapper());
    }

    @Override
    public List<EquipmentDetailDTO> findAllEquipments() {

        return template.query(
                "SELECT id, name FROM Equipment;",
                new EquipmentListMapper());
    }

    @Override
    public List<EquipmentUsageDTO> queryDateEquipment_byName(String name) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = dateFormat.format(now);
        return template.query("SELECT bookedDate, COUNT(*) times FROM (select E.name name,b.date bookedDate from Booking b join Equipment E on E.id = b.equipmentID WHERE E.name=? and b.used=1) a GROUP BY bookedDate;"
                ,new String[] {name} ,new EquipmentUsageMapper());
    }

    @Override
    public List<OccupiedEquipmentDTO> queryUsedEquipment_byName(String name) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = dateFormat.format(now);
        return template.query("select b.id,E.name,b.username,b.date,b.time from Booking b join Equipment E on E.id = b.equipmentID where b.used=1 and E.name=?;"
                ,new String[] {name} ,new UsedEquipmentMapper());
    }

    @Override
    public List<OccupiedEquipmentDTO> queryBookedEquipment_byName(String name) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = dateFormat.format(now);
        return template.query("select b.id,E.name,b.username,b.date,b.time from Booking b join Equipment E on E.id = b.equipmentID where E.name=?;"
                ,new String[] {name} ,new UsedEquipmentMapper());
    }


}
