package br.com.felippepuhle.util.datatable;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
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
        List<T> content = getResults(pageable);
        Long total = getCount();

        System.out.println("Resultados: " + pageable.getOffset() + "," + pageable.getPageSize() + ": " + total);

        return new PageImpl(content, pageable, total);
    }

    private List<T> getResults(Pageable pageable) {
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(beanClass);

        criteria.setFirstResult(pageable.getOffset());
        criteria.setMaxResults(pageable.getPageSize());

        return criteria.list();
    }

    private Long getCount() {
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(beanClass);

        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

}
