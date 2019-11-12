package com.jit.DataService.influxdb.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Component
@Log4j2
public class InfluxdbFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        Map<String,String[]> map =request.getParameterMap();
        StringBuffer info = new StringBuffer();
        for (Map.Entry<String,String[]> entry : map.entrySet()) {
            info.append(entry.getKey()+"=");
            info.append(entry.getValue()[0]+" ; ");
        }
        log.info("request info === URL:{}, Method:{}, RequestParam:{}",url,method,info.toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
