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
@Entity(name = "developers")
public class Developer implements Serializable, BaseEntity<Integer>{
    @Serial
    private static final long serialVersionUID = 6225466248402352490L;
    @Id
    @Column(name = "developer_id")
    Integer id;
    @Column(length = 45)
    String developer_name;
    Integer age;
    @Column(length = 6)
    String sex;
    Integer salary;
    Company company_id;
}
