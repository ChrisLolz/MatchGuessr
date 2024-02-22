package com.chris.backend.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.chris.backend.models.Account;
import com.chris.backend.payload.response.JwtResponse;

@Service
public class AuthService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("https://github.chrislolz.io")
                .issuedAt(now)
                .expiresAt(now.plus(24, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("authorities", roles)
                .claim("id", ((Account) authentication.getPrincipal()).getId())
                .build();
        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return jwtEncoder.encode(encoderParameters).getTokenValue();
    }

    public ResponseEntity<?> login(String username, String password) {
        try {
            Authentication authentication = authenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(username, password));
            Account account = (Account) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(generateToken(authentication), account.getId(), account.getUsername(), account.getRoles()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: Invalid username or password!");
        }
    }

    public ResponseEntity<?> register(String username, String password) {
        if (accountService.findByUsername(username) != null) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        Account account = new Account(username, passwordEncoder.encode(password));
        accountService.save(account);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(account, account.getPassword(), account.getAuthorities());

        return ResponseEntity.ok(new JwtResponse(generateToken(authentication), account.getId(), account.getUsername(), account.getRoles()));
    }

    public ResponseEntity<?> validate(Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Account account = accountService.findByUsername(jwt.getSubject());
            if (account == null) {
                return ResponseEntity.badRequest().body("Error: Invalid token!");
            }
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: Invalid token!");
        }
    }
}
