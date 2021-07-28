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
@Entity(name = "skills")
public class Skill implements Serializable, BaseEntity<Integer>{
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "skill_id")
    Integer id;
    @Column(length = 45)
    String skill_name;
    @Column(length = 45)
    String skill_level;
}

