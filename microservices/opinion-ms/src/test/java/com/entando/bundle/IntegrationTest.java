package com.entando.bundle;

import com.entando.bundle.OpinionApp;
import com.entando.bundle.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { OpinionApp.class, TestSecurityConfiguration.class })
public @interface IntegrationTest {
}
