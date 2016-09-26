package cn.starteasy.uaa.web.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO 一句话描述该类用途
 * <p>
 * 创建时间: 16/9/26 下午4:02<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Component
public class CrossFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {

        if (request.getRequestURI().contains("oauth") || request.getRequestURI().contains("login") ||
                request.getRequestURI().contains("logout")
                || request.getRequestURI().contains("checkAccessTokenIsExpire") || request.getRequestURI().contains("account")) {
            // CORS "pre-flight" request
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
            response.setHeader("Access-Control-Allow-Headers", "Authorization");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "1800");
//            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Credentials", "true");
        }
        filterChain.doFilter(request, response);
    }
}
