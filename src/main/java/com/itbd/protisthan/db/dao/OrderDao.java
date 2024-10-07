package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "erp_over", indexes = {
        @Index(name = "id_customer_key", columnList = "id_customer_key"),
        @Index(name = "id_employee_key", columnList = "id_employee_key"),
})
public class OrderDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_key", nullable = false)
    private Long id;

    @Column(name = "dt_order_date")
    private LocalDate orderDate;

    @Column(name = "dt_required_date")
    private LocalDate requiredDate;

    @Column(name = "dt_shipped_date")
    private LocalDate shippedDate;

    @Column(name = "flt_freight")
    private Double freight;

    @ManyToOne
    @JoinColumn(name = "id_customer_key")
    private CustomerDao customer;

    @ManyToOne
    @JoinColumn(name = "id_employee_key")
    private EmployeeDao employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mode_of_payment_key")
    private ModeOfPaymentDao modeOfPayment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetailDao> orderDetails;
}