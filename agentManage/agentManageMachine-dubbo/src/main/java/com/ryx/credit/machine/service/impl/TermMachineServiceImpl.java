package com.ryx.credit.machine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述： 业务系统极具终端操作接口
 */
@Service("termMachineService")
public class TermMachineServiceImpl  implements TermMachineService {


    private Logger logger = LoggerFactory.getLogger(TermMachineServiceImpl.class);

    @Resource(name = "posTermMachineServiceImpl")
    private TermMachineService posTermMachineServiceImpl;

    @Resource(name = "mposTermMachineServiceImpl")
    private TermMachineService mposTermMachineServiceImpl;
    @Resource(name = "sPosTermMachineServiceImpl")
    private TermMachineService sPosTermMachineServiceImpl;





    @Override
    public List<TermMachineVo> queryTermMachine(PlatformType platformType,Map<String,String> par) throws Exception{
        if(PlatformType.whetherPOS(platformType.code)){
            return posTermMachineServiceImpl.queryTermMachine(platformType,par);
        }else  if(PlatformType.MPOS.code.equals(platformType.code)){
            return mposTermMachineServiceImpl.queryTermMachine(platformType,par);
        }else  if(PlatformType.SSPOS.code.equals(platformType.code)){
            return sPosTermMachineServiceImpl.queryTermMachine(platformType,par);
        }else {
            List<TermMachineVo> list = new ArrayList<>();
            TermMachineVo vo = new TermMachineVo();
            vo.setId("");
            vo.setMechineName("无配置");
            list.add(vo);
            return list;
        }
    }

    @Override
    public List<MposTermBatchVo> queryMposTermBatch(PlatformType platformType) throws Exception{
        if(PlatformType.whetherPOS(platformType.code)){
            return new ArrayList<>();
        }else  if(PlatformType.MPOS.code.equals(platformType.code)){
            return mposTermMachineServiceImpl.queryMposTermBatch(platformType);
        }else  if(PlatformType.SSPOS.code.equals(platformType.code)){
            return sPosTermMachineServiceImpl.queryMposTermBatch(platformType);
        }
        return new ArrayList<>();
    }

    @Override
    public List<MposTermTypeVo> queryMposTermType(PlatformType platformType) throws Exception{

        if(PlatformType.whetherPOS(platformType.code)){
            return new ArrayList<>();
        }else if(PlatformType.MPOS.code.equals(platformType.code)){
            return mposTermMachineServiceImpl.queryMposTermType(platformType);
        }else if(PlatformType.SSPOS.code.equals(platformType.code)){
            return sPosTermMachineServiceImpl.queryMposTermType(platformType);
        }
        return new ArrayList<>();
    }

    /**
     * 机具的下发
     * @param lowerHairMachineVo
     * @return
     */
    @Override
    public AgentResult lowerHairMachine(LowerHairMachineVo lowerHairMachineVo) throws Exception{
        if(PlatformType.whetherPOS(lowerHairMachineVo.getPlatformType())){
            return AgentResult.ok();
        }else if(PlatformType.MPOS.code.equals(lowerHairMachineVo.getPlatformType())){
            return mposTermMachineServiceImpl.lowerHairMachine(lowerHairMachineVo);
        }else if(PlatformType.SSPOS.code.equals(lowerHairMachineVo.getPlatformType())){
            return sPosTermMachineServiceImpl.lowerHairMachine(lowerHairMachineVo);
        }
        return AgentResult.fail("未实现的业务");
    }

    /**
     * 机具的调整，，退货是使用
     * @param adjustmentMachineVo
     * @return
     */
    @Override
    public AgentResult adjustmentMachine(AdjustmentMachineVo adjustmentMachineVo) throws Exception{
        if(PlatformType.whetherPOS(adjustmentMachineVo.getPlatformType())){
            return AgentResult.ok();
        }else if(PlatformType.MPOS.code.equals(adjustmentMachineVo.getPlatformType())){
            return mposTermMachineServiceImpl.adjustmentMachine(adjustmentMachineVo);
        }else if(PlatformType.SSPOS.code.equals(adjustmentMachineVo.getPlatformType())){
            return sPosTermMachineServiceImpl.adjustmentMachine(adjustmentMachineVo);
        }
        return AgentResult.fail("未实现的业务");
    }

    /**
     * 机具活动的变更
     * @param changeActMachine
     * @return
     */
    @Override
    public AgentResult changeActMachine(ChangeActMachineVo changeActMachine) throws Exception{

        if(PlatformType.whetherPOS(changeActMachine.getPlatformType())){
            AgentResult res =   posTermMachineServiceImpl.changeActMachine(changeActMachine);
            logger.info("pos机具的互动变更接口:{}",res.getMsg());
            return res;
        }else if(PlatformType.MPOS.code.equals(changeActMachine.getPlatformType())){
            AgentResult res =   mposTermMachineServiceImpl.changeActMachine(changeActMachine);
            logger.info("mpos机具的互动变更接口:{}",res.getMsg());
            return res;
        }else if(PlatformType.SSPOS.code.equals(changeActMachine.getPlatformType())){
            AgentResult res =   sPosTermMachineServiceImpl.changeActMachine(changeActMachine);
            logger.info("mpos机具的互动变更接口:{}",res.getMsg());
            return res;
        }
        return AgentResult.fail("未知业务平台");
    }

    @Override
    public JSONObject request(Map data, String url) throws Exception {
        return null;
    }

    @Override
    public AgentResult querySnMsg(PlatformType platformType,String snBegin,String snEnd)throws Exception{
        if(PlatformType.whetherPOS(platformType.name())){
            return posTermMachineServiceImpl.querySnMsg(platformType,snBegin,snEnd);
        }else  if(PlatformType.MPOS.code.equals(platformType.name())){
            return mposTermMachineServiceImpl.querySnMsg(platformType,snBegin,snEnd);
        }
        return AgentResult.fail("未知业务平台");
    }

    @Override
    public AgentResult queryTerminalTransfer(List<TerminalTransferDetail> terminalTransferDetailLists, String operation) throws Exception {
       String type = terminalTransferDetailLists.get(0).getPlatformType().toString();
      if("1".equals(type)){
            return   posTermMachineServiceImpl.queryTerminalTransfer(terminalTransferDetailLists,operation);
        }else if("2".equals(type)){
            return   mposTermMachineServiceImpl.queryTerminalTransfer(terminalTransferDetailLists,operation);
        }else if("3".equals(type)){
            return   AgentResult.fail("未联动");
        }

        return AgentResult.fail("未知业务平台");

    }

    @Override
    public AgentResult queryTerminalTransferResult(String serialNumber,String type) throws Exception {
        AgentResult agentResult=null;
        if("1".equals(type)){
         agentResult =  posTermMachineServiceImpl.queryTerminalTransferResult(serialNumber,type);
        }else if("2".equals(type)){

        }else if("3".equals(type)){

        }
        return agentResult;
    }
}
