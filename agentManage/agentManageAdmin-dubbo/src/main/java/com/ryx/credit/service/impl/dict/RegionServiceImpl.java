package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.RegionMapper;
import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.pojo.admin.agent.RegionExample;
import com.ryx.credit.service.dict.RegionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 地区树实现类
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 13:46
 */
@Service("regionService")
public class RegionServiceImpl implements RegionService {

    private static final Logger log = Logger.getLogger(RegionServiceImpl.class);
    private static final String REGIONS_KEY = "agent_regions_list";
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Tree> selectAllRegion(String pCode) {
        if(StringUtils.isBlank(pCode)){
            pCode = "0";
        }
        List<Region> regionsList = regionMapper.findByPcode(pCode);
        List<Tree> rootTree = new ArrayList<>();
        for (Region region : regionsList) {
            rootTree.add(regionToTree(region));
        }
        String treeJson = JsonUtil.objectToJson(rootTree);
        redisService.setNx(REGIONS_KEY+ ":" +pCode,treeJson);
        return rootTree;
    }

    private Tree regionToTree(Region region){
        Tree tree = new Tree();
        tree.setId(Long.valueOf(region.getrCode())+"");
        tree.setPid(Long.valueOf(region.getpCode())+"");
        tree.setText(region.getrName());
        tree.setState(regionMapper.findCountByPcode(region.getrCode())==0?1:0);
        tree.settType(region.gettType());
        return tree;
    }


    private List<Tree> getChild(String id, List<Tree> rootTree) {

        //子菜单
        List<Tree> childList = new ArrayList<>();
        for (Tree region : rootTree) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(String.valueOf(region.getPid()))) {
                if (String.valueOf(region.getPid()).equals(id)) {
                    childList.add(region);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Tree tree : childList) {
            //3表示最后一级
            if (!tree.gettType().equals("3")) {
                //递归
                tree.setChildren(getChild(String.valueOf(tree.getId()), rootTree));
            }
        }
        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    @Override
    public Region queryByCode(String code) {
        RegionExample example = new RegionExample();
        example.or().andRCodeEqualTo(code);
        List<Region> list = regionMapper.selectByExample(example);
        return list.size()>0?list.get(0):null;
    }


    @Override
    public String getRegionName(String code) {
        String name  = "";
        Region r =  queryByCode(code);
        if(r!=null){
            name = r.getrName();
            if(org.apache.commons.lang.StringUtils.isNotEmpty(r.getpCode()) && !"0".equals(r.getpCode())){
                name = getRegionName(r.getpCode())+"-"+name;
            }
        }
        return name;
    }
}
