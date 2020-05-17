package com.data.enums;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-05-09 13:48
 */
public enum DbStateEnum {
    MYSQL("jdbc:mysql","mysql数据"),
    ORACLE("jdbc:oracle","oracle数据"),
    ;
    private String state;
    private String descript;
    DbStateEnum(String state,String descript){
        this.state=state;
        this.descript=descript;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
