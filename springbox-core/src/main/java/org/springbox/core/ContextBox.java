package org.springbox.core;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ContextBox {

    String[] value() default {};

    Class<?>[] classes() default {};
}