package jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Developer {

    @Column
    Integer id;
    String name;
    Integer age;
    String sex;
    Integer salary;
    Integer company_id;

}
