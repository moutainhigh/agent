package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.OProductMapper;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.pojo.admin.order.OProductExample;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by RYX on 2018/7/13.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private OProductMapper productMapper;
    @Autowired
    private IdService idService;


    @Override
    public PageInfo productList(OProduct product, Page page){

        OProductExample example = new OProductExample();
        OProductExample.Criteria criteria = example.createCriteria();

        List<OProduct> oProducts = productMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oProducts);
        pageInfo.setTotal(productMapper.countByExample(example));
        return pageInfo;
    }


    @Override
    public AgentResult saveProduct(OProduct product){
        AgentResult result = new AgentResult(500, "系统异常", "");
        product.setId(idService.genId(TabId.o_product));
        Date nowDate = new Date();
        product.setcTime(nowDate);
        product.setuTime(nowDate);
        product.setStatus(Status.STATUS_1.status);
        product.setVersion(Status.STATUS_1.status);
        int insert = productMapper.insert(product);
        if(insert==1){
            return AgentResult.ok();
        }
        return result;
    }


    @Override
    public AgentResult updateProduct(OProduct product){
        AgentResult result = new AgentResult(500, "参数错误", "");
        if(product==null){
            return result;
        }
        if(StringUtils.isBlank(product.getId())){
            return result;
        }
        product.setuTime(new Date());
        int update = productMapper.updateByPrimaryKeySelective(product);
        if(update==1){
            return AgentResult.ok();
        }
        return result;
    }
}
