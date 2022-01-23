package uk.ac.cardiff.mma.application.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cardiff.mma.application.JwtAuth.AuthenticationRequest;
import uk.ac.cardiff.mma.application.JwtAuth.DatabaseUserDetailsService;
import uk.ac.cardiff.mma.application.JwtAuth.JwtUtils;

@Profile("!test")
@RestController
public class AuthenticationController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DatabaseUserDetailsService userDetailsService;

    // JSON format username and password
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtils.generateToken(userDetails);

        Cookie JWTcookie = new Cookie("Authorization", jwt);

        // expires in 600 seconds (10 minutes)
        JWTcookie.setMaxAge(600);

        // cookie properties
        JWTcookie.setSecure(false);
        JWTcookie.setHttpOnly(true);
        JWTcookie.setPath("/");        // cookie will be available to all directories of the domain it is set from; i.e. client (browser) will send cookie to all directories of domain

        // add cookie to response
        response.addCookie(JWTcookie);

        // get authority/role of logged-in user
        // Collection of SimpleGrantedAuthority objects --> Array of String objects
        String authority = userDetails.getAuthorities().toArray()[0].toString();

        String jwtAndAuthority = new JSONObject()
                                 .put("jwt", jwt)
                                 .put("authority", authority)
                                 .toString();

        // add JWT and the authority/role of logged-in user to response as JSON
        return new ResponseEntity<>(jwtAndAuthority, HttpStatus.OK);
    }

    @RequestMapping(value = "/log-in", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("LoginPage");
        return mav;
    }

    @RequestMapping(value = "/log-out", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        // Create matching cookie and set max age to 0 to invalidate
        Cookie JWTcookie = new Cookie("Authorization", null);

        JWTcookie.setSecure(false);
        JWTcookie.setHttpOnly(true);
        JWTcookie.setPath("/");

        JWTcookie.setMaxAge(0);        //  invalidate the Authorization cookie
        response.addCookie(JWTcookie);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("LoginPage");
        return mav;
    }

    @RequestMapping(value = "/log-in/{barcode}", method = RequestMethod.GET)
    public ModelAndView login_Var(@PathVariable String barcode) {
        ModelAndView mav = new ModelAndView();
        System.out.println(barcode);
        mav.addObject("barcode",barcode);
        mav.setViewName("LoginPage");
        return mav;
    }
}