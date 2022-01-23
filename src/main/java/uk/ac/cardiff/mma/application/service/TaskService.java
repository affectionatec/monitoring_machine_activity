package uk.ac.cardiff.mma.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.cardiff.mma.application.enums.TaskListType;
import uk.ac.cardiff.mma.application.equipment.entity.Task;
import uk.ac.cardiff.mma.application.model.TaskVM;

import java.util.List;

public interface TaskService {
    List<Task> findAllTasks();

    Page<TaskVM> findAllByPage(Pageable pageable , TaskListType type);

    Page<TaskVM> findAllByPageAndDone(Pageable pageable, boolean done);

    Task getTaskById(Long id);

    Task saveTask(Task task);

    Task updateTask(Task task);

    void deleteTaskById(Long id);

    void deleteAllTask();

    void tagDone(Long id);

    void tagNotDone(Long id);
}
