package app.demo.api.customer;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

public class CustomerView {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @Property(name = "email")
    public String email;

    @NotNull
    @Property(name = "first_name")
    public String firstName;

    @Property(name = "last_name")
    public String lastName;

    @NotNull
    @Property(name = "updated_time")
    public LocalDateTime updatedTime;
}
