package com.bos.wandun.dao.impl;

import com.bos.wandun.dao.OfficeDao;
import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.Office;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.*;


@Repository("officeDaoImpl")
public class OfficeDaoImpl extends BaseDaoImpl<Office, Long> implements OfficeDao {
    @Override
    public List<Office> findChildren(Office office, boolean recursive, Integer count) {
        TypedQuery<Office> query;
        if (recursive) {
            if (office != null) {
                String jpql = "select office from Office office where office.path like :treePath order by office.grade asc, office.sort asc";
                query = entityManager.createQuery(jpql, Office.class).setParameter("treePath", "%" + Office.TREE_PATH_SEPARATOR + office.getId() + Office.TREE_PATH_SEPARATOR + "%");
            } else {
                String jpql = "select office from Office office order by office.grade asc, office.sort asc";
                query = entityManager.createQuery(jpql, Office.class);
            }
            if (count != null) {
                query.setMaxResults(count);
            }
            List<Office> result = query.getResultList();
            sort(result);
            return result;
        } else {
            String jpql = "select office from Office office where office.parent = :parent order by office.sort asc";
            query = entityManager.createQuery(jpql, Office.class).setParameter("parent", office);
            if (count != null) {
                query.setMaxResults(count);
            }
            return query.getResultList();
        }
    }


    /**
     * 排序地区
     *
     * @param offices 地区
     */
    private void sort(List<Office> offices) {
        if (CollectionUtils.isEmpty(offices)) {
            return;
        }
        final Map<Long, Integer> orderMap = new HashMap<Long, Integer>();
        for (Office office : offices) {
            orderMap.put(office.getId(), office.getSort());
        }
        Collections.sort(offices, (o1, o2) -> {
            Long[] ids1 = ArrayUtils.add(o1.getParentIds(), o1.getId());
            Long[] ids2 = ArrayUtils.add(o2.getParentIds(), o2.getId());
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
        });
    }
}
