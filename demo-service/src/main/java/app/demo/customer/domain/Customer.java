package app.demo.customer.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

@Table(name = "customer")
public class Customer {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "status")
    public CustomerStatus status;

    @NotNull
    @Column(name = "email")
    public String email;

    @NotNull
    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @NotNull
    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
