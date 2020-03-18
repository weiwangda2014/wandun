<#macro button  label="">
    <button id="clearButton"  type="button" class="btn btn-sm" data-toggle="tooltip"
            data-placement="left"
            title="${message("admin.common.clear")}">
        <i class="fa fa-plus"></i> ${(label?has_content)? string(label,message("admin.common.clear"))}
    </button>
    <clear>

    </clear>
</#macro>