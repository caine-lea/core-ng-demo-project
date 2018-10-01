package app.demo.api.product.kafka;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author neo
 */
public class ProductUpdatedMessage {
    @NotNull(message = "id is required")
    @Property(name = "id")
    public String id;

    @Property(name = "name")
    public String name;

    @Property(name = "desc")
    public String desc;
}
