package bstu.BI.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class RentalTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonIgnore
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private Integer bookTypeId;
    @Column(nullable = false)
    private Double rentPrice;
    @Column(nullable = false)
    private Double purchasePrice;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date rentalStart;
    @Column(nullable = false)
    private Date rentalFinish;
}
