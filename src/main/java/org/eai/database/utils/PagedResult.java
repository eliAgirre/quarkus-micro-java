package org.eai.database.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RegisterForReflection
public class PagedResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long numOfResults = 0L;
    private Integer page = 0;
    private Integer size = 0;
    private List<T> list = new ArrayList<>();

    public static <T> PagedResult<T> of(PanacheQuery<T> query) {
        PagedResult<T> result = new PagedResult<T>();
        result.setList(query.list());
        result.setNumOfResults(query.count());
        result.setPage(query.page().index);
        result.setSize(query.page().size);
        return result;
    }

    /**
     * Number of results, without pagination
     **/

    @JsonProperty("numOfResults")
    public Long getNumOfResults() {
        return numOfResults;
    }

    public void setNumOfResults(Long numOfResults) {
        this.numOfResults = numOfResults;
    }

    /**
     * pagination page number
     **/

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * pagination page size
     **/

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * list
     **/
    @JsonProperty("list")
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PagedResult<T> results = (PagedResult<T>) o;
        return Objects.equals(numOfResults, results.numOfResults) &&
                Objects.equals(page, results.page) &&
                Objects.equals(size, results.size) &&
                Objects.equals(list, results.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numOfResults, page, size, list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PaginableResults {\n");

        sb.append("    numOfResults: ").append(toIndentedString(numOfResults)).append("\n");
        sb.append("    page: ").append(toIndentedString(page)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("    list: ").append(toIndentedString(list)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}