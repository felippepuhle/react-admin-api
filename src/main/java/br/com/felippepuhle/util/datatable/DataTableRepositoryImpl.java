package br.com.felippepuhle.util.datatable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(beanClass);

        criteria.setFirstResult(pageable.getOffset());
        criteria.setMaxResults(pageable.getPageSize());

        if (request.getSearch() != null) {
            criteria.add(Restrictions.or(getFilters(request).toArray(new Criterion[]{})));
        }

        return criteria.list();
    }

    private Long getCount(DataTableRequest request) {
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(beanClass);

        criteria.setProjection(Projections.rowCount());

        if (request.getSearch() != null) {
            criteria.add(Restrictions.or(getFilters(request).toArray(new Criterion[]{})));
        }

        return (Long) criteria.uniqueResult();
    }

    private List<Criterion> getFilters(DataTableRequest request) {
        List<Criterion> list = new ArrayList<>();

        request.getHeaders()
                .stream()
                .filter((column) -> {
                    Boolean isRealColumn = column.getProperty().length() > 0;
                    Boolean isSearchable = column.getSearchable();

                    return isRealColumn && isSearchable;
                })
                .forEach((column) -> {
                    list.add(Restrictions.ilike(column.getProperty(), "%" + request.getSearch() + "%"));
                });

        return list;
    }

}
