package com.bos.wandun.dao.impl;

import com.bos.wandun.dao.AreaDao;
import com.bos.wandun.dao.MenuDao;
import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.Menu;
import com.bos.wandun.entity.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;


@Repository("menuDaoImpl")
public class MenuDaoImpl extends BaseDaoImpl<Menu, Long> implements MenuDao {
    @Override
    public List<Menu> findByUserId(Long id) {
        if (id == null) {
            return null;
        }
        try {
            String jpql = "SELECT menu FROM Menu menu JOIN menu.roles role WHERE  role.id IN (SELECT r.id FROM User u JOIN u.roles r WHERE u.id = :id)";
            return entityManager.createQuery(jpql, Menu.class).setParameter("id", id).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Menu> findByParentIdsLike(Menu m) {
        return null;
    }

    @Override
    public List<Menu> findChildren(Menu menu, boolean recursive, Integer count) {
        TypedQuery<Menu> query;
        if (recursive) {
            if (menu != null) {
                String jpql = "select menu from Menu menu where menu.path like :treePath order by menu.grade asc, menu.sort asc";
                query = entityManager.createQuery(jpql, Menu.class).setParameter("treePath", "%" + Menu.TREE_PATH_SEPARATOR + menu.getId() + Menu.TREE_PATH_SEPARATOR + "%");
            } else {
                String jpql = "select menu from Menu menu order by menu.grade asc, menu.sort asc";
                query = entityManager.createQuery(jpql, Menu.class);
            }
            if (count != null) {
                query.setMaxResults(count);
            }
            List<Menu> result = query.getResultList();
            sort(result);
            return result;
        } else {
            String jpql = "select menu from Menu menu where menu.parent = :parent order by menu.sort asc";
            query = entityManager.createQuery(jpql, Menu.class).setParameter("parent", menu);
            if (count != null) {
                query.setMaxResults(count);
            }
            return query.getResultList();
        }
    }


    /**
     * 排序地区
     *
     * @param menus 地区
     */
    private void sort(List<Menu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return;
        }
        final Map<Long, Integer> orderMap = new HashMap<Long, Integer>();
        for (Menu menu : menus) {
            orderMap.put(menu.getId(), menu.getSort());
        }
        Collections.sort(menus, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                Long[] ids1 = (Long[]) ArrayUtils.add(o1.getParentIds(), o1.getId());
                Long[] ids2 = (Long[]) ArrayUtils.add(o2.getParentIds(), o2.getId());
                Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
                Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
                CompareToBuilder compareToBuilder = new CompareToBuilder();
                while (iterator1.hasNext() && iterator2.hasNext()) {
                    Long id1 = iterator1.next();
                    Long id2 = iterator2.next();
                    Integer order1 = orderMap.get(id1);
                    Integer order2 = orderMap.get(id2);
                    compareToBuilder.append(order1, order2).append(id1, id2);
                    if (!iterator1.hasNext() || !iterator2.hasNext()) {
                        compareToBuilder.append(o1.getGrade(), o2.getGrade());
                    }
                }
                return compareToBuilder.toComparison();
            }
        });
    }
}
