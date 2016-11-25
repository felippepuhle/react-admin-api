package br.com.felippepuhle.util.datatable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.util.StringUtils;

public class DataTableColumn {

    private String property;
    private String name;
    private Boolean searchable;

    public String getProperty() {
        return property;
    }

    public String getName() {
        return name;
    }

    public Boolean isSearchable() {
        return searchable;
    }

    public Boolean isValid() {
        return this.property.length() > 0;
    }

    public String getPath() {
        if (StringUtils.countOccurrencesOf(this.property, ".") < 2) {
            return this.property;
        }

        List<String> parts = Arrays.asList(this.property.split("\\."));
        String association = parts.get(parts.size() - 2);
        String column = parts.get(parts.size() - 1);

        return association + "." + column;
    }

    public Map<String, String> getJoins() {
        Map<String, String> joins = new HashMap<>();

        List<String> parts = Arrays.asList(this.property.split("\\."));
        IntStream
                .range(0, parts.size())
                .filter(index -> {
                    return index < parts.size() - 1;
                })
                .forEach(index -> {
                    String prev = index > 0 ? parts.get(index - 1) : "this";
                    String next = parts.get(index);

                    joins.put(prev, next);
                });

        return joins;
    }

}
