package digital.vaidas.timeblockingapp.service;

import digital.vaidas.timeblockingapp.model.Task;
import digital.vaidas.timeblockingapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String userId, String title, String description,
                           LocalDateTime startTime, LocalDateTime endTime, Long folderId) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        Task task = new Task();
        task.setUserId(userId);
        task.setTitle(title);
        task.setDescription(description);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        task.setFolderId(folderId); // Optional folder assignment
        return taskRepository.save(task);
    }

    public Task moveToCalendar(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setMovedToCalendarAt(LocalDateTime.now());
        task.setFolderId(null); // Remove from folder when moved to calendar
        return taskRepository.save(task);
    }

    public Task moveToFolder(Long taskId, Long folderId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setFolderId(folderId);
        task.setMovedToFolderAt(LocalDateTime.now());
        task.setMovedToCalendarAt(null); // Remove from calendar
        return taskRepository.save(task);
    }

    public void revertUnfinishedTasks(String userId) {
        List<Task> calendarTasks = taskRepository.findByUserIdAndMovedToCalendarAtIsNotNull(userId);
        LocalDate today = LocalDate.now();

        for (Task task : calendarTasks) {
            if (!task.getStatus().equals(Task.TaskStatus.DONE) &&
                    task.getStartTime().toLocalDate().isBefore(today)) {
                // Move back to original folder or null if no folder
                task.setMovedToCalendarAt(null);
                task.setMovedToFolderAt(LocalDateTime.now());
                taskRepository.save(task);
            }
        }
    }

    public List<Task> getTasksByUser(String userId) {
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getTasksInFolder(String userId, Long folderId) {
        return taskRepository.findByFolderId(folderId);
    }
}