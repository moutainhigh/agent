package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.AnnouncementReadInfoMapper;
import com.ryx.credit.pojo.admin.agent.AnnouncementReadInfo;
import com.ryx.credit.service.agent.AnnouncementReadInfoService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;

/**
 * @program: agentManage
 * @description:
 * @author: ssx
 * @create: 2019-10-15 10:33
 **/
@Service("announcementReadInfoService")
public class AnnouncementReadInfoServiceImpl implements AnnouncementReadInfoService {

    @Autowired
    private AnnouncementReadInfoMapper announcementReadInfoMapper;

    @Autowired
    private IdService idService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ResultVO saveResdInfo(AnnouncementReadInfo announcementReadInfo) {
        announcementReadInfo.setId(idService.genIdInTran(TabId.A_ANNOUNCEMENT_READ_INFO));
        announcementReadInfoMapper.insert(announcementReadInfo);
        return ResultVO.success("成功");
    }

    @Override
    public AnnouncementReadInfo queryByRecord(AnnouncementReadInfo announcementReadInfo) {
        AnnouncementReadInfo announcementReadInfo1 = announcementReadInfoMapper.selectByRecord(announcementReadInfo);
        return announcementReadInfo1;
    }

}
