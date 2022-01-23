package uk.ac.cardiff.mma.application.equipment.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.equipment.entity.ConsumableDTO;

@EnableJpaRepositories
@Repository
public interface ConsumableRepository extends JpaRepository<ConsumableDTO, Long>{

    @Query(value="select * from consumable c where c.id = ?1",nativeQuery=true)
    ConsumableDTO findByConsumableID(long id);

}