package com.example.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.api.bo.BrowsingHistoryBO;
import com.example.common.bo.PageParamBO;

public interface BrowsingHistoryService {
    IPage<BrowsingHistoryVO> getPaginate(PageParamBO pageParamBO);
    void add(BrowsingHistoryBO browsingHistoryBO);
}
