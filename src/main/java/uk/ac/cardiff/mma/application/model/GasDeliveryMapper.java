package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.GasDeliveryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GasDeliveryMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new GasDeliveryDTO(
                resultSet.getInt("deliveryID"),
                resultSet.getInt("gasID"),
                resultSet.getDate("deliveryDate"),
                resultSet.getDate("expiryDate"),
                resultSet.getInt("distributionWeight"),
                resultSet.getString("deliveryStaff")
        );
    }

}