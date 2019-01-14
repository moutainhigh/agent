package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.TaxDeductionDetailMapper;
import com.ryx.credit.profit.pojo.TaxDeductionDetail;
import com.ryx.credit.profit.pojo.TaxDeductionDetailExample;
import com.ryx.credit.profit.service.ITaxDeductionDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("taxDeductionDetailService")
public class TaxDeductionDetailServiceImpl implements ITaxDeductionDetailService {
    Logger logger = LoggerFactory.getLogger(TaxDeductionDetailServiceImpl.class);

    @Autowired
    private TaxDeductionDetailMapper taxDeductionDetailMapper;

    @Override
    public PageInfo posDeductTaxList(TaxDeductionDetail taxDeductionDetail, Page page,String dateStart,String dateEnd) {
        //获取直签数据列表
        TaxDeductionDetailExample example=new TaxDeductionDetailExample();
        example.setPage(page);
        TaxDeductionDetailExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotBlank(taxDeductionDetail.getAgentName())){
            criteria.andAgentNameEqualTo(taxDeductionDetail.getAgentName());
        }
        if(StringUtils.isNotBlank(taxDeductionDetail.getAgentId())){
            criteria.andAgentIdEqualTo(taxDeductionDetail.getAgentId());
        }
        //修改:根据区间日期进行查询
        if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthBetween(dateStart,dateEnd);
        }else if(StringUtils.isNotBlank(dateStart)){
            criteria.andProfitMonthEqualTo(dateStart);
        }

        if(StringUtils.isNotBlank(taxDeductionDetail.getBusPlatform())){
            criteria.andBusPlatformNotEqualTo(taxDeductionDetail.getBusPlatform());
        }
        List<TaxDeductionDetail> taxDeductionDetails = taxDeductionDetailMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(taxDeductionDetails);
        pageInfo.setTotal((int)taxDeductionDetailMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public PageInfo posDirectlyDeductTaxList(TaxDeductionDetail taxDeductionDetail, Page page,String dateStart,String dateEnd) {
        TaxDeductionDetailExample example=new TaxDeductionDetailExample();
        example.setPage(page);
        TaxDeductionDetailExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotBlank(taxDeductionDetail.getAgentName())){
            criteria.andAgentNameEqualTo(taxDeductionDetail.getAgentName());
        }
        if(StringUtils.isNotBlank(taxDeductionDetail.getAgentId())){
            criteria.andAgentIdEqualTo(taxDeductionDetail.getAgentId());
        }
        if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthBetween(dateStart,dateEnd);
        }else if(StringUtils.isNotBlank(dateStart)){
            criteria.andProfitMonthEqualTo(dateStart);
        }
       /* if(StringUtils.isNotBlank(taxDeductionDetail.getProfitMonth())){
            criteria.andProfitMonthEqualTo(taxDeductionDetail.getProfitMonth());
        }*/
        if(StringUtils.isNotBlank(taxDeductionDetail.getBusPlatform())){
            criteria.andBusPlatformEqualTo(taxDeductionDetail.getBusPlatform());
        }
        List<TaxDeductionDetail> taxDeductionDetails = taxDeductionDetailMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(taxDeductionDetails);
        pageInfo.setTotal((int)taxDeductionDetailMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public int updateAdjust(TaxDeductionDetail taxDeductionDetail) {
        return taxDeductionDetailMapper.updateByPrimaryKeySelective(taxDeductionDetail);
    }

    @Override
    public TaxDeductionDetail selectById(String id) {
        return taxDeductionDetailMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<TaxDeductionDetail> query(TaxDeductionDetail taxDeductionDetail){
        TaxDeductionDetailExample example=rewardEqualsTo(taxDeductionDetail);
        return taxDeductionDetailMapper.selectByExample(example);
    }

    /**
     * 显示下级查询
     * @param taxDeductionDetail
     * @param page
     * @return
     */
    @Override
    public PageInfo queryAndSubordinate(TaxDeductionDetail taxDeductionDetail,Page page) {
        List<TaxDeductionDetail> taxDeductionDetails = taxDeductionDetailMapper.queryAndSubordinate(taxDeductionDetail,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(taxDeductionDetails);
        pageInfo.setTotal((int)taxDeductionDetailMapper.queryCountAndSubordinate(taxDeductionDetail));
        return pageInfo;
    }

    @Override
    public Map<String, Object> profitCount(Map<String, Object> param, boolean isQuerySubordinate) {
        if (isQuerySubordinate){
            return taxDeductionDetailMapper.profitCountWithSubordinate(param);
        }else{
            TaxDeductionDetailExample example=new TaxDeductionDetailExample();
            TaxDeductionDetailExample.Criteria criteria=example.createCriteria();
            if(StringUtils.isNotBlank(param.get("agentName").toString())){
                criteria.andAgentNameEqualTo(param.get("agentName").toString());
            }
            if(StringUtils.isNotBlank(param.get("agentId").toString())){
                criteria.andAgentIdEqualTo(param.get("agentId").toString());
            }
            String dateStart=param.get("DATESTART").toString();
            String dateEnd=param.get("DATEEND").toString();
            if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
                criteria.andProfitMonthBetween(dateStart,dateEnd);
            }else if(StringUtils.isNotBlank(dateStart)){
                criteria.andProfitMonthEqualTo(dateStart);
            }
            if(StringUtils.isNotBlank(param.get("busPlatform").toString())){
                if((boolean)param.get("directly")){
                    criteria.andBusPlatformEqualTo(param.get("busPlatform").toString());
                }else{
                    criteria.andBusPlatformNotEqualTo(param.get("busPlatform").toString());
                }
            }
            return taxDeductionDetailMapper.profitCount(example);
        }
    }

    /**
     * 封装查询条件
     * @param taxDeductionDetail
     * @return
     */
    private TaxDeductionDetailExample rewardEqualsTo(TaxDeductionDetail taxDeductionDetail){
        TaxDeductionDetailExample example = new TaxDeductionDetailExample();
        if(taxDeductionDetail == null ){
            return example;
        }
        TaxDeductionDetailExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(taxDeductionDetail.getAgentName())){
            criteria.andAgentNameEqualTo(taxDeductionDetail.getAgentName());
        }
        if(StringUtils.isNotBlank(taxDeductionDetail.getAgentId())){
            criteria.andAgentIdEqualTo(taxDeductionDetail.getAgentId());
        }
        if(StringUtils.isNotBlank(taxDeductionDetail.getProfitMonth())){
            criteria.andProfitMonthEqualTo(taxDeductionDetail.getProfitMonth());
        }
        return example;
    }



}