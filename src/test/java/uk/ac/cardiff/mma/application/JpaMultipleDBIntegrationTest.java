package uk.ac.cardiff.mma.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.cardiff.mma.application.equipment.entity.Equipment;
import uk.ac.cardiff.mma.application.equipment.repositories.EquipmentRepository;
import uk.ac.cardiff.mma.application.role.entity.Role_dto;
import uk.ac.cardiff.mma.application.role.repositories.Role_repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
public class JpaMultipleDBIntegrationTest {

    @Autowired
    private Role_repository role_repository;

    @Autowired
    private EquipmentRepository equipmentRepository;


    @Test
    @Transactional("securityTransactionManager")
    public void whenCreateUser_thenCreated(){
        Role_dto user = new Role_dto();
        user.setUsername("TestUser");
        user.setPassword("secret");
        user.setRole("ROLE_USER");
        user.setEnabled(false);
        user = role_repository.save(user);

        assertNotNull(role_repository.findByUsername("TestUser"));


    }



    @Test
    @Transactional("applicationTransactionManager")
    public void whenCreateEquipment_thenCreated(){
        Equipment equipment = new Equipment();
        equipment.setName("TestEquipment");
        equipment.setInventory(1);
        equipment.setCharge_rate(100);
        assertNotNull(equipmentRepository.findEquipmentById(1));
    }









}
