package ua.goit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serial;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "developer_project")
public class DeveloperProject {

    @Serial
    private static final long serialVersionUID = 2L;

    @Column(name = "developer_id")
    private Long developerId;

    @Column(name = "project_id")
    private Long projectId;

}
