package br.com.felippepuhle.util.datatable;

import java.util.List;

public class DataTableRequest {

    private List<DataTableColumn> headers;

    private String search;

    public List<DataTableColumn> getHeaders() {
        return headers;
    }

    public String getSearch() {
        return search;
    }

}
