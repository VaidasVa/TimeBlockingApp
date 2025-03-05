package digital.vaidas.timeblockingapp.service;

import digital.vaidas.timeblockingapp.mapper.TaskMapper;
import digital.vaidas.timeblockingapp.model.Task;
import digital.vaidas.timeblockingapp.repository.DAO.TaskDAO;
import digital.vaidas.timeblockingapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String userId, Task task) {
        task.setUserId(userId);
        System.out.println(task.getDuration());
        TaskDAO dao = taskRepository.save(TaskMapper.INSTANCE.toTaskDAO(task));
        return TaskMapper.INSTANCE.toTask(dao);
    }

    public Task moveToCalendar(Long taskId) {
//        Task task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
//        task.setMovedToCalendarAt(LocalDateTime.now());
//        task.setFolderId(null); // Remove from folder when moved to calendar
//        return taskRepository.save(task);
        return null;
    }

    public Task moveToFolder(Long taskId, Long folderId) {
//        Task task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
//        task.setFolderId(folderId);
//        task.setMovedToFolderAt(LocalDateTime.now());
//        task.setMovedToCalendarAt(null); // Remove from calendar
//        return taskRepository.save(task);
        return null;
    }

    public void revertUnfinishedTasks(String userId) {
//        List<Task> calendarTasks = taskRepository.findByUserIdAndMovedToCalendarAtIsNotNull(userId);
//        LocalDate today = LocalDate.now();
//
//        for (Task task : calendarTasks) {
//            if (!task.getStatus().equals(Task.TaskStatus.DONE) &&
//                    task.getStartTime().toLocalDate().isBefore(today)) {
//                // Move back to original folder or null if no folder
//                task.setMovedToCalendarAt(null);
//                task.setMovedToFolderAt(LocalDateTime.now());
//                taskRepository.save(task);
//            }
//        }

    }

    public List<Task> getTasksByUser(String userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(TaskMapper.INSTANCE::toTask)
                .collect(Collectors.toList());
    }

    public List<Task> getTasksInFolder(String userId, Long folderId) {
        return taskRepository.findByFolderId(folderId);
    }
}