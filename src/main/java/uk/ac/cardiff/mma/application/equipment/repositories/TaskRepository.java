package uk.ac.cardiff.mma.application.equipment.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cardiff.mma.application.equipment.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAll(Pageable pageable);

    Page<Task> findAllByDone(Pageable pageable, boolean done);


}
