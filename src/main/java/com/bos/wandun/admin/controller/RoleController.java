package com.bos.wandun.admin.controller;

import com.bos.wandun.Global;
import com.bos.wandun.Message;
import com.bos.wandun.Pageable;
import com.bos.wandun.admin.BaseController;
import com.bos.wandun.entity.Office;
import com.bos.wandun.entity.Role;
import com.bos.wandun.entity.User;
import com.bos.wandun.service.MenuService;
import com.bos.wandun.service.OfficeService;
import com.bos.wandun.service.RoleService;
import com.bos.wandun.service.UserService;
import com.bos.wandun.util.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${adminPath}/roles")
public class RoleController extends BaseController {

    private final RoleService roleService;
    private final UserService userService;
    private final MenuService menuService;
    private final OfficeService officeService;

    public RoleController(RoleService roleService, UserService userService, MenuService menuService, OfficeService officeService) {
        this.roleService = roleService;
        this.userService = userService;
        this.menuService = menuService;
        this.officeService = officeService;
    }

    @ModelAttribute("role")
    public Role get(@RequestParam(required = false) Long id) {
        if (id != null) {
            return roleService.find(id);
        } else {
            return new Role();
        }
    }

    /**
     * 列表
     */
    @RequestMapping("index")
    public String index(Pageable pageable, ModelMap model) {
        model.addAttribute("page", roleService.findPage(pageable));
        return "admin/roles/index";
    }


    @RequestMapping(value = "form")
    public String form(Role role, Model model) {
        if (role.getOffice() == null) {
            role.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("role", role);
        model.addAttribute("menuList", menuService.findAll());
        model.addAttribute("officeList", officeService.findAll());
        return "admin/roles/form";
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("save")
    public Message save(Role role) {

        if (!UserUtils.getUser().isAdmin() && role.getDataScope().equals(Global.YES)) {
            return Message.warn("越权操作，只有超级管理员才能修改此数据！");
        }
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }

        Office office = role.getOffice();
        if (office != null) {
            if (office.getId() != null) {
                office = officeService.find(office.getId());
            }
        }
        role.setOffice(office);
        if (role.getId() == null) {
            if (!"true".equals(checkName(role.getOldName(), role.getName()))) {
                return Message.warn("保存角色'" + role.getName() + "'失败, 角色名已存在");
            }
            if (!"true".equals(checkEnname(role.getOldEnname(), role.getEnname()))) {
                return Message.warn("保存角色" + role.getName() + "失败, 英文名已存在");
            }
            roleService.save(role);
        } else {
            roleService.update(role);
        }
        return SUCCESS_MESSAGE;
    }

    /**
     * 更新
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Message update(Role role) {
        roleService.update(role);
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Message delete(Long[] ids) {
        roleService.delete(ids);
        return SUCCESS_MESSAGE;
    }

    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public String auth(Role role, Model model) {
        if (role.getOffice() == null) {
            role.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("role", role);
        model.addAttribute("menuList", menuService.findAll());
        model.addAttribute("officeList", officeService.findAll());
        return "admin/roles/roleAuth";
    }


    /**
     * 更新
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public Message auth(Role role, Long[] ids) {
        role.setMenus(new HashSet<>(menuService.findList(ids)));
        roleService.update(role);
        return SUCCESS_MESSAGE;
    }

    /**
     * 角色分配页面
     *
     * @param role
     * @param model
     * @return
     */

    @RequestMapping(value = "assign")
    public String assign(Role role, Model model) {
        Set<User> userList = roleService.find(role.getId()).getUsers();
        model.addAttribute("userList", userList);
        return "admin/roles/roleAssign";
    }

    /**
     * 角色分配 -- 从角色中移除用户
     *
     * @param uid
     * @param rid
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "outrole", method = RequestMethod.POST)
    public Message outrole(Long uid, Long rid) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        Role role = roleService.find(rid);
        User user = userService.find(uid);
        if (UserUtils.getUser().getId().equals(uid)) {
            return Message.warn("无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
        } else {
            if (user.getRoles().size() <= 1) {
                return Message.warn("用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。");
            } else {

                user.getRoles().remove(role);
                userService.update(user);

                Boolean contains = user.getRoles().contains(role);
                if (contains) {
                    return Message.error("用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
                }
                return Message.success("用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
            }
        }
    }


    /**
     * 角色分配 -- 打开角色分配对话框
     *
     * @param role
     * @param model
     * @return
     */

    @RequestMapping(value = "usertorole")
    public String selectUserToRole(Role role, Model model) {
        Set<User> userList = roleService.find(role.getId()).getUsers();
        model.addAttribute("role", role);
        model.addAttribute("userList", userList);
        model.addAttribute("selectIds", userList.stream().map(user -> String.valueOf(user.getId()))
                .collect(Collectors.joining(",")));
        model.addAttribute("officeList", officeService.findAll());
        return "admin/roles/selectUserToRole";
    }


    /**
     * 角色分配
     *
     * @param role
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "assignrole", method = RequestMethod.POST)
    public Message assignRole(Role role, Long[] ids) {
        if (Global.isDemoMode()) {
            return Message.warn("演示模式，不允许操作！");
        }
        StringBuilder msg = new StringBuilder();
        int newNum = 0;

        List<User> users = userService.findList(ids);
        if (CollectionUtils.isNotEmpty(users)) {
            for (User user : users) {
                user.getRoles().add(role);
                userService.update(user);
                msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
                newNum++;
            }
        }
        return Message.success("已成功分配 " + newNum + " 个用户" + msg);
    }

    /**
     * 角色分配 -- 根据部门编号获取用户列表
     *
     * @param officeId
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "users")
    public List<Map<String, Object>> users(Long officeId) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Set<User> page = officeService.find(officeId).getUsers();
        for (User user : page) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", user.getId());
            map.put("pId", 0);
            map.put("name", user.getName());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 验证角色名是否有效
     *
     * @param oldName
     * @param name
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && roleService.findRoleByName(name) == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 验证角色英文名是否有效
     *
     * @param oldEnname
     * @param enname
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "checkEnname")
    public String checkEnname(String oldEnname, String enname) {
        if (enname != null && enname.equals(oldEnname)) {
            return "true";
        } else if (enname != null && roleService.findRoleByEnname(enname) == null) {
            return "true";
        }
        return "false";
    }
}
