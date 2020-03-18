package com.bos.wandun.config.sitemesh;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * Sitemesh3中自定义Tag
 */
public class ExpandTagRuleBundle implements TagRuleBundle {

    private static final String TAG_SCRIPTS = "JavaScripts";
    private static final String TAG_STYLES = "Styles";

    private static final String TAG_addRow = "add";
    private static final String TAG_addRowBatch = "addRowBatch";
    private static final String TAG_addRowPrint = "addRowPrint";
    private static final String TAG_batchAddRow = "batchAddRow";
    private static final String TAG_batchUpdateRow = "batchUpdateRow";
    private static final String TAG_delRow = "del";
    private static final String TAG_editRow = "edit";
    private static final String TAG_exportExcel = "export";
    private static final String TAG_importExcel = "import";
    private static final String TAG_refresh = "refresh";
    private static final String TAG_page = "page";
    private static final String TAG_printRow = "printRow";
    private static final String TAG_sortColumn = "sortColumn";

    private static final String TAG_treeSelect = "treeSelect";
    private static final String TAG_ckeditor = "ckeditor";
    private static final String TAG_ckfinder = "ckfinder";
    private static final String TAG_gridSelect = "gridSelect";
    private static final String TAG_iconSelect = "iconSelect";


    @Override
    public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        defaultState.addRule(TAG_SCRIPTS, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_SCRIPTS), false));
        defaultState.addRule(TAG_STYLES, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_STYLES), false));
        defaultState.addRule(TAG_addRow, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_addRow), false));
        defaultState.addRule(TAG_addRowBatch, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_addRowBatch), false));
        defaultState.addRule(TAG_addRowPrint, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_addRowPrint), false));
        defaultState.addRule(TAG_batchAddRow, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_batchAddRow), false));
        defaultState.addRule(TAG_batchUpdateRow, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_batchUpdateRow), false));
        defaultState.addRule(TAG_delRow, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_delRow), false));
        defaultState.addRule(TAG_editRow, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_editRow), false));
        defaultState.addRule(TAG_exportExcel, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_exportExcel), false));
        defaultState.addRule(TAG_importExcel, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_importExcel), false));
        defaultState.addRule(TAG_page, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_page), false));
        defaultState.addRule(TAG_printRow, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_printRow), false));
        defaultState.addRule(TAG_sortColumn, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_sortColumn), false));
        defaultState.addRule(TAG_refresh, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_refresh), false));
        defaultState.addRule(TAG_treeSelect, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_treeSelect), false));
        defaultState.addRule(TAG_ckeditor, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_ckeditor), false));
        defaultState.addRule(TAG_ckfinder, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_ckfinder), false));
        defaultState.addRule(TAG_gridSelect, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_gridSelect), false));
        defaultState.addRule(TAG_iconSelect, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(TAG_iconSelect), false));
    }

    @Override
    public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

    }
}