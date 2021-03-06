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
public class Project implements Serializable, BaseEntity<Long>{
    @Serial
    private static final long serialVersionUID = 6809572939833526455L;
    @Id
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "cost")
    Integer cost;
    @Column(name = "company_id")
    Long companyId;
    @Column(name = "customer_id")
    Long customerId;
    @Column(name = "start_date")
    String startDate;

}
