package cz.fi.muni.pa165.restapi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cz.fi.muni.pa165.dto.PortalUserAuthenticateDTO;
import cz.fi.muni.pa165.entity.UserRole;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String login(@RequestBody PortalUserAuthenticateDTO portalUserAuthenticateDTO) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withClaim("id", 1L)
                .withClaim("email", "valo.patrik@gmail.com")
                .withClaim("firstname", "Patrik")
                .withClaim("lastname", "Valo")
                .withClaim("role", UserRole.USER_ROLE_NAME)
                .sign(algorithm);
    }
}
