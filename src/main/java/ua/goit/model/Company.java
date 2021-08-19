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
@Entity(name = "companies")
public class Company implements Serializable, BaseEntity<Integer>{
    @Serial
    private static final long serialVersionUID = 1785452969944528159L;
    @Id
    @Column(name = "company_id")
    Integer id;
    @Column(name = "company_name", length = 45)
    String name;
    @Column(name = "company_code",length = 10)
    String code;
}
