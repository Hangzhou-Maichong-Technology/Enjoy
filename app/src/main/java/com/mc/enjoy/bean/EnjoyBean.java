package com.mc.enjoy.bean;

import com.hzmct.enjoy.constant.EnjoyEnum;

/**
 * @author Woong on 3/3/21
 * @website http://woong.cn
 */
public class EnjoyBean {
    private EnjoyEnum id;
    private String name;

    public EnjoyBean(EnjoyEnum id, String name) {
        this.id = id;
        this.name = name;
    }

    public EnjoyEnum getId() {
        return id;
    }

    public void setId(EnjoyEnum id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
