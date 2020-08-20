package cn.izern.test.case1.servlet.filter;


import cn.izern.test.case1.servlet.CustomHttpServletRequest;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * servlet filter
 * <p>这里暂时注释，让其不生效。</p>
 *
 * @author: 热心网友
 * @since 1.0.0
 */
public class CustomHttpServletRequestFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    CustomHttpServletRequest rhsRequest = null;
    if (servletRequest instanceof HttpServletRequest) {
      rhsRequest = new CustomHttpServletRequest((HttpServletRequest) servletRequest);
    }

    if (null == rhsRequest) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      filterChain.doFilter(rhsRequest, servletResponse);
    }

  }

}
