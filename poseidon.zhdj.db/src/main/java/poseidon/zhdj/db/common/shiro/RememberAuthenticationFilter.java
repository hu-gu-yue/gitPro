package poseidon.zhdj.db.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class RememberAuthenticationFilter extends FormAuthenticationFilter {
	private static Logger log = LoggerFactory.getLogger(poseidon.zhdj.db.common.shiro.ShiroDbRealm.class);

    /**
     * 这个方法决定了是否能让用户登录
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);

        //如果 isAuthenticated 为 false 证明不是登录过的，同时 isRememberd 为true 证明是没登陆直接通过记住我功能进来的
        if (!subject.isAuthenticated() && subject.isRemembered()) {

            //获取session看看是不是空的
            Session session = subject.getSession(true);
        }

        //这个方法本来只返回 subject.isAuthenticated() 现在我们加上 subject.isRemembered() 让它同时也兼容remember这种情况
        return true;
    }
}