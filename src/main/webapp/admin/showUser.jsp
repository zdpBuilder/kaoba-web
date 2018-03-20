<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
//仓库主键id
String id = request.getParameter("userId");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>查看用户信息</title>
    <link rel="stylesheet" href="../frame/layui/css/layui.css">
    <link rel="stylesheet" href="../frame/static/css/style.css">
    <link rel="stylesheet" href="../../component/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="icon" href="../frame/static/image/code.png">
    <style>
    	.layui-input{height:26px;}
    </style>
</head>
<body class="body">

<form class="layui-form" action="">
  <input type="hidden" name="userId" id="userId" value="<%=id %>"/>
     <input type="hidden" name="password" id="password" >
    
     <%--  <div class="layui-form-item" style="margin-bottom:3px;">  
     <label class="layui-form-label" style="font-size:12px;line-height:10px;">头像照片
        <button title="点击上传用户照片" type="button" class="layui-btn layui-btn-sm" id="innerPhoto" style="margin-top:10px;">
			  <i class="layui-icon">&#xe67c;</i><span style="font-size:10px;line-height:10px;">上传</span>
			</button>
        </label>
        <div class="layui-input-block" style="margin-left:10px;">
			<input type="hidden" name="avatarUrl" id="avatarUrl" />
            <img id="innerPhotoShow" src="${pageContext.request.contextPath}/img/upload_default.png" style="display: hidden;margin-left:10px;width:80px;height:80px;font-size:12px;"/>
        </div> 
         </div> --%>
         <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">登陆账号</label>
        <div class="layui-input-block">
            <input type="text" name="loginId" id="loginId" 
           autocomplete="off" disabled class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>
        
           
        
    
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">所属部门</label>
        <div class="layui-input-block">
            <input type="text" name="deptId" id="deptId" lay-verify="required" placeholder="必填项" autocomplete="off" 
           autocomplete="off" disabled class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>
        <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">工号</label>
         <div class="layui-input-block">
            <input type="text" name="employeeCode" id="employeeCode" lay-verify="required" placeholder="必填项" autocomplete="off"
              autocomplete="off" disabled     class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        </div>
        <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">姓名</label>
         <div class="layui-input-block">
            <input type="text" name="userName" id="userName" lay-verify="required" placeholder="必填项" autocomplete="off"
              autocomplete="off" disabled     class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        </div>
         <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">角色类型</label>
        <div class="layui-input-block">
            <input type="text" name="roleId" id="roleId" lay-verify="required" placeholder="必填项" autocomplete="off" 
           autocomplete="off" disabled class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>
         <div class="layui-form-item" style="margin-bottom:3px;">
        
         <label class="layui-form-label" style="font-size:12px;line-height:10px;">职位</label>

        <div class="layui-input-block">
            <input type="text" name="positionTitle" id="positionTitle" lay-verify="required" placeholder="必填项" autocomplete="off"
             autocomplete="off" disabled      class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        </div>
         <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">公司名称</label>
        <div class="layui-input-block">
            <input type="text" name="companyName" id="companyName" lay-verify="required" placeholder="必填项" autocomplete="off" 
            autocomplete="off" disabled class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>
         <div class="layui-form-item" style="margin-bottom:3px;">
         <label class="layui-form-label" style="font-size:12px;line-height:10px;">最高学历</label>
        <div class="layui-input-block">
            <input type="text" name="education" id="education" lay-verify="required" placeholder="必填项" autocomplete="off" 
           autocomplete="off" disabled class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>
   <!--  <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">入职日期</label>
        <div class="layui-input-block">
            <input type="text" name="hiredate" id="hiredate" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
    </div>  -->
    
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">毕业院校</label>
        <div class="layui-input-block">
            <input type="text" name="school" id="school" lay-verify="required" placeholder="必填项" autocomplete="off"
               autocomplete="off" disabled    class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
    </div>
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">性别</label>
        <div class="layui-input-block">
            <input type="text" name="sex" id="sex" lay-verify="required" placeholder="必填项" autocomplete="off" 
           autocomplete="off" disabled class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div>
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">邮箱</label>
        <div class="layui-input-block">
            <input type="text" name="email" id="email" lay-verify="required" placeholder="必填项" autocomplete="off"
            autocomplete="off" disabled       class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
    </div>
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">手机号</label>
        <div class="layui-input-block">
            <input type="text" name="phone" id="phone" lay-verify="required" placeholder="必填项" autocomplete="off" 
           autocomplete="off" disabled class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div> 
     <!-- <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">出生日期</label>

        <div class="layui-input-block">
            <input type="text" name="birthday" id="birthday" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
    </div> -->
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">民族</label>

        <div class="layui-input-block">
            <input type="text" name="nation" id="nation" lay-verify="required" placeholder="必填项" autocomplete="off"
             autocomplete="off" disabled      class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
    </div>
     <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">籍贯</label>
        <div class="layui-input-block">
            <input type="text" name="birthPlace" id="birthPlace" lay-verify="required" placeholder="籍贯" autocomplete="off" 
           autocomplete="off" disabled class="layui-input layui-form-danger" />
        </div>
    </div>
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">现居住地址</label>
        <div class="layui-input-block">
            <input type="text" name="address" id="address" lay-verify="required" placeholder="详细地址" autocomplete="off" 
           autocomplete="off" disabled class="layui-input layui-form-danger" />
        </div>
    </div>
    
    <div class="layui-form-item layui-form-text" style=" margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;width:80px;line-height:10px;">备注信息</label>
        <div class="layui-input-block">
            <textarea id="note" name="note" placeholder="请输入用户备注"  autocomplete="off" disabled class="layui-textarea" style="min-height:30px;font-size:12px;"></textarea>
        </div> 
    </div>
    
    
</form>

<script src="../frame/layui/layui.js" charset="utf-8"></script>
<script src="../frame/static/js/area_data.js" charset="utf-8"></script>
<script type="text/javascript" src="../js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.qrcode.min.js"></script>
<script type="text/javascript">
//页面打印 二维码特殊处理
function doPrint() {   
    bdhtml=window.document.body.innerHTML;   
    sprnstr="<!--startprint-->";   
    eprnstr="<!--endprint-->";   
    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);   
    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));   
    window.document.body.innerHTML=prnhtml;  
    window.print();   
}   

//二维码转中文
function utf16to8(str) {  
    var out, i, len, c;  
    out = "";  
    len = str.length;  
    for(i = 0; i < len; i++) {  
    c = str.charCodeAt(i);  
    if ((c >= 0x0001) && (c <= 0x007F)) {  
        out += str.charAt(i);  
    } else if (c > 0x07FF) {  
        out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));  
        out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));  
        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));  
    } else {  
        out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));  
        out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));  
    }  
    }  
    return out;  
}  


var info = 

"仓库编码:QZK2018012800001,"+
"仓库名称:钦州应急设备库";

//"仓库简称:钦州库,"+
//"主管单位:钦州海事局,"+
//"经营单位:中维华信,"+
//"联系人:李总,"+
//"电话:138××××××××,";
info = utf16to8(info);
//二维码生成器
jQuery('#qrcode').qrcode({
	render: "canvas", //渲染方式:canvas/table
    width: 100,
    height: 100,
    foreground: "#000",//前景色
    background: "#FFF",//背景色
    typeNumber : -1,      //计算模式  
    text: info
});

	layui.extend({
		picker: '../frame/static/js/picker' // {/}的意思即代表采用自有路径，即不跟随 base 路径
	})
	
    layui.use(['form', 'picker', 'upload','layedit', 'laydate', 'element'], function () {
    	
        var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                , laydate = layui.laydate
                , element = layui.element
                , upload = layui.upload
                , picker = layui.picker;
        var $ = layui.jquery;

      //重新渲染表单
      function renderForm(){
    	  form.render();
      }
      
      //省市区三级联动下拉默认
      var picker = layui.picker;
      var area = new picker();
      area.set({
          elem: '#region',
          data: Areas,
          codeConfig: {
              code: 110101,
              type: 3
          }
      }).render();
      
    //表单元素赋值
      var userId = <%=id %>;
      $.ajax({
			method: "post",
			data : {"id":userId},
			url:"../../user/show",
			success:function(result){
				result = result.data;
				if(result){
					$("#loginId").val(result.loginId);
					$("#deptId").val(result.deptId);
					$("#roleId").val(result.roleId);
					$("#employeeCode").val(result.employeeCode);
					$("#userName").val(result.userName);
					$("#positionTitle").val(result.positionTitle);
					$("#companyName").val(result.companyName);
					//$("#hiredate").val(result.hiredate);
					//$("#avatarUrl").val(result.avatarUrl);
					$("#password").val(result.password);
					$("#birthPlace").val(result.birthPlace);
					//$("#birthday").val(result.birthday);
					$("#education").val(result.education);
					$("#school").val(result.school);
					$("#address").val(result.address);
					$("#nation").val(result.nation);
					$("#sex").val(result.sex);
					$("#email").val(result.email);
					$("#phone").val(result.phone);
					$("#note").val(result.note);		
				}
			}
      });
      
        //关闭窗口按钮
        $("#close").click(function(){
        	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        	parent.layer.close(index);
        });
        
    });
</script>
</body>
</html>