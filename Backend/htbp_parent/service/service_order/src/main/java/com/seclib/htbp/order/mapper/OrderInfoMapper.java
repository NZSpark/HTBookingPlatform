package com.seclib.htbp.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seclib.htbp.model.order.OrderInfo;
import com.seclib.htbp.vo.order.OrderCountQueryVo;
import com.seclib.htbp.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
