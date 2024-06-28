package com.example.common.utils;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.example.common.Interface.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 树形结构
 * 树形节点必须实现TreeNode接口
 * 参考：
 * https://blog.csdn.net/Wizarder1/article/details/130730284
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeUtil {
    // 存放生成的属性结构
    private List<Tree<String>> treeNodes = new ArrayList<>();
    // 原数据
    private List<TreeNode> nodeList = new ArrayList<>();
    // 配置树形结构的基本配置，不配置走TreeNodeConfig默认配置
    private TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
    // 扩展属性列表，通过反射调用
    private Map<String, String> extraAttrList;
    // 配置树形节点id
    private String idKey = "id";
    // 配置树形节点名称字段
    private String nameKey = "name";
    // 配置树形节点名称字段
    private String childrenKey = "children";
    // 配置树形节点父节点字段名称
    private String parentIdKey = "pid";
    // 配置权重字段，影响树形节点排序
    private String weightKey = "sort";
    private String parentIdValue = "0";

    /**
     * 生成构建后的树形结构
     */
    public List<Tree<String>> build() {
        this.treeNodeConfig.setWeightKey(this.weightKey);
        this.treeNodeConfig.setIdKey(this.idKey);
        this.treeNodeConfig.setNameKey(this.nameKey);
        this.treeNodeConfig.setParentIdKey(this.parentIdKey);
        this.treeNodeConfig.setChildrenKey(this.childrenKey);
        //转换成树形结构
        return this.treeNodes = cn.hutool.core.lang.tree.TreeUtil.build(this.nodeList, this.parentIdValue, this.treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getWeight());
                    tree.setName(treeNode.getName());
                    //通过反射添加扩展树形到树形结构
                    for (String key : this.extraAttrList.keySet()) {
                        try {
                            try {
                                //利用反射获取方法并且调用，通过传入的对象调用
                                tree.putExtra(key, treeNode.getClass().getMethod(this.extraAttrList.get(key)).invoke(treeNode));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}

