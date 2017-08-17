<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Matrix Admin</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/bootstrap-responsive.min.css"/>
    <link rel="stylesheet" href="css/fullcalendar.css"/>
    <link rel="stylesheet" href="css/matrix-style.css"/>
    <link rel="stylesheet" href="css/matrix-media.css"/>
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet"/>
    <link rel="stylesheet" href="css/jquery.gritter.css"/>
    <link href='http://fonts.useso.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>
</head>
<body>

<!--Header-part-->
<div id="header">
    <h1><a href="dashboard.html">FSS Admin</a></h1>
</div>
<!--close-Header-part-->


<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
    <ul class="nav">
        <li class="dropdown" id="profile-messages"><a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i> <span class="text">Welcome User</span><b
                class="caret"></b></a>
            <ul class="dropdown-menu">
                <li><a href="#"><i class="icon-user"></i> My Profile</a></li>
                <li class="divider"></li>
                <li><a href="#"><i class="icon-check"></i> My Tasks</a></li>
                <li class="divider"></li>
                <li><a href="login.html"><i class="icon-key"></i> Log Out</a></li>
            </ul>
        </li>
        <li class="dropdown" id="menu-messages"><a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text">Messages</span> <span
                class="label label-important">5</span> <b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li><a class="sAdd" title="" href="#"><i class="icon-plus"></i> new message</a></li>
                <li class="divider"></li>
                <li><a class="sInbox" title="" href="#"><i class="icon-envelope"></i> inbox</a></li>
                <li class="divider"></li>
                <li><a class="sOutbox" title="" href="#"><i class="icon-arrow-up"></i> outbox</a></li>
                <li class="divider"></li>
                <li><a class="sTrash" title="" href="#"><i class="icon-trash"></i> trash</a></li>
            </ul>
        </li>
        <li class=""><a title="" href="#"><i class="icon icon-cog"></i> <span class="text">Settings</span></a></li>
        <li class=""><a title="" href="login.html"><i class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
    </ul>
</div>
<!--close-top-Header-menu-->
<!--start-top-serch-->
<div id="search">
    <input type="text" placeholder="Search here..."/>
    <button type="submit" class="tip-bottom" title="Search"><i class="icon-search icon-white"></i></button>
</div>
<!--close-top-serch-->
<div class="copyrights">Collect from <a href="https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1257661962,3777215241&fm=58">CHENMIN</a></div>
<!--sidebar-menu-->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Home</a>
    <ul>
        <li class="active"><a href="index.jsp"><i class="icon icon-home"></i> <span>Home</span></a> </li>
        <li><a href="jsp/notify/local.jsp"><i class="icon icon-th"></i> <span>Local Notifies</span></a></li>
        <li><a href="jsp/notify/remote.jsp"><i class="icon icon-th"></i> <span>Remote Notifies</span></a></li>
    </ul>
</div>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">
    <!--breadcrumbs-->
    <div id="content-header">
        <div id="breadcrumb"><a href="index.html" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
    </div>
    <!--End-breadcrumbs-->

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">
                    <div class="widget-content">
                        <table style="align-content: center">
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;Welcome To FSS System, My Family !&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                            <tr><td width="1%">*&nbsp;</td><td width="98%">&nbsp;</td><td width="1%">&nbsp;*</td></tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--end-main-container-part-->

<!--Footer-part-->

<div class="row-fluid">
    <div id="footer" class="span12">
        2015 &copy; GuiYi Admin. FSS - Collect from
        <a href="https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1257661962,3777215241&fm=58" title="CHENMIN" target="_blank">CHENMIN</a>
    </div>
</div>

<!--end-Footer-part-->

<script src="js/common/excanvas.min.js"></script>
<script src="js/common/jquery.min.js"></script>
<script src="js/common/jquery.ui.custom.js"></script>
<script src="js/common/bootstrap.min.js"></script>
<script src="js/common/jquery.flot.min.js"></script>
<script src="js/common/jquery.flot.resize.min.js"></script>
<script src="js/common/jquery.peity.min.js"></script>
<script src="js/common/fullcalendar.min.js"></script>
<script src="js/common/matrix.js"></script>
<script src="js/common/matrix.dashboard.js"></script>
<script src="js/common/jquery.gritter.min.js"></script>
<script src="js/common/matrix.interface.js"></script>
<script src="js/common/matrix.chat.js"></script>
<script src="js/common/jquery.validate.js"></script>
<script src="js/common/matrix.form_validation.js"></script>
<script src="js/common/jquery.wizard.js"></script>
<script src="js/common/jquery.uniform.js"></script>
<script src="js/common/select2.min.js"></script>
<script src="js/common/matrix.popover.js"></script>
<script src="js/common/jquery.dataTables.min.js"></script>
<script src="js/common/matrix.tables.js"></script>

<script type="text/javascript">
    // This function is called from the pop-up menus to transfer to
    // a different page. Ignore if the value returned is a null string:
    function goPage(newURL) {

        // if url is empty, skip the menu dividers and reset the menu selection to default
        if (newURL != "") {

            // if url is "-", it is this page -- reset the menu:
            if (newURL == "-") {
                resetMenu();
            }
            // else, send page to designated URL
            else {
                document.location.href = newURL;
            }
        }
    }

    // resets the menu selection upon entry to this page:
    function resetMenu() {
        document.gomenu.selector.selectedIndex = 2;
    }
</script>
</body>
</html>
