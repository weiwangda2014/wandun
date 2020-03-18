package com.bos.wandun.admin.controller;

import com.bos.wandun.Message;
import com.bos.wandun.Pageable;
import com.bos.wandun.admin.BaseController;
import com.bos.wandun.entity.Log;
import com.bos.wandun.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("${adminPath}/logs")
public class LogController extends BaseController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 列表
     */
    @RequestMapping("index")
    public String index(Pageable pageable, ModelMap model) {
        model.addAttribute("page", logService.findPage(pageable));
        return "admin/logs/index";
    }


    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Message delete(Long[] ids) {
        logService.delete(ids);
        return SUCCESS_MESSAGE;
    }


    /**
     * 清空
     */
    @ResponseBody
    @RequestMapping(value = "clear", method = RequestMethod.POST)
    public Message clear() {
        logService.clear();
        return SUCCESS_MESSAGE;
    }
}
