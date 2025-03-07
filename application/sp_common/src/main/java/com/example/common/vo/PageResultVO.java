package com.example.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 分页数据
 */
public class PageResultVO implements Serializable {
    @ApiModelProperty(value = "总数量")
    private int total;
    @ApiModelProperty(value = "分页数量")
    private int pageSize;
    @ApiModelProperty(value = "总页数")
    private int totalPage;
    @ApiModelProperty(value = "当前页码")
    private int pageIndex;

    /**
     * 分页结果
     *
     * @param total
     * @param pageIndex
     * @param pageSize
     */
    public PageResultVO(Integer total, Integer pageIndex, Integer pageSize) {
        this.total = total;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil((double) total / pageSize);
    }

    /**
     * 分页结果
     *
     * @param iPage
     */
    public PageResultVO(IPage iPage) {
        this.total = (int) iPage.getTotal();
        this.pageIndex = (int) iPage.getCurrent();
        this.pageSize = (int) iPage.getSize();
        this.totalPage = (int) Math.ceil((double) total / pageSize);
    }

}
