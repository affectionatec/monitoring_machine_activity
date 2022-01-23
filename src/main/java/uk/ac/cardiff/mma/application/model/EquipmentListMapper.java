package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EquipmentListMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new EquipmentDetailDTO(
                resultSet.getInt("id"),
                resultSet.getString("name")
        ) {};
    }
}
