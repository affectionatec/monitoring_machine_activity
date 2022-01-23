package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.RequestTrainedDTO;
import uk.ac.cardiff.mma.application.DTO.UserEquipmentTrainedDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserEquipmentTrainedMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return new RequestTrainedDTO(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("username"),
                resultSet.getString("date_trained"));
    }

}
