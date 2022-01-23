package uk.ac.cardiff.mma.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.NestedServletException;

@Repository
public class ProjectRepositoryJDBC implements ProjectRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity createProject(String code, String name) {
        String query = "INSERT INTO Projects VALUES (?, ?);";

        try {
            jdbcTemplate.update(query, code, name);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
