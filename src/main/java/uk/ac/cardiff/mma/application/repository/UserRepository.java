package uk.ac.cardiff.mma.application.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.DTO.UserDTO;


import java.util.List;


public interface UserRepository{
    public List<UserDTO> findUsers();
}

