<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var addFileDom ;
    var fileName;
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    $(function() {
        $('#adjustAMTForm').form({
            url : '${path }/profit/invoiceDetail/setAdjustAMT',
            datatype:"json",
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {

                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.messager.alert('调整', result.obj, 'ok');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });

</script>

<!--调整页面-->
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false，title:'欠票调整'" style="overflow: hidden;padding: 3px;" >
        <form id="adjustAMTForm" method="post">
            <table class="grid">
                <tr>
                    <input type="text" id="id" name="id" value="${id}" hidden>
                </tr>
                <tr>
                    <td>代理商名称：</td>
                    <td>
                        <input type="text" id="agentName" name="agentName"  value="${agentName}"/>
                    </td>
                </tr>
                <tr>
                    <td>代理商唯一码：</td>
                    <td>
                        <input type="text" id="agentId" name="agentId" value="${agentId}">
                    </td>
                </tr>
                <tr>
                    <td>调整金额:</td>
                    <td>
                        <input type="text" id="adjustAmt" name="adjustAmt"/>
                    </td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td>
                        <textarea id="adjustReson" name="adjustReson"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
