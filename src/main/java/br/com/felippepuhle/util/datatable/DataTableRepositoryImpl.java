package br.com.felippepuhle.util.datatable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class DataTableRepositoryImpl<T extends Object, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements DataTableRepository<T, ID> {

    private final Class<T> beanClass;

    private final EntityManager entityManager;

    public DataTableRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.beanClass = entityInformation.getJavaType();
        this.entityManager = entityManager;
    }

    @Override
    public Page<T> findAll(Pageable pageable, DataTableRequest request) {
        List<T> content = getResults(pageable, request);
        Long total = getCount(request);

        return new PageImpl(content, pageable, total);
    }

    private List<T> getResults(Pageable pageable, DataTableRequest request) {
        return this.createCriteria(request)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .list();
    }

    private Long getCount(DataTableRequest request) {
        return (Long) this.createCriteria(request)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    private Criteria createCriteria(DataTableRequest request) {
        Criteria criteria = entityManager
                .unwrap(Session.class)
                .createCriteria(this.beanClass, "this");

        this.getJoins(request)
                .forEach((table1, table2) -> {
                    criteria.createAlias(table1 + "." + table2, table2);
                });

        if (request.getSearch() != null) {
            criteria.add(
                    Restrictions.or(
                            this.getFilters(request).toArray(new Criterion[]{})
                    )
            );
        }

        return criteria;
    }

    private Map<String, String> getJoins(DataTableRequest request) {
        Map<String, String> joins = new HashMap<>();

        request.getHeaders()
                .stream()
                .filter((column) -> {
                    return column.isValid();
                })
                .forEach((column) -> {
                    column.getJoins().forEach((table1, table2) -> {
                        joins.merge(table1, table2, String::concat);
                    });
                });

        return joins;
    }

    private List<Criterion> getFilters(DataTableRequest request) {
        List<Criterion> list = new ArrayList<>();

        request.getHeaders()
                .stream()
                .filter((column) -> {
                    return column.isValid() && column.isSearchable();
                })
                .forEach((column) -> {
                    list.add(Restrictions.ilike(column.getPath(), "%" + request.getSearch() + "%"));
                });

        return list;
    }

}
