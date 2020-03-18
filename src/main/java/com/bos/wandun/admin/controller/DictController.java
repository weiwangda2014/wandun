package com.bos.wandun.admin.controller;

import com.bos.wandun.Global;
import com.bos.wandun.Message;
import com.bos.wandun.Pageable;
import com.bos.wandun.admin.BaseController;
import com.bos.wandun.entity.Dict;
import com.bos.wandun.service.DictService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/dicts")
public class DictController extends BaseController {

    private final DictService dictService;

    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @ModelAttribute
    public Dict get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return dictService.find(id);
        } else {
            return new Dict();
        }
    }

    /**
     * 列表
     */
    @RequiresPermissions("admin:dict:list")
    @RequestMapping(value = {"index", ""})
    public String index(Pageable pageable, ModelMap model) {
        model.addAttribute("page", dictService.findPage(pageable));
        return "admin/dicts/index";
    }

    @RequiresPermissions(value = {"admin:dict:view", "admin:dict:add", "admin:dict:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Dict dict, Model model) {
        model.addAttribute("dict", dict);
        return "admin/dicts/form";
    }


    @RequiresPermissions(value = {"admin:dict:add", "admin:dict:edit"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "save")
    public Message save(Dict dict) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        dictService.save(dict);
        return Message.success("保存字典'" + dict.getLabel() + "'成功");
    }


    /**
     * 删除
     *
     * @return
     */

    @RequiresPermissions("admin:dict:del")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Message delete(Long[] ids) {
        if (Global.isDemoMode()) {
           return Message.warn("演示模式，不允许操作！");
        }
        dictService.delete(ids);
        return Message.success("删除字典成功");
    }

    /**
     * 验证字典名是否有效
     *
     * @param label
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkLabel")
    public String checkLabel(String label) {
        if (label != null) {
            return "true";
        } else if (label != null && dictService.findByLabel(label) == null) {
            return "true";
        }
        return "false";
    }


    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String type) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Dict> list = dictService.findByType(type);
        for (int i = 0; i < list.size(); i++) {
            Dict e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            map.put("pId", e.getParentId());
            map.put("name", StringUtils.replace(e.getLabel(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "listData")
    public List<Dict> listData(@RequestParam(required = false) String type) {
        return dictService.findByType(type);
    }
}
