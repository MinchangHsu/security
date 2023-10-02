package com.caster.security.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
        // httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.getWriter()
                .write(new ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString("JSONResult.createResult(ErrorCodeMsg.UNAUTHORIZED)"));
        httpServletResponse.flushBuffer();
//         httpServletRequest.getRequestDispatcher("/toLogin").forward(httpServletRequest, httpServletResponse);
//         httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/toLogin");
    }
}
