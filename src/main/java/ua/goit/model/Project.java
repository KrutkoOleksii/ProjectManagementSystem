package ua.goit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "projects")
public class Project implements Serializable, BaseEntity<Integer>{
    @Serial
    private static final long serialVersionUID = 6809572939833526455L;
    @Id
    @Column(name = "project_id")
    Integer id;
    String project_name;
    Integer cost;
    Integer company_id;
    Integer customer_id;
}
