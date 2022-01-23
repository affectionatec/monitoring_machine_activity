package uk.ac.cardiff.mma.application.role.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.role.entity.Role_dto;

import javax.management.relation.Role;
import java.util.Optional;


@Repository
public interface Role_repository extends JpaRepository<Role_dto, Long>, JpaSpecificationExecutor<Role_dto> {
    Role_dto findByUsername(String username);
    Optional<Role_dto> getUserByUsername(String user);

}
