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
public class RuleTreeVO {
    @ApiModelProperty(value = "规则id", example = "1")
    private Integer ruleId;

    @ApiModelProperty(value = "上级id", example = "0")
    private Integer pid;

    @ApiModelProperty(value = "规则名称", example = "name")
    private String name;

    @ApiModelProperty(value = "接口url", example = "name")
    private String ruleUrl;

    @ApiModelProperty(value = "排序", example = "0")
    private Integer sort;

    @ApiModelProperty(value = "下级", example = "0")
    private List<RuleTreeVO> children;

    /**
     * 转换vo
     * @param trees
     * @return
     */
    public static List<RuleTreeVO> convertToVO(List<Tree<String>> trees) {
        return Objects.isNull(trees)?new ArrayList<>():CollUtil.newArrayList(
                trees.stream()
                        .map(tree -> convert(tree))
                        .toArray(RuleTreeVO[]::new)
        );
    }

    /**
     * 递归
     * @param node
     * @return
     */
    private static RuleTreeVO convert(Tree<String> node) {
        RuleTreeVO vo = new RuleTreeVO();
        vo.setRuleId(Integer.parseInt((String) node.get("ruleId")));
        vo.setPid(Integer.parseInt((String) node.get("pid")));
        vo.setName((String) node.get("name"));
        vo.setRuleUrl((String) node.get("ruleUrl"));
        vo.setSort(Integer.parseInt((String) node.get("sort")));
        vo.setChildren((List<RuleTreeVO>) node.get("children"));
        if (!Objects.isNull(node.getChildren()) && node.getChildren().size()>0) {
            vo.setChildren(CollUtil.newArrayList(
                    node.getChildren().stream()
                            .map(child -> convert(child))
                            .toArray(RuleTreeVO[]::new)
            ));
        }
        return vo;
    }
}
