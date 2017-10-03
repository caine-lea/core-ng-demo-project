package app;

import core.framework.api.App;
import core.framework.api.module.SystemModule;

/**
 * @author neo
 */
public class DemoServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        http().httpPort(8080);
        http().httpsPort(8443);
        load(new ProductModule());
//        load(new JobModule());
    }
}
