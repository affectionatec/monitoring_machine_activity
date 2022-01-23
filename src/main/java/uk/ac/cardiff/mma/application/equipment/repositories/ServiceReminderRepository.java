package uk.ac.cardiff.mma.application.equipment.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.ac.cardiff.mma.application.equipment.entity.ServiceReminderDTO;

public interface ServiceReminderRepository extends CrudRepository<ServiceReminderDTO, Long> {

}