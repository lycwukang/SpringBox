package org.springbox.core;

public class BeanContextInfo {

    private String name;
    private Object obj;
    private Class<?> clazz;

    public BeanContextInfo(String name, Object obj, Class<?> clazz) {
        this.name = name;
        this.obj = obj;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}