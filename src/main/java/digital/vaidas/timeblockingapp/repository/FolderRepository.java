package digital.vaidas.timeblockingapp.repository;

import digital.vaidas.timeblockingapp.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByUserId(String userId);
}