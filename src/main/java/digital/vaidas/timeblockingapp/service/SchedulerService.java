package digital.vaidas.timeblockingapp.service;

import digital.vaidas.timeblockingapp.model.Task;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerService {

    private final TaskService taskService;

    public SchedulerService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 0 0 * * *") // Midnight daily
    public void revertUnfinishedTasks() {
        // Fetch all users and revert their tasks (simplified; optimize with a repo query in production)
        taskService.getTasksByUser("user-id-placeholder").stream()
                .map(Task::getUserId)
                .distinct()
                .forEach(taskService::revertUnfinishedTasks);
    }
}