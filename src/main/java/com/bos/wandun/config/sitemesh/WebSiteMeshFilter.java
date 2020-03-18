package com.bos.wandun.config.sitemesh;

import com.bos.wandun.config.sitemesh.ExpandTagRuleBundle;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle;

public class WebSiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        final String decoratorPath = "/admin/decorator";
        builder.addDecoratorPath("/admin/**", decoratorPath)
                .addExcludedPath("/admin/login").addExcludedPath("/admin/ie.html")
                .addExcludedPath("/admin/index").addExcludedPath("/static/js/common.js")
                .addTagRuleBundle(new ExpandTagRuleBundle());

        builder.addTagRuleBundle(new Sm2TagRuleBundle());
        builder.addTagRuleBundle(new Sm2TagRuleBundle());
    }
}