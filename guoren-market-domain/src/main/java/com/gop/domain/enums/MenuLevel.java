package com.gop.domain.enums;

/**
 * Created by wuyanjie on 2018/4/16.
 */
public enum MenuLevel {
    LEVEL0(0), LEVEL1(1), LEVEL2(2);
    private int level;

    MenuLevel(int a) {
        this.level = a;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
