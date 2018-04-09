package poseidon.lib.core.tool.money;

public class MoneyTool {
	
	/**
	 * 校验是否为金额
	 * @param money
	 * @return
	 * @author zhouqi
	 */
	public static boolean isMoney(String money) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]\\d*)|\\d)(\\.\\d{1,2})?$");
		java.util.regex.Matcher match = pattern.matcher(money);
		if (match.matches()) {
			return true;
		} else {
			return false;
		}
	}
}
