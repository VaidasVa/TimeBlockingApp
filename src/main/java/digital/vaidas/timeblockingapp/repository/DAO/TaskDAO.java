package digital.vaidas.timeblockingapp.repository.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
public class TaskDAO {

    public enum TaskStatus {
        TODO, IN_PROGRESS, DONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "start_time")
    @JsonIgnore
    private LocalDateTime startTime;

    @Column(name = "duration")
    @JsonIgnore
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonIgnore
    private TaskStatus status = TaskStatus.TODO;

    @Column(name = "moved_to_calendar")
    @JsonIgnore
    private Boolean movedToCalendar = false;

    @Column(name = "folder_id")
    @JsonIgnore
    private Long folderId;
}

