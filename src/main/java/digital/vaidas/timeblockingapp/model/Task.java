package digital.vaidas.timeblockingapp.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {

    public enum TaskStatus {
        TODO, IN_PROGRESS, DONE
    }

    private Long id;
    private String userId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startTime;
    private int duration;
    private TaskStatus status;
    private Boolean movedToCalendar;
    private Long folderId;
}

