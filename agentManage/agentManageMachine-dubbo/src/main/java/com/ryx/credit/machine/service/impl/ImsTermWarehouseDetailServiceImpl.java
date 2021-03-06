package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.IDUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.machine.dao.ImsOrganMapper;
import com.ryx.credit.machine.dao.ImsTermTransferDetailMapper;
import com.ryx.credit.machine.dao.ImsTermTransferMapper;
import com.ryx.credit.machine.dao.ImsTermWarehouseDetailMapper;
import com.ryx.credit.machine.entity.*;
import com.ryx.credit.machine.service.ImsTermActiveService;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
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
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/10/11 10:21
 * @Param
 * @return
 **/
@Service("imsTermWarehouseDetailService")
public class ImsTermWarehouseDetailServiceImpl implements ImsTermWarehouseDetailService {

    private static Logger log = LoggerFactory.getLogger(ImsTermWarehouseDetailServiceImpl.class);

    private final static String ZHYY_CREATE_PERSON = AppConfig.getProperty("zhyy_create_person");
    private final static String ZHYY_ROOT_ORG_ID = AppConfig.getProperty("zhyy_root_org_id");

    @Autowired
    private ImsTermActiveService imsTermActiveService;
    @Autowired
    private ImsTermWarehouseDetailMapper imsTermWarehouseDetailMapper;
    @Autowired
    private ImsTermTransferMapper imsTermTransferMapper;
    @Autowired
    private ImsTermTransferDetailMapper imsTermTransferDetailMapper;
    @Autowired
    private ImsOrganMapper imsOrganMapper;

    /**
     * POS入库划拨操作
     * @param snList
     * @param imsTermWarehouseDetail
     * @return
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult insertWarehouseAndTransfer(List<String> snList, ImsTermWarehouseDetail imsTermWarehouseDetail)throws MessageException {
        if(null==snList){
            throw new MessageException("sn列表异常");
        }
        if(snList.size()==0){
            throw new MessageException("sn数据有误");
        }
        //查询O码是否在pos中存在
        if (null == imsTermWarehouseDetail.getOrgId()) throw new MessageException("O码不存在,请补全O码!");
        List<ImsOrgan> imsOrgans = imsOrganMapper.selectImsListByOrgId(imsTermWarehouseDetail.getOrgId());
        if (!(imsOrgans != null && imsOrgans.size() > 0)) throw new MessageException("O码在内管平台不存在!");

        log.info("POS入库划拨请求数据:SN数量:{},参数:{}",snList.size(), JSONObject.toJSONString(imsTermWarehouseDetail));
        for (String sn : snList) {
            ImsTermActive imsTermActive = imsTermActiveService.selectByPrimaryKey(sn);
            //有记录就表示已激活
            if(null!=imsTermActive){
                throw new MessageException("SN机具已激活");
            }
            if(imsTermWarehouseDetail.getStandTime()==null || StringUtils.isBlank(imsTermWarehouseDetail.getPosType()) || imsTermWarehouseDetail.getPosSpePrice()==null ){
                throw new MessageException("缺少参数");
            }
            ImsTermWarehouseDetail updateTermWarehouseDetail = imsTermWarehouseDetailMapper.selectByPrimaryKey(sn);
            if(updateTermWarehouseDetail!=null){
                throw new MessageException("SN已存在,请检查sn");
            }
            String createTime = DateUtil.format(new Date());
            imsTermWarehouseDetail.setWdId(IDUtils.genImsTermId());
            imsTermWarehouseDetail.setPosSn(sn);
            imsTermWarehouseDetail.setUseStatus("1"); //未使用
            imsTermWarehouseDetail.setStatus("0");  //正常
            imsTermWarehouseDetail.setCreateTime(createTime);
            imsTermWarehouseDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermWarehouseDetail.setUpdateTime(createTime);
            imsTermWarehouseDetail.setPayStatus("1");  //支付状态 0 已付 1 未付
            if(StringUtils.isBlank(imsTermWarehouseDetail.getDeliveryTime())) {
                imsTermWarehouseDetail.setDeliveryTime(DateUtil.formatDay(new Date()));
            }

            int i = imsTermWarehouseDetailMapper.insert(imsTermWarehouseDetail);
            log.info("同步POS入库返回结果:{}",i);
            if(i!=1){
                throw new MessageException("SN库存插入失败");
            }
            String transferId = IDUtils.genImsTermId();
            ImsTermTransfer imsTermTransfer = new ImsTermTransfer();
            imsTermTransfer.setTransferId(transferId);
            imsTermTransfer.setStatus("0");  //0：处理完成
            imsTermTransfer.setOrgId(ZHYY_ROOT_ORG_ID);
            imsTermTransfer.setCreateTime(createTime);
            imsTermTransfer.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermTransfer.setUpdateTime(createTime);
            imsTermTransfer.setUpdatePerson(ZHYY_CREATE_PERSON);
            imsTermTransfer.setTransferType("0");   //0:划拨

            int j = imsTermTransferMapper.insert(imsTermTransfer);
            log.info("同步POS划拨返回结果:{}",j);
            if(j!=1){
                throw new MessageException("SN划拨插入失败");
            }
            ImsTermTransferDetail imsTermTransferDetail = new ImsTermTransferDetail();
            imsTermTransferDetail.setId(IDUtils.genImsTermId());
            imsTermTransferDetail.setMachineId(imsTermWarehouseDetail.getMachineId());
            imsTermTransferDetail.setPosSn(sn);
            imsTermTransferDetail.setyOrgId(ZHYY_ROOT_ORG_ID);
            imsTermTransferDetail.setnOrgId(imsTermWarehouseDetail.getOrgId());
            imsTermTransferDetail.setCreateTime(createTime);
            imsTermTransferDetail.setCreatePerson(ZHYY_CREATE_PERSON);
            imsTermTransferDetail.setOperOrgId(imsTermWarehouseDetail.getOrgId());
            imsTermTransferDetail.setTransferId(transferId);

            int k = imsTermTransferDetailMapper.insert(imsTermTransferDetail);
            log.info("同步POS划拨明细返回结果:{}",k);
            if(k!=1){
                throw new MessageException("SN划拨明细插入失败");
            }
        }
        return AgentResult.ok();
    }
}
