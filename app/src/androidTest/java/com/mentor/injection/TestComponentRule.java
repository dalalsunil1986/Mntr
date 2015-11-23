package com.mentor.injection;

import android.support.test.InstrumentationRegistry;

import com.mentor.MentorApp;
import com.mentor.injection.component.DaggerTestComponent;
import com.mentor.injection.component.TestComponent;
import com.mentor.injection.module.ApplicationTestModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by Joel on 23/11/2015.
 */
public class TestComponentRule implements TestRule {
    private TestComponent mTestComponent;


    private void setupDaggerTestComponentInApplication() {
        MentorApp application = MentorApp
                .get(InstrumentationRegistry.getTargetContext());
        if (application.getComponent() instanceof TestComponent) {
            mTestComponent = (TestComponent) application.getComponent();
        } else {
            mTestComponent = DaggerTestComponent.builder()
                    .applicationTestModule(new ApplicationTestModule(application))
                    .build();
            application.setComponent(mTestComponent);
        }
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };
    }
}

