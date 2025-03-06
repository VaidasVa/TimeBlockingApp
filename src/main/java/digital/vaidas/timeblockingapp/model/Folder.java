package digital.vaidas.timeblockingapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Folder {

    private Long id;
    private String userId;
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();
}