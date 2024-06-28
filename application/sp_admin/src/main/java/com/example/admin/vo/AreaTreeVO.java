package com.example.admin.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaTreeVO {
    @ApiModelProperty(value = "地区id", example = "1")
    private Integer areaId;

    @ApiModelProperty(value = "上级id", example = "0")
    private Integer pid;

    @ApiModelProperty(value = "地区首字母", example = "B")
    private String areaKey;

    @ApiModelProperty(value = "地区名称", example = "北京市")
    private String areaName;

    @ApiModelProperty(value = "级别标志  0-省 1-市 2-县区", example = "0")
    private Short areaType;

    @ApiModelProperty(value = "显示状态", example = "true")
    private Boolean isShow;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;

    @ApiModelProperty(value = "下级地区", example = "0")
    private List<AreaTreeVO> children;

    /**
     * 转换vo
     * @param trees
     * @return
     */
    public static List<AreaTreeVO> convertToVO(List<Tree<String>> trees) {
        return Objects.isNull(trees)?new ArrayList<>():CollUtil.newArrayList(
                trees.stream()
                        .map(tree -> convert(tree))
                        .toArray(AreaTreeVO[]::new)
        );
    }

    /**
     * 递归
     * @param node
     * @return
     */
    private static AreaTreeVO convert(Tree<String> node) {
        AreaTreeVO vo = new AreaTreeVO();
        vo.setAreaId(Integer.parseInt((String) node.get("areaId")));
        vo.setAreaKey((String) node.get("areaKey"));
        vo.setAreaName((String) node.get("areaName"));
        vo.setAreaType(Short.valueOf((Short) node.get("areaType")));
        vo.setIsShow((Boolean) node.get("isShow"));
        vo.setPid(Integer.parseInt((String) node.get("pid")));
        vo.setSort(Integer.parseInt((String) node.get("sort")));
        vo.setChildren((List<AreaTreeVO>) node.get("children"));
        if (!Objects.isNull(node.getChildren()) && node.getChildren().size()>0) {
            vo.setChildren(CollUtil.newArrayList(
                    node.getChildren().stream()
                            .map(child -> convert(child))
                            .toArray(AreaTreeVO[]::new)
            ));
        }
        return vo;
    }
}
