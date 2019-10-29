package com.ryx.internet.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.pojo.OInternetCardImport;
import com.ryx.internet.pojo.OInternetCardPostpone;

import java.util.List;

/**
 * Created by RYX on 2018/12/4.
 */
public interface InternetCardService {

    PageInfo internetCardList(OInternetCard internetCard, Page page,String agentId,Long userId);

    PageInfo internetCardImportList(OInternetCardImport internetCardImport, Page page);

    void importInternetCard(String fileUrl, String importType, String userId,String batchNo)throws Exception;

    void disposeSn(List<String> snList,OInternetCard internetCard,OInternetCardImport oInternetCardImport)throws MessageException;

    List<OInternetCardImport>  exportErrorExcel(OInternetCardImport internetCardImport);

    void taskDisposeInternetCard();

    void taskUpdateMech();

    List<OInternetCard> queryInternetCardList(OInternetCard internetCard, Page page,String agentId,Long userId);

    Integer queryInternetCardCount(OInternetCard internetCard,String agentId,Long userId);

    void taskUpdateMechIsNull();

    List<OInternetCard> fetchDataMechIsNull();

    void processDataUpdateMechIsNull(OInternetCard internetCard);

    void orderInsertInternetCard(List<OLogisticsDetail> logisticsDetailList,String manuFacturer)throws Exception;

    List<OInternetCardImport> queryMigrationHistory();

    void migrationHistory(OInternetCardImport oInternetCardImport)throws MessageException;

    void internetCardPostpone(OInternetCardPostpone internetCardPostpone, String cUser,String importId,String batchNum)throws MessageException;

    PageInfo queryInternetCardPostponeList(OInternetCardPostpone internetCardPostpone,Page page);
}
