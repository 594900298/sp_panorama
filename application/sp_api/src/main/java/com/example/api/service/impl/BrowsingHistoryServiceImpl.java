package com.example.api.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.api.bo.BrowsingHistoryBO;
import com.example.api.service.BrowsingHistoryService;
import com.example.common.bo.PageParamBO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("apiBrowsingHistoryServiceImpl")
public class BrowsingHistoryServiceImpl implements BrowsingHistoryService {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 分页
     * @param pageParamBO
     * @return
     */
    @Override
    public IPage<BrowsingHistoryVO> getPaginate(PageParamBO pageParamBO) {
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
        IPage page = new Page(pageParamBO.getPageIndex(),pageParamBO.getPageSize());
        // 设置总数
        page.setTotal(mongoTemplate.count(new Query(), BrowsingHistory.class));
        // 查询分页数据
        page.setRecords(mongoTemplate.find(
                new Query(Criteria.where("userId").is(userId)).with(Sort.by(Sort.Order.desc("updateTime"))
                )
                        .skip((pageParamBO.getPageIndex()-1) * pageParamBO.getPageSize())
                        .limit(pageParamBO.getPageSize()),BrowsingHistory.class).stream().map(po->{
            BrowsingHistoryVO vo = new BrowsingHistoryVO();
            BeanUtils.copyProperties(po,vo);
            return vo;
        }).collect(Collectors.toList()));
        return page;
    }

    /**
     * 添加记录
     * @param bo
     */
    @Override
    public void add(BrowsingHistoryBO bo) {
        // 查询是否存在数据
        BrowsingHistory browsingHistory = mongoTemplate.findOne(
                new Query(
                        Criteria.where("userId").is(bo.getUserId()).and("goodsId").is(bo.getGoodsId())
                ),BrowsingHistory.class
        );
        if(Objects.isNull(browsingHistory)){
            BrowsingHistory browsingHistoryPO = new BrowsingHistory();
            browsingHistoryPO.setUserId(bo.getUserId());
            browsingHistoryPO.setGoodsId(bo.getGoodsId());
            browsingHistoryPO.setGoodsName(bo.getGoodsName());
            browsingHistoryPO.setGoodsCover(bo.getGoodsCover());
            browsingHistoryPO.setPrice(bo.getPrice());
            browsingHistoryPO.setUpdateTime((int) (System.currentTimeMillis()/1000));
            mongoTemplate.save(browsingHistoryPO);
        }else{
            mongoTemplate.updateFirst(
                    new Query(
                            Criteria.where("userId").is(bo.getUserId()).and("goodsId").is(bo.getGoodsId())
                    ),
                    new Update()
                            .set("goodsName",bo.getGoodsName())
                            .set("goodsCover",bo.getGoodsCover())
                            .set("price",bo.getPrice())
                            .set("updateTime",(int) (System.currentTimeMillis()/1000))
                    ,BrowsingHistory.class
            );
        }
    }
}
