package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.ChemicalDeliveryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChemicalDetailMapper implements RowMapper{


    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ChemicalDeliveryDTO(resultSet.getInt("id"),
                resultSet.getString("chemical_name"),
                resultSet.getString("delivery_date"),
                resultSet.getString("expiry"),
                resultSet.getInt("weight"),
                resultSet.getString("unit"));
    }
}
