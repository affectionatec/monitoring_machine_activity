package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.EquipmentOperationDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentOperateDate implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new EquipmentOperationDTO(
                resultSet.getString("date")

        ) {
        };
    }
}
