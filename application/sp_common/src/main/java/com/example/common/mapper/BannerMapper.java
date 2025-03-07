package com.example.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.po.BannerPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.common.model.Banner
 */
@Mapper
@Repository
public interface BannerMapper extends BaseMapper<BannerPO> {

}




