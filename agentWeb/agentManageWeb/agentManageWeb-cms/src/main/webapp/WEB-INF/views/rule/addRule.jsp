<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#ruleAddForm').form({
            url : '${path }/rule/addRule',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
            },
            success : function(result) {
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        $("#productId").combobox({
            url : "/rule/productList",//返回json数据的url
            valueField : "productId",//这个id和你返回json里面的id对应
            textField : "productName" //这个text和你返回json里面的text对应

        })
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="ruleAddForm" method="post">
            <table class="grid">
                <tr>
                    <td style="width:150px">名称</td>
                    <td >
                        <input name="name" id="name" class="easyui-validatebox" data-options="required:true"/>
                    </td>
                </tr>
                 <tr>
                    <td>开启时间</td>
                    <td><input class="easyui-datetimebox" name="validStartTime" data-options="required:true"  style="width:150px">  </td>
                </tr>
                <tr>
                    <td>结束时间</td>
                    <td><input class="easyui-datetimebox" name="validEndTime" data-options="required:true"  style="width:150px">  </td>
                </tr>
                <tr>
                    <td>产品</td>
                    <td>
                        <input id="productId" name="productId" data-options="required:true">
                    </td>
                </tr>
                <tr>
                    <td>状态</td>
                    <td >
                        <select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1">有效</option>
                            <option value="0">无效</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>优先级(数越小，优先级越高)</td>
                    <td >
                        <input name="priority" id="priority" class="easyui-validatebox" data-options="required:true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>