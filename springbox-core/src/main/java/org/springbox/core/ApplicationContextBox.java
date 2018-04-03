package org.springbox.core;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ApplicationContextBox {

    private static final String[] IGNORE_BEAN_NAMES = new String[]{
            "org.springframework"
    };

    private static final Class<?>[] IGNORE_IMPLEMENTS_CLASSES = new Class<?>[]{
            BeanDefinitionRegistryPostProcessor.class,
            ApplicationListener.class
    };

    private static Class<? extends Annotation>[] IGNORE_ANNOTATION_CLASSES = new Class[]{
            Configuration.class
    };

    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    public ApplicationContextBox(String... basePackages) {
        this(null, basePackages);
    }

    public ApplicationContextBox(Class<?>... annotatedClasses) {
        this(null, annotatedClasses);
    }

    public ApplicationContextBox(ConfigurableEnvironment environment, String... basePackages) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(basePackages);
        if (environment != null) {
            // 立即刷新profiles
            ConfigurableEnvironment configurableEnvironment = new StandardEnvironment();
            configurableEnvironment.setDefaultProfiles(environment.getDefaultProfiles());
            configurableEnvironment.setActiveProfiles(environment.getActiveProfiles());
            context.setEnvironment(configurableEnvironment);
        }
        context.refresh();
        this.context = context;
    }

    public ApplicationContextBox(ConfigurableEnvironment environment, Class<?>... annotatedClasses) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(annotatedClasses);
        if (environment != null)
            context.setEnvironment(environment);
        context.refresh();
        this.context = context;
    }

    public List<BeanContextInfo> getAgentContextBeansInfo() {
        List<BeanContextInfo> beansInfo = new ArrayList<>();
        for (String name : context.getBeanDefinitionNames()) {
            Object obj = context.getBean(name);
            Class<?> clazz = obj.getClass();
            boolean contextBean = true;
            for (String ignore : IGNORE_BEAN_NAMES) {
                if (name.startsWith(ignore)) {
                    contextBean = false;
                    break;
                }
            }
            for (Class<?> ignore : IGNORE_IMPLEMENTS_CLASSES) {
                for (String beanName : context.getBeanNamesForType(ignore)) {
                    if (name.equals(beanName)) {
                        contextBean = false;
                        break;
                    }
                }
            }
            for (Class<? extends Annotation> ignore : IGNORE_ANNOTATION_CLASSES) {
                for (String beanName : context.getBeanNamesForAnnotation(ignore)) {
                    if (name.equals(beanName)) {
                        contextBean = false;
                        break;
                    }
                }
            }
            if (contextBean) {
                beansInfo.add(new BeanContextInfo(name, obj, clazz));
            }
        }
        return beansInfo;
    }
}