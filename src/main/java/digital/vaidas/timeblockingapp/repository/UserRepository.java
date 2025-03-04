package digital.vaidas.timeblockingapp.repository;

import digital.vaidas.timeblockingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
