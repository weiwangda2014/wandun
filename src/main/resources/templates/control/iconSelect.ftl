<#macro select id="" name="" value="">
    <i id="${id}Icon" class="icon-${(value?has_content) ?string(value,'')}"></i>&nbsp;<span id="${id}IconLabel">${(value?has_content) ?string(value,'无')}</span>&nbsp;
    <input id="${id}" name="${name}" type="hidden" value="${value}"/><a id="${id}Button" href="javascript:" class="btn btn-primary">选择</a>&nbsp;&nbsp;
    <input id="${id}clear" class="btn btn-default" type="button" value="清除" onclick="clear()"/>
    <iconSelect>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#${id}Button").click(function () {
                    top.layer.open({
                        type: 2,
                        title: "选择图标",
                        area: ['700px', $(top.document).height() - 180 + "px"],
                        content: '${admin}/tag/iconselect?value="+$("#${id}").val()',
                        btn: ['确定', '关闭'],
                        yes: function (index, layero) { //或者使用btn1
                            var icon = layero.find("iframe")[0].contentWindow.$("#icon").val();
                            $("#${id}Icon").attr("class", "fa " + icon);
                            $("#${id}IconLabel").text(icon);
                            $("#${id}").val(icon);
                            top.layer.close(index);
                        }, cancel: function (index) { //或者使用btn2

                        }
                    });
                });
                $("#${id}clear").click(function () {
                    $("#${id}Icon").attr("class", "icon- hide");
                    $("#${id}IconLabel").text("无");
                    $("#${id}").val("");
                });
            });
        </script>
    </iconSelect>
</#macro>