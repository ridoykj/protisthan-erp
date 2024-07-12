package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_role", schema = "erp_over")
public class RoleDao {
    @Id
    @Size(max = 140)
    @Column(name = "tx_role_key", nullable = false, length = 140)
    private String id;

    @Column(name = "is_bulk_actions")
    private Boolean isBulkActions;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "is_dashboard")
    private Boolean isDashboard;

    @Column(name = "is_desk_access")
    private Boolean isDeskAccess;

    @Column(name = "is_disabled")
    private Boolean isDisabled;

    @Size(max = 140)
    @Column(name = "tx_home_page", length = 140)
    private String homePage;

    @Column(name = "ct_idx")
    private Integer idx;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Column(name = "is_notifications")
    private Boolean isNotifications;

    @Size(max = 140)
    @Column(name = "tx_restrict_to_domain", length = 140)
    private String restrictToDomain;

    @Column(name = "is_two_factor_auth")
    private Boolean twoFactorAuth;

}