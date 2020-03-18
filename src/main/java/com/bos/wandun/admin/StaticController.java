package com.bos.wandun.admin;

import com.bos.wandun.Setting;
import com.bos.wandun.util.SystemUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/static/js/")
public class StaticController extends BaseController {
    @RequestMapping(value = "common.js", method = RequestMethod.GET)
    public String common(Model model, HttpServletResponse response) {
        Setting setting = SystemUtils.getSetting();
        model.addAttribute("setting", setting);
        response.addHeader("Content-Type", "application/javascript; charset=UTF-8");
        return "admin/js/common";
    }
}