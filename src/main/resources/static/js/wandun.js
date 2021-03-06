/*
 *
 *   H+ - 后台主题UI框架
 *   version 4.9
 *
*/

//自定义js

//公共配置


$(document).ready(function () {
    // MetsiMenu
    $('#side-menu').metisMenu();

    // 打开右侧边栏
    $('.right-sidebar-toggle').click(function () {
        $('#right-sidebar').toggleClass('sidebar-open');
    });

    // 右侧边栏使用slimscroll
    $('.sidebar-container').slimScroll({
        height: '100%',
        railOpacity: 0.4,
        wheelStep: 10
    });

    // 打开聊天窗口
    $('.open-small-chat').click(function () {
        $(this).children().toggleClass('fa-comments').toggleClass('fa-remove');
        $('.small-chat-box').toggleClass('active');
    });

    // 聊天窗口使用slimscroll
    $('.small-chat-box .content').slimScroll({
        height: '234px',
        railOpacity: 0.4
    });
    $(("[type='checkbox']")).iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_square-blue'
    });
    // Small todo handler
    $('.check-link').click(function () {
        var button = $(this).find('i');
        var label = $(this).next('span');
        button.toggleClass('fa-check-square').toggleClass('fa-square-o');
        label.toggleClass('todo-completed');
        return false;
    });

    //固定菜单栏
    $(function () {
        $('.sidebar-collapse').slimScroll({
            height: '100%',
            railOpacity: 0.9,
            alwaysVisible: false
        });
    });


    // 菜单切换
    $('.navbar-minimalize').click(function () {
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });


    // 侧边栏高度
    function fix_height() {
        var heightWithoutNavbar = $("body > #wrapper").height() - 61;
        $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
    }

    fix_height();

    $(window).bind("load resize click scroll", function () {
        if (!$("body").hasClass('body-small')) {
            fix_height();
        }
    });

    //侧边栏滚动
    $(window).scroll(function () {
        if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav')) {
            $('#right-sidebar').addClass('sidebar-top');
        } else {
            $('#right-sidebar').removeClass('sidebar-top');
        }
    });

    $('.full-height-scroll').slimScroll({
        height: '100%'
    });

    $('#side-menu>li').click(function () {
        if ($('body').hasClass('mini-navbar')) {
            NavToggle();
        }
    });
    $('#side-menu>li li a').click(function () {
        if ($(window).width() < 769) {
            NavToggle();
        }
    });

    $('.nav-close').click(NavToggle);

    //ios浏览器兼容性处理
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        $('#content-main').css('overflow-y', 'auto');
    }

    $('input,select,textarea').bind("keyup", function (e) {
        if (e.which == 39) { // right arrow
            $(this).closest('td').next().find('input,select,textarea').focus();
        } else if (e.which == 37) { // left arrow
            $(this).closest('td').prev().find('input,select,textarea').focus();
        } else if (e.which == 40) { // down arrow
            $(this).closest('tr').next().find('td:eq(' + $(this).closest('td').index() + ')').find('input,select,textarea').focus();
        } else if (e.which == 38) { // up arrow
            $(this).closest('tr').prev().find('td:eq(' + $(this).closest('td').index() + ')').find('input,select,textarea').focus();
        }
    });
});

$(window).bind("load resize", function () {
    if ($(this).width() < 769) {
        $('body').addClass('mini-navbar');
        $('.navbar-static-side').fadeIn();
    }
});

function NavToggle() {
    $('.navbar-minimalize').trigger('click');
}

function SmoothlyMenu() {
    if (!$('body').hasClass('mini-navbar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 100);
    } else if ($('body').hasClass('fixed-sidebar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 300);
    } else {
        $('#side-menu').removeAttr('style');
    }
}


//主题设置
$(function () {

    // 顶部菜单固定
    $('#fixednavbar').click(function () {
        if ($('#fixednavbar').is(':checked')) {
            $(".navbar-static-top").removeClass('navbar-static-top').addClass('navbar-fixed-top');
            $("body").removeClass('boxed-layout');
            $("body").addClass('fixed-nav');
            $('#boxedlayout').prop('checked', false);

            if (localStorageSupport) {
                localStorage.setItem("boxedlayout", 'off');
            }

            if (localStorageSupport) {
                localStorage.setItem("fixednavbar", 'on');
            }
        } else {
            $(".navbar-fixed-top").removeClass('navbar-fixed-top').addClass('navbar-static-top');
            $("body").removeClass('fixed-nav');

            if (localStorageSupport) {
                localStorage.setItem("fixednavbar", 'off');
            }
        }
    });


    // 收起左侧菜单
    $('#collapsemenu').click(function () {
        if ($('#collapsemenu').is(':checked')) {
            $("body").addClass('mini-navbar');
            SmoothlyMenu();

            if (localStorageSupport) {
                localStorage.setItem("collapse_menu", 'on');
            }

        } else {
            $("body").removeClass('mini-navbar');
            SmoothlyMenu();

            if (localStorageSupport) {
                localStorage.setItem("collapse_menu", 'off');
            }
        }
    });

    // 固定宽度
    $('#boxedlayout').click(function () {
        if ($('#boxedlayout').is(':checked')) {
            $("body").addClass('boxed-layout');
            $('#fixednavbar').prop('checked', false);
            $(".navbar-fixed-top").removeClass('navbar-fixed-top').addClass('navbar-static-top');
            $("body").removeClass('fixed-nav');
            if (localStorageSupport) {
                localStorage.setItem("fixednavbar", 'off');
            }
            if (localStorageSupport) {
                localStorage.setItem("boxedlayout", 'on');
            }
        } else {
            $("body").removeClass('boxed-layout');
            if (localStorageSupport) {
                localStorage.setItem("boxedlayout", 'off');
            }
        }
    });

    $(".nb").click(function () {
        var style = $(this).attr("id");
        $("body").removeClass(function (index, className) {
            return (className.match(/(^|\s)skin-\S+/g) || []).join(' ');
        });
        $("body").addClass(style);
        if (localStorageSupport) {
            localStorage.setItem("skin", style);
        }
        return false;
    });
    if (localStorageSupport) {
        var skin = localStorage.getItem("skin");
        var body = $('body');

        if (skin !== null) {
            if (!body.hasClass(skin)) {
                body.addClass(skin);
            }
        }
    }
    if (localStorageSupport) {
        var collapse = localStorage.getItem("collapse_menu");
        var fixednavbar = localStorage.getItem("fixednavbar");
        var boxedlayout = localStorage.getItem("boxedlayout");

        if (collapse == 'on') {
            $('#collapsemenu').prop('checked', 'checked')
        }
        if (fixednavbar == 'on') {
            $('#fixednavbar').prop('checked', 'checked')
        }
        if (boxedlayout == 'on') {
            $('#boxedlayout').prop('checked', 'checked')
        }
    }

    if (localStorageSupport) {

        var collapse = localStorage.getItem("collapse_menu");
        var fixednavbar = localStorage.getItem("fixednavbar");
        var boxedlayout = localStorage.getItem("boxedlayout");

        var body = $('body');

        if (collapse == 'on') {
            if (!body.hasClass('body-small')) {
                body.addClass('mini-navbar');
            }
        }

        if (fixednavbar == 'on') {
            $(".navbar-static-top").removeClass('navbar-static-top').addClass('navbar-fixed-top');
            body.addClass('fixed-nav');
        }

        if (boxedlayout == 'on') {
            body.addClass('boxed-layout');
        }
    }
});


(function ($) {
    // 检测登录
    $.checkLogin = function () {
        var result = false;
        $.ajax({
            url: global.ctx + "/login/check.html",
            type: "GET",
            dataType: "json",
            cache: false,
            async: false,
            success: function (data) {
                result = data;
            }
        });
        return result;
    }
    // 跳转登录
    $.redirectLogin = function (redirectUrl, message) {
        var href = global.ctx + "/login/index.html";
        if (redirectUrl != null) {
            href += "?redirectUrl=" + encodeURIComponent(redirectUrl);
        }
        if (message != null) {
            $.message("warn", message);
            setTimeout(function () {
                location.href = href;
            }, 1000);
        } else {
            location.href = href;
        }
    }

    $.message = function () {
        var message = {};
        if ($.isPlainObject(arguments[0])) {
            message = arguments[0];
        } else if (typeof arguments[0] === "string" && typeof arguments[1] === "string") {
            message.type = arguments[0];
            message.content = arguments[1];
        } else {
            return false;
        }
        if (message.type == null || message.content == null) {
            return false;
        }
        switch (message.type) {
            case "error":
                top.layer.alert(message.content, {icon: 2});
                break;
            case "warn":
                top.layer.alert(message.content, {icon: 3});
                break;
            default:
                top.layer.alert(message.content, {icon: 1});
        }
    }

    $.validateForm = function () {
        return arguments[0].validate();
    }
    $.doSubmit = function (form, index) {

        form.ajaxSubmit({
            dataType: "json",
            forceSync: true,
            success: function (data) {
                top.layer.close(this.layerIndex);
                switch (data.type) {
                    case "error":
                        top.layer.alert(data.content, {icon: 2});
                        break;
                    case "warn":
                        top.layer.alert(data.content, {icon: 3});
                        break;
                    default:
                        top.layer.alert(data.content, {icon: 1});
                        parent.layer.close(index);
                        var timestamp = new Date().getTime();
                        top.getActiveTab().attr("src", top.getActiveTab().attr("src") + "?timestamp=" + timestamp);
                        top.getActiveTab()[0].contentDocument.forms['listForm'].submit();
                }
            },
            beforeSubmit: function (arr, $form, options) {
                this.layerIndex = top.layer.load(0, {shade: [0.5, '#393D49']});
            }
        });
    }

    $.doPrintSubmit = function (form, url, index) {
        form.ajaxSubmit({
            dataType: "json",
            forceSync: true,
            success: function (json) {
                top.layer.close(this.layerIndex);
                switch (json.type) {
                    case "error":
                        top.layer.alert(json.content, {icon: 2});
                        break;
                    case "warn":
                        top.layer.alert(json.content, {icon: 3});
                        break;
                    default:
                        top.layer.alert(json.content, {icon: 1});
                        parent.layer.close(index);

                        var sn = json.data.sn;

                        var print = top.layer.open({
                            type: 2,
                            area: ['100%', '100%'],
                            title: "打印",
                            maxmin: true, // 开启最大化最小化按钮
                            content: url + "?sn=" + sn
                        });
                        top.layer.full(print);


                        var timestamp = new Date().getTime();
                        top.getActiveTab().attr("src", top.getActiveTab().attr("src") + "?timestamp=" + timestamp);
                        top.getActiveTab()[0].contentDocument.forms['listForm'].submit();
                }
            },
            beforeSubmit: function (arr, $form, options) {
                this.layerIndex = top.layer.load(0, {shade: [0.5, '#393D49']});
            }
        });
    }


})(jQuery);

//判断浏览器是否支持html5本地存储
function localStorageSupport() {
    return (('localStorage' in window) && window['localStorage'] !== null)
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == "") {
        url = window.location.search;
    } else {
        url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]);
    return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue) {
    for (var i = 0; i < data.length; i++) {
        var row = data[i];
        if (row.value == value) {
            return row.label;
        }
    }
    return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height) {
    var top = parseInt((window.screen.height - height) / 2, 10), left = parseInt((window.screen.width - width) / 2, 10),
        options = "location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes," +
            "resizable=yes,scrollbars=yes," + "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left;
    window.open(url, name, options);
}

// 显示加载框
function loading(mess) {
    var index = top.layer.load(1, {
        shade: [0.1, '#fff'] //0.1透明度的白色背景
    });
    setTimeout(function () {
        top.layer.close(index)
    }, 1000);//延时0.1秒，对应360 7.1版本bug
}

// 打开对话框(添加修改)
function openDialog(title, url, width, height, target) {
    top.layer.open({
        type: 2,
        area: [width, height],
        title: title,
        maxmin: true, // 开启最大化最小化按钮
        content: url,
        btn: ['确定', '关闭'],
        yes: function (index, layero) {
            var body = top.layer.getChildFrame('body', index);
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            var inputForm = body.find('#inputForm');
            var top_iframe;
            if (target) {
                top_iframe = target;// 如果指定了iframe，则在改frame中跳转
            } else {
                top_iframe = top.getActiveTab().attr("name");// 获取当前active的tab的iframe
            }

            inputForm.attr("target", top_iframe);// 表单提交成功后，从服务器返回的url在当前tab中展示
            if ($.validateForm(inputForm).form()) {

                var contentWindow = iframeWin.contentWindow;
                contentWindow.$.doSubmit(inputForm, index);
            }
            return false;
        },
        cancel: function (index) {
        }
    });
}

//打开对话框(添加并打印)
function openDialogPrint(title, url, width, height, target, printurl) {
    top.layer.open({
        type: 2,
        area: [width, height],
        title: title,
        maxmin: true, // 开启最大化最小化按钮
        content: url,
        btn: ['确定', '确定并打印', '关闭'],
        yes: function (index, layero) {
            var body = top.layer.getChildFrame('body', index);
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            var inputForm = body.find('#inputForm');
            var top_iframe;
            if (target) {
                top_iframe = target;// 如果指定了iframe，则在改frame中跳转
            } else {
                top_iframe = top.getActiveTab().attr("name");// 获取当前active的tab的iframe
            }
            inputForm.attr("target", top_iframe);// 表单提交成功后，从服务器返回的url在当前tab中展示
            if ($.validateForm(inputForm).form()) {
                $.doSubmit(inputForm, index);
            }
            return false;
        },
        btn2: function (index, layero) {
            var body = top.layer.getChildFrame('body', index);
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            var inputForm = body.find('#inputForm');
            var top_iframe;
            if (target) {
                top_iframe = target;// 如果指定了iframe，则在改frame中跳转
            } else {
                top_iframe = top.getActiveTab().attr("name");// 获取当前active的tab的iframe
            }
            inputForm.attr("target", top_iframe);// 表单提交成功后，从服务器返回的url在当前tab中展示

            if ($.validateForm(inputForm).form()) {
                $.doPrintSubmit(inputForm, printurl, index);
            }

            return false;
        },
        cancel: function (index) {
        }
    });
}

// 打开对话框(查看)
function openDialogView(title, url, width, height) {
    top.layer.open({
        type: 2,
        area: [width, height],
        title: title,
        maxmin: true, // 开启最大化最小化按钮
        content: url,
        btn: ['关闭'],
        cancel: function (index) {
        }
    });

}


function page(n, s) {// 翻页
    $("#pageNo").val(n);
    $("#pageSize").val(s);
    $("#searchForm").submit();
    $("span.page-size").text(s);
    return false;
}

function search() {// 查询，页码清零
    $("#listForm").submit();
    return false;
}

// 验证
if ($.validator != null) {

    $.extend($.validator.messages, {
        required: '必填',
        email: 'E-mail格式错误',
        url: '网址格式错误',
        date: '日期格式错误',
        dateISO: '日期格式错误',
        pointcard: '信用卡格式错误',
        number: '只允许输入数字',
        digits: '只允许输入零或正整数',
        minlength: $.validator.format('长度不允许小于{0}'),
        maxlength: $.validator.format('长度不允许大于{0}'),
        rangelength: $.validator.format('长度必须在{0}-{1}之间'),
        min: $.validator.format('不允许小于{0}'),
        max: $.validator.format('不允许大于{0}'),
        range: $.validator.format('必须在{0}-{1}之间'),
        accept: '输入后缀错误',
        equalTo: '两次输入不一致',
        remote: '输入错误',
        integer: '只允许输入整数',
        positive: '只允许输入正数',
        negative: '只允许输入负数',
        decimal: '数值超出了允许范围',
        pattern: '格式错误',
        extension: '文件格式错误'
    });

    $.validator.setDefaults({
        errorClass: "fieldError",
        ignore: ".ignore",
        ignoreTitle: true,
        errorPlacement: function (error, element) {
            var fieldSet = element.closest("span.fieldSet");
            if (fieldSet.length > 0) {
                error.appendTo(fieldSet);
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            $(form).find("input:submit").prop("disabled", true);
            $(form).ajaxSubmit({
                dataType: "json",
                success: function (data) {
                    if (data.type == "success") {
                        top.layer.alert(data.content, {icon: 1});
                    } else if (data.type === "warn") {
                        top.layer.alert(data.content, {icon: 3});
                    } else {
                        top.layer.alert(data.content, {icon: 2});
                    }
                    top.layer.close(this.layerIndex);
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);//关闭自身页面
                },
                beforeSubmit: function (arr, $form, options) {
                    this.layerIndex = top.layer.load(0, {shade: [0.5, '#393D49']});
                }
            });
        }
    });

}