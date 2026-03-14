package com.palpita.palpita.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain
  ) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    String authHeader = httpRequest.getHeader("Authorization");

    if(authHeader != null && authHeader.startsWith("Bearer ")){
      String token = authHeader.substring(7);

      System.out.println("JWT recebido: " + token);
    }

    chain.doFilter(request, response);
  }
}
