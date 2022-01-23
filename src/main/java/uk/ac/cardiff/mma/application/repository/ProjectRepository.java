package uk.ac.cardiff.mma.application.repository;

import org.springframework.http.ResponseEntity;

public interface ProjectRepository {
    public ResponseEntity createProject(String code, String name);
}
