package com.milanogc.accounting.infrastructure.ui.web.security;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/")
public class AuthenticationController {

  private final Map<String, List<String>> users = new HashMap<>();

  public AuthenticationController() {
    users.put("user@example.com", Arrays.asList("user"));
    users.put("admin@example.com", Arrays.asList("user", "admin"));
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Token> authenticate(@RequestBody final Credentials credentials) throws ServletException {
    // https://github.com/nsarno/knock#user-content-authenticating-from-a-web-or-mobile-application

    if (credentials.email == null || !users.containsKey(credentials.email)) {
      // invalid credentials
      return ResponseEntity.status(401).body(null); // TODO return/throw proper object ?
    }

    // curl -H "Content-Type: application/json" -X POST -d '{"auth":{"email":"user@example.com","password":"secret"}}' http://localhost:8080/
    String token = Jwts.builder()
        .setSubject(credentials.email)
        .claim("roles", users.get(credentials.email))
        .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey")
        .compact();

    return ResponseEntity.ok(new Token(token));
  }

  @JsonRootName(value = "credentials")
  private static class Credentials {
    public String email;
    @SuppressWarnings("unused")
    public String password;
  }

  @JsonRootName(value = "token")
  private static class Token {
    private String token;

    public Token(final String token) {
      this.token = token;
    }
    
    @JsonValue
    public String getToken() {
      return token;
    }
  }
}
