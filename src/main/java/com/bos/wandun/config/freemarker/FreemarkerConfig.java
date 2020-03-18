package com.bos.wandun.config.freemarker;

import com.bos.wandun.Global;
import com.bos.wandun.Setting;
import com.bos.wandun.template.directive.MenuDirective;
import com.bos.wandun.template.directive.PaginationDirective;
import com.bos.wandun.template.method.*;
import com.bos.wandun.util.SystemUtils;
import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Configuration
public class FreemarkerConfig {

    final freemarker.template.Configuration configuration;
    final ServletContext servletContext;

    public FreemarkerConfig(freemarker.template.Configuration configuration, ServletContext servletContext) {
        this.configuration = configuration;
        this.servletContext = servletContext;
    }


    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {

        /**
         * 加载 Setting
         */
        Setting setting = SystemUtils.getSetting();
        setting.setSmtpPassword(null);
        setting.setKuaidi100Key(null);
        setting.setCnzzPassword(null);
        setting.setSmsKey(null);
        ProxyFactory proxyFactory = new ProxyFactory(setting);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
            if (StringUtils.startsWith(method.getName(), "set")) {
                throw new UnsupportedOperationException("Operation not supported");
            }
        });

        configuration.setSharedVariable("setting", proxyFactory.getProxy());
        configuration.setSharedVariable("locale", setting.getLocale());
        configuration.setSharedVariable("theme", setting.getTheme());


        /**
         * Shiro标签
         */
        configuration.setSharedVariable("shiro", new ShiroTags());


        configuration.setSharedVariable("ctx", servletContext.getContextPath());

        configuration.setSharedVariable("static", servletContext.getContextPath() + "/static");

        configuration.setSharedVariable("admin", servletContext.getContextPath() + Global.getAdminPath());


        configuration.setSharedVariable("menus", new MenuDirective());

        configuration.setSharedVariable("pagination", new PaginationDirective());

        configuration.setSharedVariable("message", new MessageMethod());

        configuration.setSharedVariable("dictJson", new DictJsonMethod());
        configuration.setSharedVariable("dictList", new DictListMethod());


        configuration.setSharedVariable("dictlabel", new DictLabelMethod());

        configuration.setSharedVariable("abbreviate", new AbbreviateMethod());
    }
}