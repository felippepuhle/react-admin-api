package br.com.felippepuhle.util.datatable;

import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DataTableRepository<T extends Object, ID extends Serializable> {

    public Page<T> findAll(Pageable pageable, DataTableRequest request);

}
