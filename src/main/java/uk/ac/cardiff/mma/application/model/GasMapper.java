package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.GasDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GasMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new GasDTO(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("storage"),
                resultSet.getString("unit"),
                resultSet.getInt("bottleNum"),
                resultSet.getString("coshh"),
                resultSet.getString("location"),
                resultSet.getString("hazardLevel"),
                resultSet.getString("comments")
        );
    }

}