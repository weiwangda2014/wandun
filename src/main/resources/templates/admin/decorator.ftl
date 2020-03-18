<!DOCTYPE html>
<html>
<head>
    <title>
        <sitemesh:write property="title"/>
    </title>
    <#include "admin/css.ftl ">
    <sitemesh:write property="Styles"/>

    <#include "admin/script.ftl">

</head>
<body>
<sitemesh:write property="body"/>


<sitemesh:write property="add"/>
<sitemesh:write property="del"/>
<sitemesh:write property="edit"/>
<sitemesh:write property="export"/>
<sitemesh:write property="import"/>
<sitemesh:write property="refresh"/>

<sitemesh:write property="printRow"/>
<sitemesh:write property="sortColumn"/>
<sitemesh:write property="treeSelect" />
<sitemesh:write property="ckeditor"/>
<sitemesh:write property="ckfinder"/>
<sitemesh:write property="gridSelect"/>
<sitemesh:write property="iconSelect"/>

<sitemesh:write property="JavaScripts"/>
</body>
</html>