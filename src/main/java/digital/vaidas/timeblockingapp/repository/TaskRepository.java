package digital.vaidas.timeblockingapp.repository;

import digital.vaidas.timeblockingapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(String userId);
    List<Task> findByFolderId(Long folderId);
    List<Task> findByUserIdAndMovedToCalendarAtIsNotNull(String userId);
    List<Task> findByUserIdAndFolderIdIsNotNull(String userId);
}