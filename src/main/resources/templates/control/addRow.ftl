<#macro button id="" url="" title="" width="" height="" target="" label="">
    <button type="button" class="btn btn-sm" data-toggle="tooltip" data-placement="left" onclick="add()" title="${message("admin.common.add")}">
        <i class="fa fa-plus"></i> ${(label?has_content)? string(label,message("admin.common.add"))}
    </button>
    <add>
        <script type="text/javascript">
            function add() {
                openDialog("新增" + '${title}', "${url}", "${(width?has_content)?string(width,'100%')}", "${(height?has_content)?string(width,'100%')}", "${target}");
            }
        </script>
    </add>
</#macro>