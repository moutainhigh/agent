<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <%@ include file="/commons/queryAgentBase_model.jsp" %>
        <%@ include file="/commons/queryAttachment_model.jsp" %>
        <%@ include file="/commons/taskAgentcapital_model.jsp" %>
        <%@ include file="/commons/queryAgentContractTable_model.jsp" %>
        <%@ include file="/commons/queryAgentColinfoTable_model.jsp" %>
        <%@ include file="/commons/queryAgentBusi_model.jsp" %>
        <%@ include file="/commons/approval_edit_debit.jsp" %>
        <%--业务审批--%>
        <%--<%@ include file="/commons/approval_business.jsp" %>--%>
        <%--市场部审批顶级机构--%>
        <%@ include file="/commons/approval_market_toporg.jsp" %>
        <%--财务审批--%>
        <%@ include file="/commons/approval_account.jsp" %>
        <%--审批意见--%>
        <%@ include file="/commons/approval_opinion_netIn.jsp" %>
        <c:if test="${!empty mistakeList}">
        <div class="easyui-panel" title="温馨提示"  data-options="iconCls:'fi-results'">
            <table class="grid">
                    <c:forEach items="${mistakeList}" var="mistake">
                        <tr >
                            <c:if test="${!empty mistake.msg}">
                                <td>提示信息</td>
                                <td>${mistake.msg}</td>
                            </c:if>
                            <c:if test="${!empty mistake.agentId}">
                                <td>代理商编号</td>
                                <td>${mistake.agentId}</td>
                            </c:if>
                            <c:if test="${!empty mistake.agentName}">
                                <td>代理商名称</td>
                                <td>${mistake.agentName}</td>
                            </c:if>
                            <c:if test="${!empty mistake.busPlatform}">
                                <td>平台类型</td>
                                <td>${mistake.busPlatform}</td>
                            </c:if>
                            <c:if test="${!empty mistake.busParent}">
                                <td>上级代理</td>
                                <td>${mistake.busParent}</td>
                            </c:if>
                        </tr>
                    </c:forEach>
            </table>
        </div>
        </c:if>
        <shiro:hasPermission name="/agActivity/approvalEdit">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" onclick="editAgentBusPlat()" >修改</a>
        </shiro:hasPermission>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="submitApproval()">提交</a>

    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    </shiro:hasPermission>
    <div title="审批流程图">
        <shiro:hasPermission name="/agActivity/approvalRecordImgSee">
            <img src="/agActivity/approvalImage?taskId=${taskId}" />
        </shiro:hasPermission>
    </div>
</div>
<script>

    function editAgentBusPlat(){
        addTab({
            title : '审批修改',
            border : false,
            closable : true,
            fit : true,
            href:'/agentEnter/agentByid?id=${agent.id}'+"&editWatch="+'999'
        });
    }

    function submitApproval() {
        var addAgentAccountTable = (typeof get_addAgentAccountTable_FormDataItem=== "function")?get_addAgentAccountTable_FormDataItem():[];
        var subApprovalTable = (typeof get_netIn_subApproval_FormDataItem=== "function")?get_netIn_subApproval_FormDataItem():[];
        var payCompanyTable = (typeof get_payCompanyTable_FormDataItem=== "function")?get_payCompanyTable_FormDataItem():[];
        var busEditTable = (typeof get_busEditTable_FormDataItem=== "function")?get_busEditTable_FormDataItem():[];
        var agentCapital = (typeof get_agentCapital=== "function")?get_agentCapital():[];
        var busEditDebitForm = (typeof get_busEditTable_Form=== "function")?get_busEditTable_Form():[];
        var terminalsLowerForm = (typeof get_terminalsLower_Form=== "function")?get_terminalsLower_Form():[];
        var organNumTableForm = (typeof get_organNumTable_Form === "function")?get_organNumTable_Form():[];
        var marketToporgTableIdForm = (typeof get_market_toporg_TableId_Form === "function")?get_market_toporg_TableId_Form():[];

        var busInfoVoList = [];
        if(payCompanyTable!=''){
            busInfoVoList = payCompanyTable;
        }else if(busEditTable!=''){
            busInfoVoList = busEditTable;
        }

        var subFlag = false;
        if(payCompanyTable!=''){
            $.each( payCompanyTable, function(index, content) {
                if(content.cloPayCompany==''){
                    info("请分配打款公司！");
                    return false;
                }
                subFlag = true;
            });
        }else{
            subFlag = true;
        }


        if(subFlag){
            parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/agActivity/taskApproval",
                        dataType:'json',
                        contentType:'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            agentColinfoRelList:addAgentAccountTable,
                            busInfoVoList:busInfoVoList,
                            approvalOpinion:subApprovalTable.approvalOpinion,
                            approvalResult:subApprovalTable.approvalResult,
                            dept:subApprovalTable.dept,
                            taskId:subApprovalTable.taskId,
                            capitalVoList:agentCapital,
                            editDebitList:busEditDebitForm,
                            terminalsLowerList:terminalsLowerForm,
                            orgTypeList:organNumTableForm,
                            marketToporgTableIdForm:marketToporgTableIdForm
                        }),
                        beforeSend:function(){
                          progressLoad();
                        },
                        success: function(msg){
                            info(msg.resInfo);
                            if(msg.resCode=='1'){
                                $('#index_tabs').tabs('close',"处理任务");
                                activityDataGrid.datagrid('reload');
                            }
                        },
                        complete:function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
        }
    }

    function refreshTabView() {
        refreshTab();
    }
</script>