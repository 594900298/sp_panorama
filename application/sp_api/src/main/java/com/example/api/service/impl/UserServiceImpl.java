package com.example.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.api.service.UserService;
import com.example.common.enums.OrderStatus;
import com.example.common.mapper.UserMapper;
import com.example.common.po.GoodsCollect;
import com.example.common.po.Order;
import com.example.common.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Service("apiUserServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsCollectService goodsCollectService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取统计数量
     *
     * @return
     */
    @Override
    public UserCountVO getCount() {
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("id");
        UserCountVO userCountVO = new UserCountVO();
        // 待支付
        userCountVO.setWaitPayCount((int) orderService.count(new QueryWrapper<Order>().lambda().eq(Order::getUserId, userId).eq(Order::getOrderStatus, OrderStatus.WAIT_PAY.getKey())));
        // 待发货
        userCountVO.setWaitSendCount((int) orderService.count(new QueryWrapper<Order>().lambda().eq(Order::getUserId, userId).eq(Order::getOrderStatus, OrderStatus.WAIT_SEND.getKey())));
        // 待收货
        userCountVO.setWaitTakeCount((int) orderService.count(new QueryWrapper<Order>().lambda().eq(Order::getUserId, userId).eq(Order::getOrderStatus, OrderStatus.WAIT_TAKE.getKey())));
        // 售后
        userCountVO.setAfterSales((int) orderService.count(new QueryWrapper<Order>().lambda().eq(Order::getUserId, userId).eq(Order::getOrderStatus, OrderStatus.AFTER_SALES.getKey())));
        // 收藏商品数量
        userCountVO.setCollectCount((int) goodsCollectService.count(new QueryWrapper<GoodsCollect>().lambda().eq(GoodsCollect::getUserId, userId).eq(GoodsCollect::getDeleteTime, 0)));
        // 浏览记录数量
        userCountVO.setBrowsingHistoryCount((int) mongoTemplate.count(new Query(), BrowsingHistory.class));
        return userCountVO;
    }
}




