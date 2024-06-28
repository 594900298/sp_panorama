package com.example.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        Integer time = (int)(System.currentTimeMillis()/1000);
        this.strictInsertFill(metaObject, "updateTime", () -> time, Integer.class);
        this.strictInsertFill(metaObject, "createTime", () -> time, Integer.class);
        this.strictInsertFill(metaObject, "deleteTime", () -> 0, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Integer time = (int)(System.currentTimeMillis()/1000);
        this.strictInsertFill(metaObject, "updateTime", () -> time, Integer.class);
        this.strictInsertFill(metaObject, "createTime", () -> time, Integer.class);
    }
}
