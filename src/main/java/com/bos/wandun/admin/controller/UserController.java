package com.bos.wandun.admin.controller;

import com.bos.wandun.*;
import com.bos.wandun.admin.BaseController;
import com.bos.wandun.entity.Office;
import com.bos.wandun.entity.User;
import com.bos.wandun.service.OfficeService;
import com.bos.wandun.service.RoleService;
import com.bos.wandun.service.UserService;
import com.bos.wandun.util.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final OfficeService officeService;
    private final RoleService roleService;

    public UserController(UserService userService, OfficeService officeService, RoleService roleService) {
        this.userService = userService;
        this.officeService = officeService;
        this.roleService = roleService;
    }


    @RequestMapping(value = {"index"})
    public String index() {
        return "admin/users/index";
    }

    @ModelAttribute
    public User get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return userService.find(id);
        } else {
            return new User();
        }
    }

    /**
     * 列表
     */
    @RequestMapping("list")
    public String list(User user, Pageable pageable, ModelMap model) {
        List<Filter> filters = new ArrayList<>();
        if (user != null && user.getOffice() != null && user.getOffice().getId() != null) {
            Filter filter = new Filter();
            filter.setProperty("office.id");
            filter.setValue(user.getOffice().getId());
            filter.setOperator(Filter.Operator.eq);
            filters.add(filter);
            pageable.setFilters(filters);
        }

        model.addAttribute("page", userService.findPage(pageable));
        return "admin/users/list";
    }


    @RequestMapping(value = "form")
    public String form(User user, Model model) {
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/users/form";
    }


    @ResponseBody
    @RequestMapping(value = "save")
    public Message save(User user) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }

        if (user.getOffice() != null && user.getOffice().getId() != null) {
            Office office = officeService.find(user.getOffice().getId());
            user.setOffice(office);
        }
        if (user.getCompany() != null && user.getCompany().getId() != null) {
            Office company = officeService.find(user.getCompany().getId());
            user.setCompany(company);
        }

        // 保存用户信息

        if (user.getId() == null) {
            if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
                return Message.warn("保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            }
            userService.save(user);
        } else {
            userService.update(user);
        }
        return Message.success("保存用户'" + user.getLoginName() + "'成功");

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Message delete(Long[] ids) {
        userService.delete(ids);
        return SUCCESS_MESSAGE;
    }

    @RequiresPermissions("admin:user:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(User user, HttpServletResponse response) {

        return "";
    }

    /**
     * 导入用户数据
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequiresPermissions("admin:user:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public Message importFile(MultipartFile file) {
        return Message.success("保存用户成功");
    }

    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody

    @RequestMapping(value = "checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
            return "true";
        } else if (loginName != null && userService.findUserByName(loginName) == null) {
            return "true";
        }
        return "false";
    }


    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) Long officeId) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<User> list = userService.findUserByOfficeId(officeId);
        for (int i = 0; i < list.size(); i++) {
            User e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "u_" + e.getId());
            map.put("pId", officeId);
            map.put("name", StringUtils.replace(e.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }
}
