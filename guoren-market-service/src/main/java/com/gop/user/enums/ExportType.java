package com.gop.user.enums;

import com.google.common.base.Optional;

/**
 * Created by wuyanjie on 2018/4/20.
 */
public enum ExportType {
    USR_ACCOUNT_STATUS(1,"用户资产状态"),
    PLATFORM_TOTAL_ACCOUNT(1,"用户资产状态");

    private int code;
    private String name;

    ExportType(int code,String name) {
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Optional<ExportType> getValue(int code) {
        for (ExportType type : ExportType.values()) {
            if (type.code == code) {
                return Optional.of(type);
            }
        }
        return Optional.absent();
    }
}
