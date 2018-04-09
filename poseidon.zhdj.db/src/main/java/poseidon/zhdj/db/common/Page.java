package poseidon.zhdj.db.common;

import java.util.List;

/**
 * 用于接口传递分页数据
 */
public class Page<T> {

    private Integer pageCount;
    private Integer currentPage;
    private Long total;
    private List<T> data;

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageCount=" + pageCount +
                ", currentPage=" + currentPage +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}
