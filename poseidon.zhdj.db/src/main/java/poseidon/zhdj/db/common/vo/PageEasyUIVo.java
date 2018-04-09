package poseidon.zhdj.db.common.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PageEasyUIVo <T>{
	private boolean success;
	
	private String message;
	
	//========================================================================
	// 当前的页码
	//========================================================================
	private Integer page = 1;

	//========================================================================
	// 每一页的大小
	//========================================================================
	private Integer row = 20;

	//========================================================================
	// 总的页数
	//========================================================================
	private Long pageCount = 0L;

	//========================================================================
	// 总的记录条数
	//========================================================================
	private Long total = 0L;

	//========================================================================
	// 查询条件
	//========================================================================
	private Map<String, Object> condition;

	//========================================================================
	// 页面中的list
	//========================================================================
	private List<T> rows;
	
	public PageEasyUIVo(Integer page, Integer row, Long pageCount, Long total, List<T> rows){
		this.page = page;
		this.row = row;
		this.pageCount = pageCount;
		this.total = total;
		this.rows = rows;
	}

	public PageEasyUIVo(PageVo<T> obj) {
		if ( obj == null )
		{
			this.setPage(0);
			this.setRow(0);
			this.setTotal(0L);
			this.setPageCount(0L);
			rows = new ArrayList();
			this.setRows(rows);
			this.success = false;
		}
		else {
			this.setPage(obj.getPageNo());
			this.setRow(obj.getPageSize());
			this.setTotal(obj.getTotal());
			this.setPageCount(obj.getPageCount());
			this.setRows(obj.getList());
		}
		
	}

	public PageEasyUIVo() {
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
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

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
