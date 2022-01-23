package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.model.EquipmentDetailMapper;

import java.util.List;

@Repository
public class EquipmentDetailRepositoryJDBC implements EquipmentDetailRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EquipmentDetailRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EquipmentDetailDTO> findAllEquipmentDetails() {
        System.out.println("Sending SQL request to the database");
        return jdbcTemplate.query(
                "SELECT id, name, inventory FROM Equipment;",
                new EquipmentDetailMapper());
    }

    @Override
    public List<EquipmentDetailDTO> findAllEquipmentDetails_byName(String name) {

        return jdbcTemplate.query(
                "SELECT id, name, inventory FROM Equipment where name=?;",
                new String[] {name}, new EquipmentDetailMapper());
    }

}
