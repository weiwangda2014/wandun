package com.bos.wandun.template.directive;

import com.bos.wandun.entity.Menu;
import com.bos.wandun.util.UserUtils;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class MenuDirective implements TemplateDirectiveModel {


    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        List<Menu> menus = UserUtils.getMenuList();
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.getVersion());
        List<Menu> list = MenuTree.buildTree(new HashSet<>(menus));
        environment.setVariable("menus", builder.build().wrap(list));
        templateDirectiveBody.render(environment.getOut());

    }


    static class MenuTree {

        //建立树形结构
        public static List<Menu> buildTree(Set<Menu> menuList) {
            List<Menu> treeMenus = new ArrayList<>();
            for (Menu menuNode : getRootNode(menuList)) {
                menuNode = buildChildTree(menuList, menuNode);
                treeMenus.add(menuNode);
            }
            return treeMenus;
        }

        //递归，建立子树形结构
        private static Menu buildChildTree(Set<Menu> menuList, Menu pNode) {
            Set<Menu> childMenus = new HashSet<>();
            for (Menu menuNode : menuList) {
                if (menuNode.getParent() != null && menuNode.getParent().getId().equals(pNode.getId())) {
                    childMenus.add(buildChildTree(menuList, menuNode));
                    pNode.setChildren(childMenus);
                }
            }

            return pNode;
        }

        //获取根节点
        private static List<Menu> getRootNode(Set<Menu> menuList) {
            List<Menu> rootMenuLists = new ArrayList<Menu>();
            for (Menu menuNode : menuList) {
                if (menuNode.getParent() != null && menuNode.getParent().getId() == 1L) {
                    rootMenuLists.add(menuNode);
                }
            }
            return rootMenuLists;
        }
    }
}