package com.moko.support.task;

import com.moko.support.entity.OrderType;

/**
 * @Date 2018/1/20
 * @Author wenzheng.liu
 * @Description
 * @ClassPath com.moko.support.task.SoftwareVersionTask
 */
public class SoftwareVersionTask extends OrderTask {

    public byte[] data;

    public SoftwareVersionTask(int responseType) {
        super(OrderType.softwareVersion, responseType);
    }

    @Override
    public byte[] assemble() {
        return data;
    }
}
