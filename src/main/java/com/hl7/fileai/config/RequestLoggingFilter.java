package com.hl7.fileai.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.hl7.tools.ServerLog;

@Component
public class RequestLoggingFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String clientIpAddress = httpRequest.getRemoteAddr();
        // Log the client IP address
        // For example, using SLF4J Logger
        // Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
        // logger.info("Request from client IP: {}", clientIpAddress);
        try {
			ServerLog.writeLog("Request from client IP: " + clientIpAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        chain.doFilter(request, response);
    }
}
