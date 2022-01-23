package uk.ac.cardiff.mma.application.JwtAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uk.ac.cardiff.mma.application.role.entity.Role_dto;
import uk.ac.cardiff.mma.application.role.repositories.Role_repository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private Role_repository role_repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Role_dto user = role_repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
