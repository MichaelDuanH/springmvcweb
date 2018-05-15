<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>    

<html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
     <jsp:include page="base/jsp/base.jsp"/>
    <title>计算器</title>
    <link href="main/i/logo.ico" type="image/x-icon" rel="icon"/>
   
  </head>
  <body>
   <div>
       <input type="button" value="计算" onclick="startCalc()"/> 
       <input type="button" value="清空" onclick="clearCalc()"/> <br/>
    <div>
      <table>
       <tr><td>总金额(万): </td><td><input type="text" id="totalAmountId"/></td></tr>
       <tr><td>年数:</td> <td><input type="text" id="yearsId"/></td></tr>
       <tr><td>利率: </td><td><input type="text" id="rateId"/></td></tr>
        <tr><td>月供: </td> <td><input type="text" id="resultId"></td></tr>
        <tr><td>总利息: </td> <td><input type="text" id="totalInterestRateId"></td></tr>
        <tr><td>月利息: </td> <td><input type="text" id="monthInterestRateId"></td></tr>
        <tr><td>本金: </td><td><input type="text" id="monthPayId"></td></tr>
      </table>
    </div>       
   </div>
   <script type="text/javascript">
     function clearCalc () {
    	   $("#totalAmountId").val("");
    	   $("#yearsId").val("");
    	   $("#rateId").val("");
    	   $("#resultId").val("");
    	   $("#monthRateId").val("");
    	   $("#monthPayId").val("");
    	   $("#totalInterestRateId").val("");
    	   $("#monthInterestRateId").val("");
     }
     function fomatFloat(src,pos){
       return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
     }
   
      function startCalc() {
    	  var totalAmount = $("#totalAmountId").val();
          var years =  $("#yearsId").val();
          var rate = $("#rateId").val();
          if(!totalAmount || !years || !rate) {
        		$.messager.alert('提示', '请正确填写必要的数据','info');
        		return;
          }
          totalAmount = totalAmount * 10000;
          var monthes = years * 12;
          var monthRate = rate / 100 / 12;
          //[贷款本金 * 月利率 * (1＋月利率)^还款月数]÷[(1＋月利率)^还款月数－1]
          var results = totalAmount * monthRate * Math.pow((1 + monthRate),monthes) / (Math.pow((1 + monthRate),monthes) - 1);
          $("#resultId").val(fomatFloat(results,3));
          //贷款本金 * 月利率 * [(1+月利率)^还款月数 - (1+月利率)^(还款月序号-1)]÷[(1+月利率)^还款月数-1]
      /*     var monthRates = 
        	  (totalAmount * monthRate * (
        			       Math.pow((1 + monthRate),monthes) - Math.pow((1 + monthRate),monthes-1)
        			     )) / (Math.pow((1 + monthRate),monthes) - 1);
          $("#monthRateId").val(fomatFloat(monthRates,3)); */
          //支付利息=月均还款*还款月数—贷款金额
          var totalInterestRate = results * monthes - totalAmount;
          $("#totalInterestRateId").val(fomatFloat(totalInterestRate,3));
          
          // 月均利息
          var monthInterestRate = totalInterestRate / monthes;
          $("#monthInterestRateId").val(fomatFloat(monthInterestRate,3));
          //贷款本金×月利率×(1+月利率)^(还款月序号-1)÷[(1+月利率)^还款月数-1]
      /*     var payments = 
        	       (totalAmount * monthRate * Math.pow((1 + monthRate),monthes-1)) 
        	       / (Math.pow((1 + monthRate),monthes) - 1); */
          var payments = results - monthInterestRate;
          $("#monthPayId").val(fomatFloat(payments,3));
      }
   </script>
  </body>
</html>