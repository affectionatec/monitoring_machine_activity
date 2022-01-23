package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.EquipmentOperationDTO;
import uk.ac.cardiff.mma.application.DTO.EquipmentUsageDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentOperationMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new EquipmentOperationDTO(
                resultSet.getInt("id"),
                resultSet.getString("date"),
                resultSet.getString("equipmentName"),
                resultSet.getString("itemName"),
                resultSet.getString("value")

        ) {
        };
    }

}
