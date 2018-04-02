package org.springbox.core;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextBoxManager {

    // ====================== 全局保存信息
    private static final Map<String, ApplicationContext> registerApplicationContext = new HashMap<>();
    private static final Map<String, ApplicationContextBox> registerApplicationContextAgent = new HashMap<>();

    public static Map<String, ApplicationContext> getRegisterApplicationContext() {
        return registerApplicationContext;
    }

    public static Map<String, ApplicationContextBox> getRegisterApplicationContextAgent() {
        return registerApplicationContextAgent;
    }
    // end ====

    private Map<String, ApplicationContextBox> applicationContextAgents = new HashMap<>();

    public Map<String, ApplicationContextBox> getApplicationContextAgents() {
        return applicationContextAgents;
    }

    public ApplicationContextBox getOrCreate(ConfigurableEnvironment environment, String packageName) {
        if (registerApplicationContextAgent.containsKey(packageName)) {
            return registerApplicationContextAgent.get(packageName);
        }
        ApplicationContextBox applicationContextBox = new ApplicationContextBox(environment, packageName);
        registerApplicationContextAgent.put(packageName, applicationContextBox);
        registerApplicationContext.put(packageName, applicationContextBox.getContext());
        return applicationContextBox;
    }

    public boolean containsKey(String key) {
        return applicationContextAgents.containsKey(key);
    }

    public void add(String packageName, ApplicationContextBox applicationContextBox) {
        applicationContextAgents.put(packageName, applicationContextBox);
    }

    public ApplicationContext getContext() {
        if (applicationContextAgents.size() > 0) {
            return getContext(applicationContextAgents.keySet().iterator().next());
        }
        return null;
    }

    public ApplicationContext getContext(String packageName) {
        return applicationContextAgents.get(packageName).getContext();
    }

    public ApplicationContextBox getContextBox() {
        if (applicationContextAgents.size() > 0) {
            return getContextBox(applicationContextAgents.keySet().iterator().next());
        }
        return null;
    }

    public ApplicationContextBox getContextBox(String packageName) {
        return applicationContextAgents.get(packageName);
    }
}