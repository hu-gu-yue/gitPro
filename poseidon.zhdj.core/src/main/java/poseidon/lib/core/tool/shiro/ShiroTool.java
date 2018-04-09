
package poseidon.lib.core.tool.shiro;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiroTool {

	
	public static boolean isGuest() {
		boolean flag = true;
		
		try
		{
			flag = (ShiroTool.getSubject() == null || ShiroTool.getSubject().getPrincipal() == null);
		}
		catch(Exception e)
		{
			flag = true;
		}
		return flag;
	}

	public static boolean isUser() {
		return getSubject() != null && getSubject().getPrincipal() != null;
	}


	public static boolean isAuthenticated() {
		return getSubject() != null && getSubject().isAuthenticated();
	}

	public static boolean isNotAuthenticated() {
		return !isAuthenticated();
	}

	public static String principal(Map map) {
		String strValue = null;
		if (getSubject() != null) {

			// Get the principal to print out
			Object principal;
			String type = map != null ? (String) map.get("type") : null;
			if (type == null) {
				principal = getSubject().getPrincipal();
			} else {
				principal = getPrincipalFromClassName(type);
			}
			String property = map != null ? (String) map.get("property") : null;
			// Get the string value of the principal
			if (principal != null) {
				if (property == null) {
					strValue = principal.toString();
				} else {
					strValue = getPrincipalProperty(principal, property);
				}
			}

		}

		if (strValue != null) {
			return strValue;
		} else {
			return null;
		}
	}

	public static boolean hasRole(String roleName) {
		return getSubject() != null && getSubject().hasRole(roleName);
	}


	public static boolean lacksRole(String roleName) {
		boolean hasRole = getSubject() != null && getSubject().hasRole(roleName);
		return !hasRole;
	}

	public static boolean hasAnyRole(String roleNames) {
		boolean hasAnyRole = false;

		Subject subject = getSubject();

		if (subject != null) {
			for (String role : roleNames.split(",")) {
				if (subject.hasRole(role.trim())) {
					hasAnyRole = true;
					break;
				}
			}

		}

		return hasAnyRole;
	}

	public static boolean hasPermission(String p) {
		return getSubject() != null && getSubject().isPermitted(p);
	}
	
	public static boolean lacksPermission(String p) {
		return !hasPermission(p);
	}

	@SuppressWarnings({ "unchecked" })
	public static  Object getPrincipalFromClassName(String type) {
		Object principal = null;

		try {
			Class cls = Class.forName(type);
			principal = getSubject().getPrincipals().oneByType(cls);
		} catch (ClassNotFoundException e) {

		}
		return principal;
	}

	public static  String getPrincipalProperty(Object principal, String property) {
		String strValue = null;

		try {
			BeanInfo bi = Introspector.getBeanInfo(principal.getClass());

			// Loop through the properties to get the string value of the
			// specified property
			boolean foundProperty = false;
			for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
				if (pd.getName().equals(property)) {
					Object value = pd.getReadMethod().invoke(principal, (Object[]) null);
					strValue = String.valueOf(value);
					foundProperty = true;
					break;
				}
			}

			if (!foundProperty) {
				final String message = "Property [" + property + "] not found in principal of type [" + principal.getClass().getName() + "]";

				throw new RuntimeException(message);
			}

		} catch (Exception e) {
			final String message = "Error reading property [" + property + "] from principal of type [" + principal.getClass().getName() + "]";

			throw new RuntimeException(message, e);
		}

		return strValue;
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
}
