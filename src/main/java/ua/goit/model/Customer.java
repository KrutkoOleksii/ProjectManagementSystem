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
@Entity(name = "customers")
public class Customer implements Serializable, BaseEntity<Integer>{
    @Serial
    private static final long serialVersionUID = 6696391088550445549L;
    @Id
    @Column(name = "customer_id")
    Integer id;
    @Column(length = 45)
    String customer_name;
    @Column(length = 10)
    String customer_code;
}
