/*
 *
 *
 *
 */
package com.bos.wandun.template.method;

import com.bos.wandun.util.FreeMarkerUtils;
import com.bos.wandun.util.SpringUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模板方法 - 多语言
 *
 * @author Hello King
 * @version 4.0
 */
@Component("messageMethod")
public class MessageMethod implements TemplateMethodModelEx {

    /**
     * 执行
     *
     * @param arguments 参数
     * @return 结果
     */
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        String code = FreeMarkerUtils.getArgument(0, String.class, arguments);
        if (StringUtils.isNotEmpty(code)) {
            String message;
            if (arguments.size() > 1) {
                Object[] args = new Object[arguments.size() - 1];
                for (int i = 1; i < arguments.size(); i++) {
                    Object argument = arguments.get(i);
                    if (argument != null && argument instanceof TemplateModel) {
                        args[i - 1] = DeepUnwrap.unwrap((TemplateModel) argument);
                    } else {
                        args[i - 1] = argument;
                    }
                }
                message = SpringUtils.getMessage(code, args);
            } else {
                message = SpringUtils.getMessage(code);
            }
            return new SimpleScalar(message);
        }
        return null;
    }

}