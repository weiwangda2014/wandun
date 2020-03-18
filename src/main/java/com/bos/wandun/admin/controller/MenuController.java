package com.bos.wandun.admin.controller;


import com.bos.wandun.*;
import com.bos.wandun.admin.BaseController;
import com.bos.wandun.entity.Menu;
import com.bos.wandun.service.MenuService;
import com.bos.wandun.util.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/menus")
public class MenuController extends BaseController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @ModelAttribute("menu")
    public Menu get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return menuService.find(id);
        } else {
            return new Menu();
        }
    }

    @RequestMapping("index")
    public String index(Pageable pageable, ModelMap model) {

        List<Filter> filters = new ArrayList<>();

        Filter filter = new Filter();

        filter.setProperty("parent.id");
        filter.setValue(null);
        filter.setOperator(Filter.Operator.eq);
        filters.add(filter);
        pageable.setFilters(filters);

        model.addAttribute("page", menuService.findPage(pageable));
        return "admin/menus/index";
    }


    @RequestMapping(value = "form")
    public String form(Menu menu, Model model) {
        if (menu.getParent() == null || menu.getParent().getId() == null) {
            menu.setParent(menuService.find(null));
        }
        menu.setParent(menuService.find(menu.getParent().getId()));
        // 获取排序号，最末节点排序号+30
        if (menu.getId() != null) {
            List<Menu> list = Lists.newArrayList();
            List<Menu> all = menuService.findAll();
            if (list.size() > 0) {
                menu.setSort(list.get(list.size() - 1).getSort() + 30);
            }
        }
        model.addAttribute("menu", menu);
        return "admin/menus/form";
    }

    /**
     * 保存
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public Message save(Menu menu) {
        if (!UserUtils.getUser().isAdmin()) {
            return Message.warn("越权操作，只有超级管理员才能添加或修改数据！");
        }
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        if (menu.getId() == null) {
            menuService.save(menu);
        } else {
            menuService.update(menu);
        }

        return Message.success("保存菜单'" + menu.getName() + "'成功");
    }


    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Message delete(Long[] ids) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        menuService.delete(ids);
        return SUCCESS_MESSAGE;
    }

    @RequestMapping(value = "tree")
    public String tree() {
        return "modules/sys/menuTree";
    }


    @RequestMapping(value = "treeselect")
    public String treeselect(String parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "modules/sys/menuTreeselect";
    }

    /**
     * 批量修改菜单排序
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateSort")
    public Message updateSort(Long[] ids, Integer[] sorts) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        for (int i = 0; i < ids.length; i++) {
            Menu menu = menuService.find(ids[i]);
            menu.setSort(sorts[i]);
            menuService.update(menu);
        }
        return Message.success("保存菜单排序成功!");
    }

    /**
     * isShowHide是否显示隐藏菜单
     *
     * @param extId
     * @param isShowHide
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Menu> list = menuService.findAll();
        for (int i = 0; i < list.size(); i++) {
            Menu e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getPath().indexOf("," + extId + ",") == -1)) {
                if (isShowHide != null && isShowHide.equals("0") && e.getDisplay().equals("0")) {
                    continue;
                }
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", (e.getParent() != null ? e.getParent().getId() : null));
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }
}
