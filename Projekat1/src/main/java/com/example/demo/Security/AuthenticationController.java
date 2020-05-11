package com.example.demo.Security;

import com.example.demo.Profile.Profile;
import com.example.demo.Profile.ProfileRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createToken(@RequestBody AuthenticationRQ request) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationRS(token));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody ValidationRQ validationRQ) throws Exception {

        UserDetails userDetails = userDetailsService.loadUserByUsername(validationRQ.getUsername());

        if(!jwtUtil.validateToken(validationRQ.getToken(), userDetails)) {
            throw new BadCredentialsException("Incorrect username or password") ;
        }

        Profile profile = profileRepository.findByUsername(validationRQ.getUsername());
        return ResponseEntity.ok(new ValidationRS(userDetails.getUsername(), userDetails.getPassword(), profile.getRole().getName()));
    }
}
