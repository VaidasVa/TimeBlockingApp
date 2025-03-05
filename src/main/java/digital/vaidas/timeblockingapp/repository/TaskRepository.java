package digital.vaidas.timeblockingapp.repository;

import digital.vaidas.timeblockingapp.model.Task;
import digital.vaidas.timeblockingapp.repository.DAO.TaskDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskDAO, Long> {
    List<TaskDAO> findByUserId(String userId);
    List<Task> findByFolderId(Long folderId);
}