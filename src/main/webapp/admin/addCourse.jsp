<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>  
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>新增课程</title>
    <link rel="stylesheet" href="../plugins/layui2.x/css/layui.css">
    <link rel="stylesheet" href="../css/style.css">
    <style>
    	.layui-input{height:26px;font-size:12px;width:180px;}
    	.layui-form-select{font-size:12px;width:180px;}
    	dd{line-height:26px;font-size:12px;width:140px;}
    	.layui-elem-field legend{font-size:12px;}
    	.layui-form-radio *{line-height:26px;font-size:12px;}
    </style>
</head>
<body class="body">

<form class="layui-form" action="">  

 <fieldset class="layui-elem-field">
  <legend>基本信息</legend>
  		<div class="layui-form-item" style="margin-bottom:3px;">
	        <label class="layui-form-label" style="font-size:12px;line-height:10px;">课程名称</label>
	        <div class="layui-input-inline">
	            <input type="text" name="courseName" id="loginId" lay-verify="required" placeholder="必填项" autocomplete="off" 
	            class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
	        </div>
	        
	        <!-- <label class="layui-form-label" style="font-size:12px;line-height:10px;">姓名</label>
	         <div class="layui-input-inline">
	            <input type="text" name="userName" id="userName" lay-verify="required" placeholder="必填项" autocomplete="off"
	                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
	        </div> -->
        </div>
     </fieldset>
    <!--  <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">邮箱</label>
        <div class="layui-input-inline">
            <input type="text" name="email" id="email" lay-verify="required|email" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">手机号</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" id="phone" lay-verify="required|phone" placeholder="必填项" autocomplete="off" 
            class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        </div> 
      <div class="layui-form-item" style="margin-bottom:3px;">
      <label class="layui-form-label" style="font-size:12px;line-height:10px;">民族</label>

				<div class="layui-input-inline">
					 <input type="text" name="nation" id="nation" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
					<select name="nation" id="nation" lay-verify="required"
						lay-search="">
						<option value="">请选择</option>
						<option value="汉">汉族</option>
						<option value="蒙古族">蒙古族</option>
						<option value="回族">回族</option>
						<option value="藏族">藏族</option>
						<option value="维吾尔族">维吾尔族</option>
						<option value="苗族">苗族</option>
						<option value="彝族">彝族</option>
						<option value="壮族">壮族</option>
						<option value="布依族">布依族</option>
						<option value="朝鲜族">朝鲜族</option>
						<option value="满族">满族</option>
						<option value="侗族">侗族</option>
						<option value="瑶族">瑶族</option>
						<option value="白族">白族</option>
						<option value="土家族">土家族</option>
						<option value="哈尼族">哈尼族</option>
						<option value="哈萨克族">哈萨克族</option>
						<option value="傣族">傣族</option>
						<option value="黎族">黎族</option>
						<option value="傈僳族">傈僳族</option>
						<option value="佤族">佤族</option>
						<option value="畲族">畲族</option>
						<option value="高山族">高山族</option>
						<option value="拉祜族">拉祜族</option>
						<option value="水族">水族</option>
						<option value="东乡族">东乡族</option>
						<option value="纳西族">纳西族</option>
						<option value="景颇族">景颇族</option>
						<option value="柯尔克孜族">柯尔克孜族</option>
						<option value="土族">土族</option>
						<option value="达斡尔族">达斡尔族</option>
						<option value="仫佬族">仫佬族</option>
						<option value="羌族">羌族</option>
						<option value="布朗族">布朗族</option>
						<option value="撒拉族">撒拉族</option>
						<option value="毛南族">毛南族</option>
						<option value="仡佬族">仡佬族</option>
						<option value="锡伯族">锡伯族</option>
						<option value="阿昌族">阿昌族</option>
						<option value="普米族">普米族</option>
						<option value="塔吉克族">塔吉克族</option>
						<option value="怒族">怒族</option>
						<option value="乌孜别克族">乌孜别克族</option>
						<option value="俄罗斯族">俄罗斯族</option>
						<option value="鄂温克族">鄂温克族</option>
						<option value="德昂族">德昂族</option>
						<option value="保安族">保安族</option>
						<option value="裕固族">裕固族</option>
						<option value="京族">京族</option>
						<option value="塔塔尔族">塔塔尔族</option>
						<option value="独龙族">独龙族</option>
						<option value="鄂伦春族">鄂伦春族</option>
						<option value="赫哲族">赫哲族</option>
						<option value="门巴族">门巴族</option>
						<option value="珞巴族">珞巴族</option>
						<option value="基诺族">基诺族</option>
					</select>
				</div>

				<label class="layui-form-label" style="font-size:12px;line-height:10px;">出生日期</label>

         <div class="layui-input-inline">
            <input type="text" name="birthday" id="birthday" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div> 
    </div> 
    <div class="layui-form-item" style="margin-bottom:3px;">
        
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">籍贯</label>
        <div class="layui-input-inline">
            <input type="text" name="birthPlace" id="birthPlace" lay-verify="required" placeholder="籍贯" autocomplete="off" 
            class="layui-input layui-form-danger"  style="height:26px;font-size:12px;" />
        </div>
        
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">性别</label>     
	    <div class="layui-input-inline layui-form" lay-filter="radioFilter" style="margin-top:-6px;font-size:12px;">
	      <input id="sex1" type="radio" name="sex" value="男" title="男" checked="" />
	      <input id="sex2" type="radio" name="sex" value="女" title="女" />
	    </div>
    
        
    </div>
    
    <div class="layui-form-item" style="margin-bottom:3px;">
    	<label class="layui-form-label" style="font-size:12px;line-height:10px;">居住地址</label>
        <div class="layui-input-inline">
            <input type="text" name="address" id="address" lay-verify="required" placeholder="必填项" autocomplete="off" 
            class="layui-input layui-form-danger" style="width:490px;height:26px;font-size:12px;" />
        </div>
    </div>
    
        <div class="layui-form-item" style="margin-bottom:3px;">
         <label class="layui-form-label" style="font-size:12px;line-height:10px;">最高学历</label>
        <div class="layui-input-inline">
            <input type="text" name="education" id="education" lay-verify="required" placeholder="必填项" autocomplete="off" 
            class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">毕业院校</label>
        <div class="layui-input-inline">
            <input type="text" name="school" id="school" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
    </div>
  <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">工号</label>
         <div class="layui-input-inline">
            <input type="text" name="employeeCode" id="employeeCode" lay-verify="required|number" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">所属部门</label>
        <div class="layui-input-inline">
            <input type="text" name="dept" id="dept" lay-verify="required" placeholder="必填项" autocomplete="off" 
            class="layui-input layui-form-danger " style="height:26px;font-size:12px;">
        </div>
    </div>
    
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">角色类型</label>
        <div class="layui-input-inline layui-form" lay-filter="selRoleIdFilter">
        <select id="roleId" name="roleId" lay-verify="required" lay-search="" >
          
        </select>
      </div> 
         <label class="layui-form-label" style="font-size:12px;line-height:10px;">职位</label>
        <div class="layui-input-inline">
            <input type="text" name="positionTitle" id="positionTitle" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div>
    </div>
        
    <div class="layui-form-item" style="margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">单位名称</label>
        <div class="layui-input-inline">
            <input type="text" name="companyName" id="companyName" lay-verify="required" placeholder="必填项" autocomplete="off" 
            class="layui-input layui-form-danger " style="height:26px;font-size:12px;" >
        </div>
        
        <label class="layui-form-label" style="font-size:12px;line-height:10px;">入职日期</label>
         <div class="layui-input-inline">
            <input type="text" name="hiredate" id="hiredate" lay-verify="required" placeholder="必填项" autocomplete="off"
                   class="layui-input layui-form-danger" style="height:26px;font-size:12px;">
        </div> 
    </div>
        
    <div class="layui-form-item" style="margin-bottom:3px;">
        	<label class="layui-form-label" style="font-size:12px;line-height:10px;">所属应急仓库</label>
        <div class="layui-input-inline layui-form" lay-filter="warehouseIdFilter">
        <select id="warehouseId" name="warehouseId" lay-verify="required" lay-search="" >
        </select>
      </div> 
    </div>
    
    <div class="layui-form-item layui-form-text" style=" margin-bottom:3px;">
        <label class="layui-form-label" style="font-size:12px;width:80px;line-height:10px;">备注信息</label>
        <div class="layui-input-inline">
            <textarea id="note" name="note" placeholder="请输入用户备注" class="layui-textarea" style="width:490px;height:26px;font-size:12px;min-height:50px;"></textarea>
        </div> 
    </div> -->

    <div class="layui-form-item" style="text-align: center;margin-top:10px;">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-submit="" lay-filter="addForm">保存</button>
        &nbsp;&nbsp;
        <button class="layui-btn layui-btn-sm layui-btn-normal" id="close">取消</button>
    </div>
</form>

<script src="../plugins/layui2.x/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    layui.use(['form', 'layedit', 'laydate', 'element','upload'], function () {
    var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate
            , element = layui.element
            , upload = layui.upload;
        
    var $ = layui.jquery;

    //日期插件加载
    laydate.render({ 
        elem: '#hiredate' 
     });
    //自定义表单验证
    form.verify({  	
    	 phone:[/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,'手机号码格式不正确'],
    	 email: [/^$|^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$|^1[3|4|5|7|8]\d{9}$/, '邮箱格式不正确'],
    });
  //重新渲染表单
  function renderForm(){
	  form.render();
  }
  //保存按钮
  form.on('submit(addForm)', function (data) {
	  
	    // formJson.birthday=new Date(formJson.birthday.replace("-","/"));   
        var formJson = data.field;
       // formJson.hiredate=new Date(formJson.hiredate.replace("-","/"));
        //console.info(data);
       // console.info(formJson);
       	$.ajax({
   			method: "post",
   			url:"${pageContext.request.contextPath}/course/save",
   			data: formJson,
   			async:false,
   			success:function(result){
   				var data = result.data;
               	if(data>0){
               		//关闭窗口 并给父页面传值
                       var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                       parent.layer.msg('添加成功！', {title:'提示消息',icon: 1, time: 1500}); //1s后自动关闭);             
                       parent.reloadTable(1);
                       parent.layer.close(index);
               	}else{ 
               		 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                       parent.layer.msg('添加失败！', {title:'提示消息',icon: 2, time: 1500}); //1s后自动关闭);                          
                       parent.layer.close(index);
               	}
   			},
   			error: function(){			
   				 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.msg('服务器异常！', {title:'提示消息',icon: 0, time: 1500}); //1s后自动关闭);                          
                    parent.layer.close(index);	
		}  	
           });     
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