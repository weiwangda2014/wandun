/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bos.wandun.util;

import com.bos.wandun.entity.Log;
import com.bos.wandun.entity.User;
import com.bos.wandun.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 字典工具类
 *
 * @author ThinkGem
 * @version 2014-11-7
 */
public class LogUtils {

    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

    @Autowired
    private static LogService logService = SpringContextHolder.getBean(LogService.class);

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, String title) {
        saveLog(request, null, null, title);
    }

    public static void saveLog(Log log) {
        logService.save(log);
    }

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title) {
        User user = UserUtils.getUser();
        if (user != null && user.getId() != null) {
            Log log = new Log();
            log.setTitle(title);
            log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
            log.setRemoteAddr(StringUtils.getRemoteAddr(request));
            log.setUserAgent(request.getHeader("user-agent"));
            log.setRequestUri(request.getRequestURI());

            ObjectMapper mapper = new ObjectMapper();
            String parameter = null;
            try {
                parameter = mapper.writeValueAsString(request.getParameterMap());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            log.setParams(parameter);
            log.setMethod(request.getMethod());
            log.setCreateBy(user.getId());
            log.setUpdateBy(user.getId());
            log.setUpdateDate(new Date());
            log.setCreateDate(new Date());
            // 异步保存日志
            try {
                InterceptorLogEntity entiry = new InterceptorLogEntity(log, handler, ex);
                LogThread.interceptorLogQueue.put(entiry);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
