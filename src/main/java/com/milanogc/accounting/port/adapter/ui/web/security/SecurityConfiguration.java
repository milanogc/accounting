package com.milanogc.accounting.port.adapter.ui.web.security;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfiguration {

  @Bean
  public FilterRegistrationBean jwtFilter() {
    final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new JwtFilter());
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}
