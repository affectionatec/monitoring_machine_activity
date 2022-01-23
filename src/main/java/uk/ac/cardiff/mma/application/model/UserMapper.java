package uk.ac.cardiff.mma.application.model;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.cardiff.mma.application.DTO.UserDTO;


import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserDTO> {

    @Override
    public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserDTO(
                rs.getString("username"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("birth"),
                rs.getString("email"),
                rs.getString("type")

        );
    }

}

