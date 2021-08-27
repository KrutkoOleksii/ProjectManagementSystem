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
public class Customer implements Serializable, BaseEntity<Long>{
    @Serial
    private static final long serialVersionUID = 6696391088550445549L;
    @Id
    @Column(name = "id")
    Long id;
    @Column(name = "name", length = 45)
    String name;
    @Column(name = "code",length = 10)
    String code;
}
