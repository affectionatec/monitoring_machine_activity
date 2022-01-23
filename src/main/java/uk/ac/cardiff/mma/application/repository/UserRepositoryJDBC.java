package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.UserDTO;
import uk.ac.cardiff.mma.application.model.UserMapper;


import java.util.List;

@Repository
public class UserRepositoryJDBC implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryJDBC(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;
    }

    @Override
    public List<UserDTO> findUsers(){
        return jdbcTemplate.query("SELECT * FROM Users",
                new UserMapper());
    }

}
