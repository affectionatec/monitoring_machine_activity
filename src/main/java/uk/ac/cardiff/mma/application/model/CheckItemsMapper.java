package uk.ac.cardiff.mma.application.model;

import uk.ac.cardiff.mma.application.DTO.CheckItemsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckItemsMapper implements RowMapper {


    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return new CheckItemsDTO(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("mode"),
                resultSet.getString("value"));
    }
}
