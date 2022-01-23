package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.ChemicalDeliveryIDVersionDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChemicalDetialIDVersionMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ChemicalDeliveryIDVersionDTO(resultSet.getInt("id"),
                resultSet.getInt("chemicalID"),
                resultSet.getString("delivery_date"),
                resultSet.getString("expiry"),
                resultSet.getInt("weight"),
                resultSet.getString("unit"));
    }
}
