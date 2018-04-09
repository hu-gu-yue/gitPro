package poseidon.lib.core.tool.checkempty;

import java.util.Collection;

/**
 * 校验对象是否为空的工具类(例如：null或者没有元素的空容器)
 * 
 * @author yindan
 * 
 */
public class CheckEmptyTool {

	public static boolean emptyCollection(Collection co) {
		return null == co || co.size() == 0;
	}
}
