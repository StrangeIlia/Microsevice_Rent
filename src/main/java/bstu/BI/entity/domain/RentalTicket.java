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
    private Long userId;
    @Column(nullable = false)
    private Long bookTypeId;
    @Column(nullable = false)
    private Double rentPrice;
    @Column(nullable = false)
    private Double purchasePrice;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date rentalStart = new Date();
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date rentalFinish;
}
