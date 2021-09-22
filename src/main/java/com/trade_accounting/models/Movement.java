package com.trade_accounting.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movement")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "when_changed_date")
    private LocalDate whenСhangedDate;

//    Со склада
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse warehouseFrom;
// На склад
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse warehouseTo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employeeChanged;

    @Column(name = "is_sent")
    @ColumnDefault("false")
    private Boolean isSent = false;

    @Column(name = "is_print")
    @ColumnDefault("false")
    private Boolean isPrint = false;

    @Column(name = "is_spend")
    @ColumnDefault("false")
    private Boolean isSpend = false;

    @Column(name = "comment")
    private String comment;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MovementProduct> movementProducts;
}
