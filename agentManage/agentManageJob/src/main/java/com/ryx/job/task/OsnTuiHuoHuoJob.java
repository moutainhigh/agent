package com.ryx.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ryx.credit.service.order.OsnOperateReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/4/23
 * 描述：
 */
@Service("osnTuiHuoJob")
public class OsnTuiHuoHuoJob implements DataflowJob<String> {

    private static Logger logger = LoggerFactory.getLogger(OsnTuiHuoHuoJob.class);

    @Autowired
    private OsnOperateReturnService osnOperateReturnService;
    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        try {
            logger.debug("退货发货处理物流开始获取数据");
            return osnOperateReturnService.fetchFhData(0,shardingContext.getShardingItem());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("退回发货处理物流开始处理失败",e);
        }
        return new ArrayList<>();
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        logger.info("退货发货处理物流开始处理数据");
        if(osnOperateReturnService.processData(list)){
            logger.info("退货发货处理物流开始处理成功");
        }else{
            logger.info("退货发货处理物流开始处理失败");
        }
    }
}
