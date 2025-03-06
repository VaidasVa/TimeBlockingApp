package digital.vaidas.timeblockingapp.repository;

import digital.vaidas.timeblockingapp.repository.DAO.FolderDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<FolderDAO, Long> {
    List<FolderDAO> findByUserId(String userId);
}