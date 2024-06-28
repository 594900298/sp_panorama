package com.example.common.Interface;

/**
 * 需要使用属性结构需要实现该接口，并让这些方法获取到值
 */
public interface TreeNode {
    public String getId();

    public String getParentId();

    public String getWeight();

    public String getName();
}
