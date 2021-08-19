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
    @Column(name = "",length = 45)
    String developer_name;
    @Column(name = "age")
    Integer age;
    @Column(name = "sex",length = 6)
    String gender;
    @Column(name = "salary")
    Integer salary;
    @Column(name = "company_id")
    Company companyId;
}
