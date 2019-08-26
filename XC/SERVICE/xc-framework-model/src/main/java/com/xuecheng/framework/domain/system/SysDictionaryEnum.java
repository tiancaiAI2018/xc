package com.xuecheng.framework.domain.system;

public enum SysDictionaryEnum {
    COURSE_UNPUBLISHED("制作中","202001"),
    COURSE_PUBLISH("已发布","202002"),
    COURSE_OFFLINE("已下线","202003");
    String name;
    String value;
    private SysDictionaryEnum(String name, String value){
        this.name=name;
        this.value=value;
    }
    public String getName(){return name;}
    public String getValue(){return value;}
}
