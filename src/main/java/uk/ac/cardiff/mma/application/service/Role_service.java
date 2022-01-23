package uk.ac.cardiff.mma.application.service;


import uk.ac.cardiff.mma.application.role.entity.Role_dto;

import java.util.List;

public interface Role_service {

    List<Role_dto> getAllUsers();

    void saveUser(Role_dto user);

    Role_dto getUserById(long id);

    void deleteUserById(long id);

    Role_dto getUserByUsername(String username);





}
