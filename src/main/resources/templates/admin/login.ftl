<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${message("admin.login.title")} - Powered By ${setting.siteName}</title>
    <meta name="keywords" content="${keywords}">
    <meta name="description" content="${description}">
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/animate.css@3.7.2/animate.min.css">
    <link href="${static}/css/style.min.css?v=4.1.0" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;${admin}/ie.html"/>
    <![endif]-->
</head>

<body class="gray-bg">


<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>
            <img src="${setting.logo}" alt="${setting.siteName}" class="img-circle"/>
        </div>
        <h3>${setting.siteName}</h3>

        <form class="form-horizontal" role="form" action="${admin}/login" method="post">
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">${message("admin.login.username")}</label>
                <div class="col-sm-10">
                    <input type="text" id="username" name="username"
                           class="form-control required"
                           placeholder="${message("admin.login.username")}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">${message("admin.login.password")}</label>
                <div class="col-sm-10">
                    <input type="password" id="password" name="password"
                           class="form-control required"
                           placeholder="${message("admin.login.password")}"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit"
                            class="btn btn-primary block full-width m-b">${message("admin.login.login")}</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- 全局js -->
<script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/js/bootstrap.min.js"></script>
<script>
    if (window.top !== window.self) {
        window.top.location = window.location;
    }
</script>
</body>
</html>
