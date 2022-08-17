package com.tjulab.orderservice.service.impl;

import com.tjulab.orderservice.entity.PayLog;
import com.tjulab.orderservice.mapper.PayLogMapper;
import com.tjulab.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-08-17
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
