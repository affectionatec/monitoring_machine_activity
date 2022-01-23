package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.InvoiceDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceMapper implements RowMapper {
    @Override
    public InvoiceDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new InvoiceDTO(
                resultSet.getString("equipmentName"),
                resultSet.getInt("halfHours"),
                resultSet.getFloat("chargeRate"),
                resultSet.getFloat("totalCharge")
        );
    }
}
