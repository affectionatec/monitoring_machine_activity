package uk.ac.cardiff.mma.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.mma.application.role.entity.Role_dto;
import uk.ac.cardiff.mma.application.role.repositories.Role_repository;

import java.util.List;
import java.util.Optional;

@Service
public class Role_service_impl implements Role_service {

    @Autowired
    Role_repository user_repository;


    @Override
    public List<Role_dto> getAllUsers() {
        return user_repository.findAll();
    }

    @Override
    public void saveUser(Role_dto user) {
        this.user_repository.save(user);

    }

    @Override
    public Role_dto getUserById(long id) {
        Optional<Role_dto> optional = user_repository.findById(id);
        Role_dto user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException(" user not found for id :: " + id);
        }
        return user;
    }

    @Override
    public void deleteUserById(long id) {
        this.user_repository.deleteById(id);

    }


    @Override
    public Role_dto getUserByUsername(String username){
        Optional <Role_dto> optional = user_repository.getUserByUsername(username);
        Role_dto user = null;

        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException(" user not found for username :: " + username);
        }
        return user;


    }



}
