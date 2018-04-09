/**
 * Title: PageTool.java
 * Description:
 * Copyright: Copyright (c) 2015
 * Company: www.zjyou.cn
 * @author camus
 * @date 2015年1月19日
 * @version 1.0
 */
package poseidon.zhdj.db;

import java.util.Map;

import com.github.pagehelper.Page;


public class PageTool {

	
	/**
	 * Description: 为所有分页后的对象添加返回的结果分页属性
	 * @param list
	 * @param map
	 * @return
	 */
	public static Map<String, Object> addPageParamToMap( Map<String, Object> map, Object list) {
		Page<?> page = (Page<?>) list;
		map.put("total", page.getTotal());
		map.put("pages", page.getPages());
		map.put("pageNum", page.getPageNum());
		map.put("pageSize", page.getPageSize());
		map.put("list", list);
		return map;
	}
	
	
}
