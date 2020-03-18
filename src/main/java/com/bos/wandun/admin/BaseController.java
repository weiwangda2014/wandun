package com.bos.wandun.admin;

import com.bos.wandun.Message;
import com.bos.wandun.Setting;
import com.bos.wandun.util.SpringUtils;
import com.bos.wandun.util.SystemUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class BaseController {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 错误消息
     */
    protected static final Message ERROR_MESSAGE = Message.error("失败");

    /**
     * 成功消息
     */
    protected static final Message SUCCESS_MESSAGE = Message.success("成功");


    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;

    /**
     * 前端基础路径
     */
    @Value("${frontPath}")
    protected String frontPath;

    /**
     * 前端URL后缀
     */
    @Value("${urlSuffix}")
    protected String urlSuffix;

    @Value("${name}")
    protected String name;
    @Value("${version}")
    protected String version;
    @Value("${copyright}")
    protected String copyright;
    @Value("${author}")
    protected String author;
    @Value("${logo}")
    protected String logo;
    @Value("${mode}")
    protected String mode;
    @Value("${keywords}")
    protected String keywords;
    @Value("${description}")
    protected String description;
    @Value("${site}")
    protected String site;


    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Message MethodArgumentNotValidHandler(Exception exception) {
        StringBuilder errorMessage = new StringBuilder();
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            BindingResult result = ex.getBindingResult();
            FieldError error = result.getFieldError();
            //String field = error.getField();
            String message = error.getDefaultMessage();
            errorMessage.append("<p><span class=\"text-danger\">" + message + "</span></p>");
        } else if (exception instanceof BindException) {
            BindException ex = (BindException) exception;
            List<FieldError> errors = ex.getFieldErrors();
            for (FieldError error : errors) {
                //String field = error.getField();
                String message = error.getDefaultMessage();
                errorMessage.append("<p><span class=\"text-danger\">" + message + "</span></p>");
            }
        } else if (exception instanceof ValidationException) {
            ValidationException ex = (ValidationException) exception;
            String message = ex.getMessage();
            errorMessage.append("<p><span class=\"text-danger\">" + message + "</span></p>");
        } else if (exception instanceof AuthenticationException) {
            AuthenticationException ex = (AuthenticationException) exception;
            String message = ex.getMessage();
            errorMessage.append("<p><span class=\"text-danger\">" + message + "</span></p>");
        } else if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
            for (ConstraintViolation v : violations) {
                String message = v.getMessageTemplate();
                errorMessage.append("<p><span class=\"text-danger\">" + message + "</span></p>");
            }

        } else {
            String message = exception.getMessage();
            errorMessage.append("<p><span class=\"text-danger\">" + message + "</span></p>");
        }

        logger.error(exception.getMessage(), exception);
        return Message.error(errorMessage.toString());
    }

    protected String renderString(HttpServletResponse response, Object object) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return renderString(response, mapper.writeValueAsString(object), "application/json");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 货币格式化
     *
     * @param amount
     *            金额
     * @param showSign
     *            显示标志
     * @param showUnit
     *            显示单位
     * @return 货币格式化
     */
    protected String currency(BigDecimal amount, boolean showSign, boolean showUnit) {
        Setting setting = SystemUtils.getSetting();
        String price = setting.setScale(amount).toString();
        if (showSign) {
            price = setting.getCurrencySign() + price;
        }
        if (showUnit) {
            price += setting.getCurrencyUnit();
        }
        return price;
    }

    /**
     * 获取国际化消息
     *
     * @param code
     *            代码
     * @param args
     *            参数
     * @return 国际化消息
     */
    protected String message(String code, Object... args) {
        return SpringUtils.getMessage(code, args);
    }

}
