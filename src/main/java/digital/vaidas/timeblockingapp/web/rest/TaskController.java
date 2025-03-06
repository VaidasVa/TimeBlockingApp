package digital.vaidas.timeblockingapp.web.rest;
import digital.vaidas.timeblockingapp.model.Task;
import digital.vaidas.timeblockingapp.service.TaskService;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                           @RequestBody Task taskRequest) {
        System.out.println("Principal: " + principal);
        String userId = principal.getAttribute("sub");
        System.out.println("User ID: " + userId);
        if (userId == null) {
            throw new IllegalStateException("User not authenticated");
        }
        return taskService.createTask(userId, taskRequest);
    }


    @PutMapping("/{taskId}/move-to-calendar")
    public Task moveToCalendar(@PathVariable Long taskId) {
        return taskService.moveToCalendar(taskId);
    }

    @PutMapping("/{taskId}/move-to-folder")
    public Task moveToFolder(@PathVariable Long taskId, @RequestParam Long folderId) {
        return taskService.moveToFolder(taskId, folderId);
    }

    @GetMapping
    public List<Task> getTasks(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        String userId = principal.getAttribute("sub");
        return taskService.getTasksByUser(userId);
    }

    @GetMapping("/folder/{folderId}")
    public List<Task> getTasksInFolder(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                                       @PathVariable Long folderId) {
        String userId = principal.getAttribute("sub");
        return taskService.getTasksInFolder(userId, folderId);
    }
}