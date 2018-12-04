package app.demo;

import app.DemoServiceApp;
import core.framework.test.module.AbstractTestModule;

/**
 * @author neo
 */
public class TestModule extends AbstractTestModule {
    @Override
    protected void initialize() {
        load(new DemoServiceApp());
    }
}
