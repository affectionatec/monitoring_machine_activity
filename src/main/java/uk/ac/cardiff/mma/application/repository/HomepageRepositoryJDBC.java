package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;
import uk.ac.cardiff.mma.application.model.OccupiedEquipmentMapper;

import java.sql.Types;
import java.util.List;

@Repository
public class HomepageRepositoryJDBC implements HomepageRepository{

    JdbcTemplate template;

    @Autowired
    public HomepageRepositoryJDBC(JdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<OccupiedEquipmentDTO> queryOccupiedEquipment() {
        return template.query("select b.id,E.name,b.username,b.date,b.time,b.used,b.project_code from Booking b join Equipment E on E.id = b.equipmentID;"
                ,new OccupiedEquipmentMapper());
    }

    @Override
    public List<OccupiedEquipmentDTO> queryBookingRecord(String username) {
        int type[] = new int[]{
                Types.VARCHAR
        };
        return template.query("select b.id,E.name,b.username,b.date,b.time,b.used,b.project_code from Booking b join Equipment E on E.id = b.equipmentID where b.username = ?;",new Object[]{username},type,new OccupiedEquipmentMapper());
    }

    @Override
    public List<String> getAllProjectCodes() {
        String query = "SELECT code FROM Projects;";
        List<String> projectCodes = template.queryForList(query, String.class);

        return projectCodes;
    }
}
