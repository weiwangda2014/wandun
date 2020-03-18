package com.bos.wandun.template.method;

import com.bos.wandun.entity.Dict;
import com.bos.wandun.util.DictUtils;
import com.bos.wandun.util.FreeMarkerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("dictListMethod")
public class DictListMethod implements TemplateMethodModelEx {


    public Object exec(List arguments) throws TemplateModelException {
        String type = FreeMarkerUtils.getArgument(0, String.class, arguments);
        if (StringUtils.isNotBlank(type)) {
            List<Dict> dicts = DictUtils.getDictList(type);
            return dicts;
        }
        return null;
    }
}
