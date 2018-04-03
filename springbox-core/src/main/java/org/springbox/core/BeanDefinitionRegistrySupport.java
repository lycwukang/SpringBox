package org.springbox.core;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;
import java.util.UUID;

public abstract class BeanDefinitionRegistrySupport implements BeanDefinitionRegistryPostProcessor {

    private static final String applicationContextBoxManagerBeanName = "applicationContextBoxManager";
    private static final String applicationContextBoxAppendBeanName = "$applicationContextBox";

    private String[] packageNames = new String[0];
    private Class<?>[] classes = new Class<?>[0];

    public void setPackageNames(String[] packageNames) {
        this.packageNames = packageNames;
    }

    public void setClasses(Class<?>[] classes) {
        this.classes = classes;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 获取package
        List<String> packageNames = Lists.newArrayList(this.packageNames);
        List<Class<?>> classes = Lists.newArrayList(this.classes);
        if (this.getClass().isAnnotationPresent(ContextBox.class)) {
            ContextBox contextAgent = this.getClass().getAnnotation(ContextBox.class);
            packageNames.addAll(Lists.newArrayList(contextAgent.value()));
            classes.addAll(Lists.newArrayList(contextAgent.classes()));
        }
        for (Class<?> clazz : classes) {
            if (!packageNames.contains(clazz.getPackage().getName())) {
                packageNames.add(clazz.getPackage().getName());
            }
        }
        ApplicationContextBoxManager beanAgentManager = getBeanForType(beanFactory, ApplicationContextBoxManager.class);
        if (beanAgentManager == null) {
            beanAgentManager = new ApplicationContextBoxManager();
            register(beanFactory, applicationContextBoxManagerBeanName, beanAgentManager, ApplicationContextBoxManager.class);
        }
        for (String packageName : packageNames) {
            ApplicationContextBox context;
            if (!beanAgentManager.containsKey(packageName)) {
                context = beanAgentManager.getOrCreate(beanFactory.getBean(ConfigurableEnvironment.class), packageName);
                beanAgentManager.add(packageName, context);
            } else {
                context = beanAgentManager.getContextBox(packageName);
            }
            List<BeanContextInfo> beanContextInfo = context.getAgentContextBeansInfo();
            for (BeanContextInfo beanInfo : beanContextInfo) {
                register(beanFactory, beanInfo.getName(), beanInfo.getObj(), beanInfo.getClazz());
            }
        }
    }

    private void register(ConfigurableListableBeanFactory beanFactory, String beanName, Object object, Class<?> clazz) {
        // getBeanNamesForType不会遍历parent
        if (beanFactory.getBeanNamesForType(clazz).length == 0) {
            // getSingletonNames不会遍历parent
            if (!isExist(beanFactory.getSingletonNames(), beanName)) {
                beanName = beanName + applicationContextBoxAppendBeanName;
                if (isExist(beanFactory.getSingletonNames(), beanName)) {
                    beanName = beanName + "#" + UUID.randomUUID().toString().replace("-", "");
                }
            }
            beanFactory.registerSingleton(beanName, object);
        }
    }

    private boolean isExist(String[] names, String name) {
        for (String n : names) {
            if (n.equals(name)) return true;
        }
        return false;
    }

    private <T> T getBeanForType(ConfigurableListableBeanFactory beanFactory, Class<T> clazz) {
        // getBeanNamesForType不会遍历parent
        String[] beanNames = beanFactory.getBeanNamesForType(clazz);
        if (beanNames.length > 0) {
            return beanFactory.getBean(beanNames[0], clazz);
        } else if (beanFactory.getParentBeanFactory() != null) {
            return getBeanForType((ConfigurableListableBeanFactory) beanFactory.getParentBeanFactory(), clazz);
        } else {
            return null;
        }
    }
}