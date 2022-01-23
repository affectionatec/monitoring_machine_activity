package uk.ac.cardiff.mma.application.equipment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cardiff.mma.application.equipment.entity.Equipment;



public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    public Equipment findEquipmentById (int id);


}
