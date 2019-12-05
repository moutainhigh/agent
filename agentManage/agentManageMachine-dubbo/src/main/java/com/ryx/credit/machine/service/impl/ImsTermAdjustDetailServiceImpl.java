package com.ryx.credit.machine.service.impl;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.IDUtils;
import com.ryx.credit.machine.dao.ImsTermAdjustDetailMapper;
import com.ryx.credit.machine.dao.ImsTermAdjustMapper;
import com.ryx.credit.machine.dao.ImsTermWarehouseDetailMapper;
import com.ryx.credit.machine.entity.ImsTermActive;
import com.ryx.credit.machine.entity.ImsTermAdjust;
import com.ryx.credit.machine.entity.ImsTermAdjustDetail;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;
import com.ryx.credit.machine.service.ImsTermActiveService;
import com.ryx.credit.machine.service.ImsTermAdjustDetailService;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.service.order.IOrderReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/***
 * POS机具调整
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/10/11 10:19
 * @Param
 * @return
 **/
@Service("imsTermAdjustDetailService")
public class ImsTermAdjustDetailServiceImpl implements ImsTermAdjustDetailService {

    private static Logger log = LoggerFactory.getLogger(ImsTermAdjustDetailServiceImpl.class);

    private final static String ZHYY_CREATE_PERSON = AppConfig.getProperty("zhyy_create_person");

    @Autowired
    private ImsTermActiveService imsTermActiveService;
    @Autowired
    private ImsTermAdjustDetailMapper imsTermAdjustDetailMapper;
    @Autowired
    private ImsTermAdjustMapper imsTermAdjustMapper;
    @Autowired
    private ImsTermWarehouseDetailMapper imsTermWarehouseDetailMapper;
    @Autowired
    private IOrderReturnService orderReturnService;


    //TODO 修正物流发送
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult insertImsTermAdjustDetail(OLogistics oLogistics, List<OLogisticsDetail> logisticsDetailList, ImsTermAdjustDetail imsTermAdjustDetail)throws MessageException {
        if(null==logisticsDetailList){
            throw new MessageException("sn列表异常");
        }
        if(logisticsDetailList.size()==0){
            throw new MessageException("sn数据有误");
        }

        //调整申请
        String createTime = DateUtil.format(new Date());
        String adjustId = IDUtils.genImsTermId();
        ImsTermAdjust imsTermAdjust = new ImsTermAdjust();
        imsTermAdjust.setId(adjustId);
        imsTermAdjust.setCreateTime(createTime);
        imsTermAdjust.setCreatePerson(ZHYY_CREATE_PERSON);
        int i = imsTermAdjustMapper.insert(imsTermAdjust);
        log.info("同步POS调整返回结果:{}",i);
        if(i!=1){
            throw new MessageException("SN调整失败");
        }

        log.info("同步POS调整数据开始:snList:{},请求参数:{}",logisticsDetailList.size(),imsTermAdjustDetail);
        //调整明细
        for (OLogisticsDetail oLogisticsDetail : logisticsDetailList) {
            ImsTermActive imsTermActive = imsTermActiveService.selectByPrimaryKey(oLogisticsDetail.getSnNum());
            //有记录就表示已激活
            if(null!=imsTermActive){
                throw new MessageException("Sn机具已激活");
            }
            ImsTermWarehouseDetail imsTermWarehouseDetail = imsTermWarehouseDetailMapper.selectByPrimaryKey(oLogisticsDetail.getSnNum());
            //每个sn明细
            imsTermAdjustDetail.setId(IDUtils.genImsTermId());
            imsTermAdjustDetail.setPosSn(oLogisticsDetail.getSnNum());
            imsTermAdjustDetail.setAdId(adjustId);
            imsTermAdjustDetail.setyOrgId(imsTermWarehouseDetail.getOrgId());
            imsTermAdjustDetail.setCreateTime(createTime);
            imsTermAdjustDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            int j = imsTermAdjustDetailMapper.insert(imsTermAdjustDetail);
            log.info("同步POS调整返回结果:{}",j);
            if(j!=1){
                throw new MessageException("SN调整失败");
            }

            //调整sn
            imsTermWarehouseDetail.setOrgId(imsTermAdjustDetail.getnOrgId());
            //调整机具属性
            imsTermWarehouseDetail.setMachineId(oLogisticsDetail.getBusProCode());
            imsTermWarehouseDetail.setPosSpePrice(oLogisticsDetail.getPosSpePrice());
            imsTermWarehouseDetail.setPosType(oLogisticsDetail.getPosType());
            imsTermWarehouseDetail.setStandTime(oLogisticsDetail.getStandTime());
            imsTermWarehouseDetail.setUseStatus("1"); //未使用
            imsTermWarehouseDetail.setStatus("0");  //正常
            imsTermWarehouseDetail.setCreateTime(createTime);
            imsTermWarehouseDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermWarehouseDetail.setUpdateTime(createTime);
            imsTermWarehouseDetail.setPayStatus("1");  //支付状态 0 已付 1 未付
            if(oLogistics.getSendDate()!=null) {
                imsTermWarehouseDetail.setDeliveryTime(DateUtil.formatDay(oLogistics.getSendDate()));
            }else{
                imsTermWarehouseDetail.setDeliveryTime(DateUtil.formatDay(new Date()));
            }
            int k = imsTermWarehouseDetailMapper.updateByPrimaryKeySelective(imsTermWarehouseDetail);
            log.info("同步POS调整更新库存明细返回结果:{}",k);
            if(k!=1){
                throw new MessageException("SN调整失败");
            }
        }
        return AgentResult.ok();
    }
}
