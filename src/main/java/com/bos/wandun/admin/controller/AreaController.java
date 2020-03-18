package com.bos.wandun.admin.controller;


import com.bos.wandun.*;
import com.bos.wandun.admin.BaseController;
import com.bos.wandun.entity.Area;
import com.bos.wandun.service.AreaService;
import com.bos.wandun.util.UserUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/areas")
public class AreaController extends BaseController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @ModelAttribute("area")
    public Area get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return areaService.find(id);
        } else {
            return new Area();
        }
    }

    @RequiresPermissions("admin:area:list")
    @RequestMapping("index")
    public String index(Pageable pageable, ModelMap model) {
        List<Filter> filters = new ArrayList<>();

        Filter filter = new Filter();

        filter.setProperty("parent.id");
        filter.setValue(null);
        filter.setOperator(Filter.Operator.eq);
        filters.add(filter);
        pageable.setFilters(filters);
        model.addAttribute("page", areaService.findPage(pageable));
        return "admin/areas/index";
    }

    @RequiresPermissions(value = {"admin:area:view", "admin:area:add", "admin:area:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Area area, Model model) {
        if (area.getParent() == null || area.getParent().getId() == null) {
            area.setParent(UserUtils.getUser().getOffice().getArea());
        } else {
            area.setParent(areaService.find(area.getParent().getId()));
        }

        model.addAttribute("area", area);
        return "admin/areas/form";
    }

    @RequiresPermissions(value = {"admin:area:add", "admin:area:edit"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "save")
    public Message save(Area area) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        Long id = area.getParent().getId();
        if (id != null) {
            Area parent = areaService.find(id);
            area.setParent(parent);
        }

        if (area.getId() == null) {
            areaService.save(area);
        } else {
            areaService.update(area);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除
     */
    @RequiresPermissions("admin:area:del")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Message delete(Long[] ids) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        areaService.delete(ids);
        return SUCCESS_MESSAGE;
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Area> list = areaService.findAll();
        for (int i = 0; i < list.size(); i++) {
            Area e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getPath().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", (e.getParent() != null ? e.getParent().getId() : null));
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "data")
    public List<Area> data(@RequestParam(required = false) Long id) {
        List<Area> list = areaService.findChildren(id);
        return list;
    }
}
