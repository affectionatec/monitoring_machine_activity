package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.LaundryDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LaundryListMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LaundryDetailDTO(
                resultSet.getString("laundry_id"),
                resultSet.getString("barcode"),
                resultSet.getString("status"),
                resultSet.getString("comment")
        );
    }
}
