package digital.vaidas.timeblockingapp.repository;

import digital.vaidas.timeblockingapp.repository.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, String> {
}
