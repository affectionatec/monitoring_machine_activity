package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentUsageDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentUsageMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new EquipmentUsageDTO(
                resultSet.getString("bookedDate"),
                resultSet.getInt("times")
        ) {};
    }
}