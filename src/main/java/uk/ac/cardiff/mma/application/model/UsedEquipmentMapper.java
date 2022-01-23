package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsedEquipmentMapper implements RowMapper {


    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        return new OccupiedEquipmentDTO(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("username"),
                rs.getString("date"),
                rs.getString("time"));
    }
}
