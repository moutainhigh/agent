<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="打款记录"  data-options="iconCls:'fi-wrench',tools:'#paylist_tt'">
    <table style="" id="${paylist_model}paylist_src_table">
<c:forEach items="${paylist}" var="paylistItem">
        <tr>
            <td style="text-align: right;width: 220px;">打款方式:<input type="hidden" name="id" value="${paylistItem.id}" /> </td>
            <td>
                <select  name="paylist_payType" style="width:100px;" >
                    <c:forEach items="${payTypeSelect}" var="payTypes">
                        <option value="${payTypes.key}" <c:if test="${paylistItem.payType==payTypes.key}">selected</c:if>>${payTypes.value}</option>
                    </c:forEach>
                </select>
            </td>
            <td style="text-align: right;width: 220px;">打款金额:</td>
            <td><input class="easyui-numberbox" name="paylist_amount" value="${paylistItem.amount}" data-options="min:0,precision:2" /></td>
            <c:if test="${paylistItem.payType=='YHHK'}">
                <td style="text-align: right;width: 200px;"  class="skgs">收款公司:</td>
                <td class="skgs">
                    <select  name="paylist_collectCompany" style="width:100px;"   >
                        <c:forEach var="recCompListItem" items="${recCompList}" >
                        <option value="${recCompListItem.id}" <c:if test="${recCompListItem.id==paylistItem.collectCompany}">selected="selected"</c:if> >${recCompListItem.comName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td style="text-align: right;width: 150px;" class="dkr">打款人:</td>
                <td class="dkr"><input class="easyui-textbox" name="paylist_payUser" value="${paylistItem.payUser}" /></td>
                <td style="text-align: right;width: 200px;" class="dksj">打款时间:</td>
                <td class="dksj"><input  class="easyui-datebox dyTime" name="paylist_payTime" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${paylistItem.payTime}" />"/></td>
            </c:if>
            <td style="text-align: right;width: 100px;">
                <a href="javascript:;" class="easyui-linkbutton" style="width: 60px;"  onclick="paylist_tt_delete_row(this)">删除</a>
            </td>
        </tr>
</c:forEach>
    </table>
</div>
<div id="paylist_tt">
    <a href="javascript:void(0)" class="fi-plus" style="margin-right: 50px;" onclick="paylist_tt_add_row('#${paylist_model}paylist_src_table','#${paylist_model}paylist_tt_row_templet')" title="添加付款记录"></a>
</div>
<table id="${paylist_model}paylist_tt_row_templet" style="display: none;">
    <tr>
        <td style="text-align: right;width: 220px;">打款方式:</td>
        <td>
            <select   name="paylist_payType" style="width:100px;" >
                <c:forEach items="${payTypeSelect}" var="payTypes">
                    <option value="${payTypes.key}">${payTypes.value}</option>
                </c:forEach>
            </select>
        </td>
        <td style="text-align: right;width: 220px;">打款金额:</td>
        <td><input input-class="easyui-numberbox" name="paylist_amount" data-options="min:0,precision:2" /></td>
        <td style="text-align: right;width: 150px;" class="skgs">收款公司:</td>
        <td class="skgs">
            <select  name="paylist_collectCompany" style="width:100px;"   >
                <c:forEach var="recCompListItem" items="${recCompList}" >
                    <option value="${recCompListItem.id}">${recCompListItem.comName}</option>
                </c:forEach>
            </select>
        </td>
        <td style="text-align: right;width: 150px;" class="dkr">打款人:</td>
        <td class="dkr"><input input-class="easyui-textbox"   name="paylist_payUser"  /></td>
        <td style="text-align: right;width: 150px;" class="dksj">打款时间:</td>
        <td class="dksj"><input  input-class="easyui-datebox dyTime"  name="paylist_payTime"/></td>
        <td style="text-align: right;width: 100px;">
            <a href="javascript:;" class="easyui-linkbutton" style="width: 60px;"  onclick="paylist_tt_delete_row(this)">删除</a>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(function () {
        payListChange("#${paylist_model}paylist_src_table");
        $(".dyTime").datebox({
            onSelect:function(beginDate){
                var date = new Date();
                if(beginDate>date){
                    info("时间不能大于当前时间");
                    $(this).combo('setText','');
                }
            }
        });
    });

    //删除打款记录行
    function paylist_tt_delete_row(t){
        //删除行元素
        $(t).parent().parent().remove();
    }

    function paylist_tt_add_row(target,templet){
        //删除行元素
        $(target).append($(templet).html());
        var inputs = $(target+" tr:last").find("input");
        for(var i=0;i<inputs.length;i++){
            if($(inputs[i]).attr("input-class") && $(inputs[i]).attr("input-class").length>0){
                $(inputs[i]).addClass($(inputs[i]).attr("input-class"));
            }
        }
        $.parser.parse($(target+" tr:last"));
        payListChange(target +" tr:last");
        $(".dyTime").datebox({
            onSelect:function(beginDate){
                var date = new Date();
                if(beginDate>date){
                    info("时间不能大于当前时间");
                    $(this).combo('setText','');
                }
            }
        });
    }

    function payListChange(target) {
        $(target+" select[name='paylist_payType']").change(function () {
            var payTypeVal = $(this).find("option:selected").val();
            var parent = $(this).parent().parent();
            if(payTypeVal=='FRDK'){
                parent.find(".skgs").replaceWith("<td style='width: 220px;'></td>");
                parent.find(".dkr").replaceWith("<td style='width: 220px;'></td>");
                parent.find(".dksj").replaceWith("<td style='width: 220px;'></td>");
            }else{
                parent.closest("tr").remove();
                paylist_tt_add_row('#${paylist_model}paylist_src_table','#${paylist_model}paylist_tt_row_templet')
            }
        });
    }

    /*获取打款列表*/
    function getUpdatePayList(paylist_model){
        var dataList = [];
        var listTr = $("#"+paylist_model+"paylist_src_table").find("tr");
        if(listTr!=undefined && listTr.length>0){
            for(var i = 0;i<listTr.length;i++) {
                var data =  getUpdatePayListItemData(listTr[i]);
                dataList.push(data);
            }
        }
        return dataList;
    }
    /**
     * 获取tr的数据
     * @param tr
     */
    function getUpdatePayListItemData(tr){
        var id = $(tr).find("input[name='id']").val();
        var paylist_payType = $(tr).find("select[name='paylist_payType']").val();
        var paylist_amount = $(tr).find("input[name='paylist_amount']").val();
        var paylist_payUser = $(tr).find("input[name='paylist_payUser']").val();
        var paylist_payTime = $(tr).find("input[name='paylist_payTime']").val();
        var paylist_collectCompany = $(tr).find("select[name='paylist_collectCompany']").val();
        if(id==undefined)id='';
        return {
            id:id,
            payType:paylist_payType,
            amount:paylist_amount,
            payUser:paylist_payUser,
            payTime:paylist_payTime,
            collectCompany:paylist_collectCompany
        };
    }
</script>