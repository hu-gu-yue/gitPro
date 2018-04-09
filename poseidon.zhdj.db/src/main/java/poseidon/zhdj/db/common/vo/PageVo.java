package poseidon.zhdj.db.common.vo;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

public class PageVo<T> {
	/**
	 * 当前的页码
	 */
	private Integer pageNo = 1;

	/**
	 * 每一页的大小
	 */
	private Integer pageSize = 20;

	/**
	 * 总的页数
	 */
	private Long pageCount = 0L;

	/**
	 * 总的记录条数
	 */
	private Long total = 0L;

	/**
	 * 查询条件
	 */
	private Map<String, Object> condition;
	
	/**
	 * 页面中的list
	 */
	private List<T> list;

	public PageVo(Page<T> obj) {
		this.setPageNo(obj.getPageNum());
		this.setPageSize(obj.getPageSize());
		this.setTotal(obj.getTotal());
		this.setPageCount(new Long(obj.getPages()));
		this.setList((List<T>) obj);
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getPageCount() {
		return pageCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}
}