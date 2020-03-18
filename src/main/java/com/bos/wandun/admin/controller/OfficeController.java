package com.bos.wandun.admin.controller;

import com.bos.wandun.Filter;
import com.bos.wandun.Global;
import com.bos.wandun.Message;
import com.bos.wandun.Pageable;
import com.bos.wandun.admin.BaseController;
import com.bos.wandun.entity.Office;
import com.bos.wandun.entity.User;
import com.bos.wandun.service.AreaService;
import com.bos.wandun.service.OfficeService;
import com.bos.wandun.service.UserService;
import com.bos.wandun.util.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/offices")
public class OfficeController extends BaseController {

    private final OfficeService officeService;
    private final AreaService areaService;
    private final UserService userService;

    public OfficeController(OfficeService officeService, AreaService areaService, UserService userService) {
        this.officeService = officeService;
        this.areaService = areaService;
        this.userService = userService;
    }

    @ModelAttribute
    public Office get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return officeService.find(id);
        } else {
            return new Office();
        }
    }

    @RequiresPermissions("admin:office:index")
    @RequestMapping("index")
    public String index(Pageable pageable, ModelMap model) {
        List<Filter> filters = new ArrayList<>();
        Filter filter = new Filter();
        filter.setProperty("parent.id");
        filter.setValue(null);
        filter.setOperator(Filter.Operator.eq);
        filters.add(filter);
        pageable.setFilters(filters);
        model.addAttribute("page", officeService.findPage(pageable));
        return "admin/offices/index";
    }


    /**
     * 列表
     */
    @RequiresPermissions("admin:office:index")
    @RequestMapping("list")
    public String list(Office office, Pageable pageable, ModelMap model) {
        List<Filter> filters = new ArrayList<>();
        if (office.getId() != null) {
            Filter filter = new Filter();
            filter.setProperty("parent.id");
            filter.setValue(office.getId());
            filter.setOperator(Filter.Operator.eq);
            filters.add(filter);
            pageable.setFilters(filters);
        }
        if (office.getId() == null) {
            Filter filter = new Filter();
            filter.setProperty("parent.id");
            filter.setValue(null);
            filter.setOperator(Filter.Operator.eq);
            filters.add(filter);
            pageable.setFilters(filters);
        }
        model.addAttribute("page", officeService.findPage(pageable));
        return "admin/offices/list";
    }

    @RequiresPermissions(value = {"admin:office:view", "admin:office:add", "admin:office:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Office office, Model model) {
        User user = UserUtils.getUser();
        if (office.getParent() == null || office.getParent().getId() == null) {
            office.setParent(user.getOffice());
        }
        if (office.getArea() == null) {
            office.setArea(user.getOffice().getArea());
        }
        // 自动获取排序号
        if (office.getId() != null && office.getParent() != null) {
            int size = 0;
            List<Office> list = officeService.findAll();
            for (int i = 0; i < list.size(); i++) {
                Office e = list.get(i);
                if (e.getParent() != null && e.getParent().getId() != null
                        && e.getParent().getId().equals(office.getParent().getId())) {
                    size++;
                }
            }
            office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size + 1 : 1), 3, "0"));
        }
        model.addAttribute("office", office);
        return "admin/offices/form";
    }

    @ResponseBody
    @RequiresPermissions(value = {"admin:office:add", "admin:office:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public Message save(Office office) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        if (office.getArea() != null) {
            office.setArea(areaService.find(office.getArea().getId()));
        }
        if (office.getParent() != null) {
            office.setParent(officeService.find(office.getParent().getId()));
        }
        if (office.getPrimaryPerson() != null && office.getPrimaryPerson().getId() != null) {
            office.setPrimaryPerson(userService.find(office.getPrimaryPerson().getId()));
        }
        if (office.getDeputyPerson() != null && office.getDeputyPerson().getId() != null) {
            office.setDeputyPerson(userService.find(office.getDeputyPerson().getId()));
        }
        if (office.getId() == null) {
            officeService.save(office);
        } else {
            officeService.update(office);
        }
        return Message.success("保存机构'" + office.getName() + "'成功");
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequiresPermissions("admin:office:del")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Message delete(Long[] ids) {
        officeService.delete(ids);
        return SUCCESS_MESSAGE;
    }


    /**
     * 获取机构JSON数据。
     *
     * @param extId 排除的ID
     * @param type  类型（1：公司；2：部门/小组/其它：3：用户）
     * @param grade 显示级别
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Office> list = officeService.findByisAll(isAll);

        for (int i = 0; i < list.size(); i++) {
            Office e = list.get(i);
            if ((StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getPath().indexOf("," + extId + ",") == -1))
                    && (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
                    && (grade == null || (grade != null && e.getGrade() <= grade.intValue()))
                    && Global.YES.equals(e.getUseable())) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", (e.getParent() != null ? e.getParent().getId() : null));
                map.put("pIds", e.getPath());
                map.put("name", e.getName());
                if (type != null && "3".equals(type)) {
                    map.put("isParent", true);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }
}
