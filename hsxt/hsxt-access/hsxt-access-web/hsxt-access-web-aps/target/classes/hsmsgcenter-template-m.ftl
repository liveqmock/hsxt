<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="description" content="" >
	<meta name="keywords" content="" >
	<title>消息详情</title>
	<style type="text/css">
	  /**html,body{height:100%;}**/
	  body,ul,h1,h2,p{margin: 0;}
	  ul {padding:0; list-style-type:none;}
	  a {border: none;text-decoration: none;}
	  /*
	  body{
		  font-size:100%; 
		  font-family: Arial,"Microsoft Yahei",sans-serif; 
		  background:#FFF;
	  }
	  */
	p{
		text-indent: 2em; /*em是相对单位，2em即现在一个字大小的两倍*/
	}
	  body, td, th, h1, h2, h3, h4, h5, h6 {
		  font-family: Arial,SimSun,sans-serif;
		  font-size: 12px;
		}
	  /*.hgroup{
			margin:10px; 
	   }*/
	  .article{
		 /* border:1px solid #DEDEDE;
		  -moz-border-radius: 4px;
		  -webkit-border-radius:4px;
		  border-radius:4px;*/
		  padding:10px;
		  margin:10px;
		  font-size:100%;
		  background-color:#f7f7f7;
	  }
	  .clr:after{
		  content:'.';
		  display:block;
		  clear:both;
		  visibility:hidden;
		  height:0px;
		}
      .clr{
		   *zoom:1;
		}
	   .flt{
			float:left;
			display:inline;
	   }
	   .frt{
			float:right;
			display:inline;
	   }
	   .fl {float: left;display: inline;}
	   .fr {float: right;display: inline;}
	   .p10 {padding: 10px;}
	  /*.hgroup h1{
		  font-size:130%;
		  font-weight:normal;
	  }
	  .hgroup h2 {
		  margin:10px 0 20px 0;  
		  font-size:120%;
		  font-weight:normal;
		  color:#666;
	   }
	  .hgroup h2 span{
		  margin-right:20px;
	  }*/
	  .section{
		  margin:10px;
	  }
	  .section h1{
		  font-size:130%;
	  }
	  .section h3{
		  font-size:100%;
		  font-weight:normal;
		  color:#999;
	  }
	  .section p{ 
	  	  text-indent:2em;
		  line-height:1.5em;
		  margin-top:1em;
	  }
	  .section p:first-child{
		  margin-top:0;
	  }
	  .section .sign{margin-top:20px; text-align:right;}
	  .section .sign p{line-height:0.5em;}
	  .subtitle .span-left, .subtitle .span-right{float:left;display:inline;margin-top:10px;margin-bottom:10px;}
	  .subtitle .span-right{float:right;margin-top:6px;}
	  .xtxx_chat_area_content {
		  font-family: "Microsoft YaHei";
		  text-align: left;
		  overflow-y: auto;
	  }
	  .msg-pv-content-title-box {padding-top: 5px;height: 50px;border-bottom: 1px solid #999;}
	  .msg-pv-context-title-font {color: #3e8529;font-weight: bold;font-size: 14px;margin-bottom: 8px;}
	  .msg-pv-context-abstract {border: 1px dashed #CCC;padding: 10px;}
	  .mt20{margin-top:20px;}
    </style>
</head>
<body>
<!--
<div class="article">
	<div class="section">
    	<h1>${title}</h1>
		<div class="subtitle clr">
			<span class="span-left">${sendTime}</span>
			<span class="span-right">${entCustName}</span>
		</div>
		<div class="msg-pv-context-abstract">
			<p>${summary}</p>
		</div>
		<#if images??>
		<div  class="mt20">
			<img src="${images}" width="" height="" style="width:100%;height:100%;-webkit-background-size:100% 100%;background-size:100% 100%;-moz-background-size:100% 100%;">
		</div>
		</#if>
		<div  class="mt20">
	        <p style="font-family:宋体">
	          ${msg}
	        </p>
        </div>
    </div>
</div>
-->
	<div class="clearfix xtxx_chat_area_content p10">
		<div class="msg-pv-content-title-box">
			<div class="msg-pv-context-title-font">${title}</div>
			<span class="fl">${sendTime}</span> <span class="fr">${entCustName}</span>
		</div>
		<div class="p10">
			<div class="msg-pv-context-abstract">
				<p>${summary}</p>
			</div>
			<#if images??>
			<div class="mt20 msg_pv_context_img">
				<img src="${images}" width="" height="" style="width:100%;height:100%;-webkit-background-size:100% 100%;background-size:100% 100%;-moz-background-size:100% 100%;">
			</div>
			</#if>
			<div class="mt20 msg_pv_content">
				<p>${msg}</p>
			</div>
		</div>
	</div>
</body>
</html>