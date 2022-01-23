package uk.ac.cardiff.mma.application.equipment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.mma.application.equipment.entity.ConsumableOrder;

@Repository
public interface ConsumableOrderRepository extends JpaRepository<ConsumableOrder, Integer> {

    @Query(value = "select * from consumable_order o where o.id = ?1", nativeQuery = true)
    ConsumableOrder findByConsumable_id(long id);
}
