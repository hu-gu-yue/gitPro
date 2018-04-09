package poseidon.lib.core.tool.system;

public class TypeTool {
	public static Integer getChecked( Integer value, Integer defaultValue){
		if ( value == null || value < defaultValue ){
			value = defaultValue;
		}
		return value;
	}
}
