package uk.ac.cardiff.mma.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.ac.cardiff.mma.application.DTO.GasDTO;
import uk.ac.cardiff.mma.application.equipment.entity.Task;
import uk.ac.cardiff.mma.application.equipment.repositories.TaskRepository;
import uk.ac.cardiff.mma.application.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceImplTest {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TaskService service;

    @Test
    void whenSaveTask_thenSaveTask(){

        Task task = new Task();
        task.setId(100L);
        task.setTitle("TestEquipment");
        task.setDescription("Description");
        task.setDeadLine(LocalDateTime.now());
        task.setCreateDate(LocalDateTime.now());
        task.setDone(false);

        repository.save(task);
        repository.delete(task);
    }

    @Test
    void updateTaskTest() {
        Task task = service.getTaskById(100L);
        task.setTitle("UpdateEquipmentName");
        task.setDescription("UpdateDescription");
        Task updateData = service.updateTask(task);
        repository.delete(task);
        System.out.println(updateData);
    }

    @Test
    public void TaskDTO() {
        Task task = new Task(1l,"title","description",false);

        assertEquals(task.getTitle(), "title");
        assertEquals(task.getDescription(), "description");
        assertEquals(task.getDone(), false);

    }



}
