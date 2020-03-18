package com.bos.wandun.template.method;

import com.bos.wandun.util.DictUtils;
import com.bos.wandun.util.FreeMarkerUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("dictLabelMethod")
public class DictLabelMethod implements TemplateMethodModelEx {

    public Object exec(List arguments) throws TemplateModelException {
        String value = FreeMarkerUtils.getArgument(0, String.class, arguments);
        String type = FreeMarkerUtils.getArgument(1, String.class, arguments);
        String defaultValue = FreeMarkerUtils.getArgument(2, String.class, arguments);

        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            String label = DictUtils.getDictLabel(value, type, defaultValue);
            return new SimpleScalar(label);
        }
        return null;
    }
}
