package uk.ac.cardiff.mma.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.cardiff.mma.application.enums.TaskListType;
import uk.ac.cardiff.mma.application.equipment.entity.Task;
import uk.ac.cardiff.mma.application.equipment.repositories.TaskRepository;
import uk.ac.cardiff.mma.application.model.TaskVM;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository repository;
    public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    @Override
    public Page<TaskVM> findAllByPage(Pageable pageable , TaskListType type) {
        Page<Task> taskPage = null;

        switch (type) {
            case All:
                taskPage = repository.findAll(pageable);
                break;
            case Done:
                taskPage = repository.findAllByDone(pageable,true);
                break;
            case NotDone:
                taskPage = repository.findAllByDone(pageable,false);
                break;
        }

        return taskPage.map(this::toTaskVm);
    }

    @Override
    public Page<TaskVM> findAllByPageAndDone(Pageable pageable, boolean done) {
        Page<Task> taskPage = repository.findAllByDone(pageable, done);

        return taskPage.map(this::toTaskVm);
    }

    private TaskVM toTaskVm(Task x) {
        return TaskVM.builder()
                .id(x.getId())
                .title(x.getTitle())
                .description(x.getDescription().length() > 15
                        ? x.getDescription().substring(0,15) + "..."
                        : x.getDescription())
                .createDate(DATE_FMT.format(x.getCreateDate()))
                .deadLine(DATE_FMT.format(x.getDeadLine()))
                .done(x.getDone())
                .isWarning(x.getDeadLine().isBefore(LocalDateTime.now()) && !x.getDone())
                .build();
    }

    @Override
    public Task getTaskById(Long id) {
        Optional<Task> optionalTask = repository.findById(id);
        return optionalTask.orElse(new Task());
    }

    @Override
    public Task saveTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return repository.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllTask() {
        repository.deleteAll();
    }

    @Override
    public void tagDone(Long id) {
        Task task = this.getTaskById(id);
        task.setDone(true);
        this.saveTask(task);
    }

    @Override
    public void tagNotDone(Long id) {
        Task task = this.getTaskById(id);
        task.setDone(false);
        this.saveTask(task);
    }

}
