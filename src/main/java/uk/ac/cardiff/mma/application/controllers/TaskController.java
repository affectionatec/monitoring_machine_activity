package uk.ac.cardiff.mma.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.cardiff.mma.application.enums.TaskListType;
import uk.ac.cardiff.mma.application.equipment.entity.Task;
import uk.ac.cardiff.mma.application.model.TaskVM;
import uk.ac.cardiff.mma.application.service.TaskService;

@Controller
@RequiredArgsConstructor
public class TaskController {


    private final TaskService service;

    @GetMapping("/admin/service_reminder")
    public String index(@PageableDefault(
            size = 5,
            sort = {"createDate"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(defaultValue = "All") TaskListType type,
                        Model model) {
        //Page<Task> tasks = service.findAllByPage(PageRequest.of(page,size, Sort.Direction.DESC,"id"));
        Page<TaskVM> tasks = service.findAllByPage(pageable,type);
        model.addAttribute("page", tasks);
        model.addAttribute("type",type);

        return "Tasks";
    }

    @GetMapping("/tasks/showDone")
    public String showDoneList(@PageableDefault(
            size = 5,
            sort = {"createDate"},
            direction = Sort.Direction.DESC) Pageable pageable,
                               Model model) {
        Page<TaskVM> tasks = service.findAllByPageAndDone(pageable,true);
        model.addAttribute("page", tasks);
        return "Tasks";
    }

    @GetMapping("/tasks/showNotDone")
    public String showNotDoneList(@PageableDefault(
            size = 5,
            sort = {"createDate"},
            direction = Sort.Direction.DESC) Pageable pageable,
                                  Model model) {
        Page<TaskVM> tasks = service.findAllByPageAndDone(pageable,false);
        model.addAttribute("page", tasks);
        return "Tasks";
    }

    @GetMapping("/tasks/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Task task = service.getTaskById(id);
        model.addAttribute("task", task);

        return "TaskDetails";
    }

    @GetMapping("tasks/addTask")
    public String addTask(Model model){
        model.addAttribute("task", new Task());
        return "TaskInput";
    }

    @PostMapping("/addTask")
    public String addTask(Task task){
        service.saveTask(task);

        return "redirect:/admin/service_reminder";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable Long id ,Model model){
        Task task = service.getTaskById(id);
        model.addAttribute("task",task);

        return "TaskInput";
    }

    @GetMapping("/tasks/delete/{id}")
    public String delTask(@PathVariable Long id, @RequestParam int page ,@RequestParam TaskListType type){
        service.deleteTaskById(id);
        return "redirect:/admin/service_reminder"+"?page=" + page + "&type=" + type;
    }

    @GetMapping("/tasks/deleteAll")
    public String delAllTask(){
        service.deleteAllTask();
        return "redirect:/admin/service_reminder";
    }

    @GetMapping("/tasks/tagDone/{id}")
    public String tagDone(@PathVariable Long id , @RequestParam int page ,@RequestParam TaskListType type){
        service.tagDone(id);
        return "redirect:/admin/service_reminder"+"?page="+page+ "&type=" + type;
    }

    @GetMapping("/tasks/tagNotDone/{id}")
    public String tagNotDone(@PathVariable Long id , @RequestParam int page,@RequestParam TaskListType type){
        service.tagNotDone(id);
        return "redirect:/admin/service_reminder"+"?page="+page+ "&type=" + type;
    }

}
