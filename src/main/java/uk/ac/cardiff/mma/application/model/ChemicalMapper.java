package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.ChemicalDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChemicalMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ChemicalDTO(resultSet.getInt("id"),
                resultSet.getString("chemical_name"),
                resultSet.getInt("storage"),
                resultSet.getString("level"),
                resultSet.getString("location"),
                resultSet.getString("unit"),
                resultSet.getInt("bottle_num"));
    }
}
