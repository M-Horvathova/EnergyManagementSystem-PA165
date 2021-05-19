package cz.fi.muni.pa165.restapi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.UserRole;
import cz.fi.muni.pa165.facade.PortalUserFacade;
import cz.fi.muni.pa165.restapi.exceptions.IncorrectPassordException;
import cz.fi.muni.pa165.restapi.exceptions.NotExistingUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final PortalUserFacade portalUserFacade;

    @Autowired
    public AuthController(PortalUserFacade portalUserFacade) {
        this.portalUserFacade = portalUserFacade;
    }

    @RequestMapping(path = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String register(@RequestBody PortalUserRegistrationDTO portalUserRegistrationDTO) throws Exception {
        portalUserFacade.registerUser(portalUserRegistrationDTO);
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withClaim("id", portalUserRegistrationDTO.getId())
                .withClaim("email", portalUserRegistrationDTO.getEmail())
                .withClaim("firstname", portalUserRegistrationDTO.getFirstName())
                .withClaim("lastname", portalUserRegistrationDTO.getLastName())
                .withClaim("role", UserRole.USER_ROLE_NAME)
                .sign(algorithm);
    }

    @RequestMapping(path = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String login(@RequestBody PortalUserAuthenticateDTO portalUserAuthenticateDTO) throws Exception {
        PortalUserDTO portalUserDTO = portalUserFacade.findUserByEmail(portalUserAuthenticateDTO.getUserName());
        if (portalUserDTO == null) {
            throw new NotExistingUserException();
        }
        if (portalUserFacade.authenticate(portalUserAuthenticateDTO)) {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.create()
                    .withClaim("id", portalUserDTO.getId())
                    .withClaim("email", portalUserDTO.getEmail())
                    .withClaim("firstname", portalUserDTO.getFirstName())
                    .withClaim("lastname", portalUserDTO.getLastName())
                    .withClaim("role", portalUserDTO.isAdmin() ? UserRole.ADMINISTRATOR_ROLE_NAME : UserRole.USER_ROLE_NAME)
                    .sign(algorithm);
        } else {
            throw new IncorrectPassordException();
        }
    }

    @RequestMapping(path = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String editUser(@PathVariable("id") Long id, @RequestBody PortalUserChangeBasicInfoDTO portalUserChangeBasicInfoDTO) throws Exception {
        portalUserChangeBasicInfoDTO.setId(id);
        portalUserFacade.updateBasicUserInfo(portalUserChangeBasicInfoDTO);
        return "true";
    }

    @RequestMapping(path = "/{id}",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PortalUserDTO getUser(@PathVariable("id") Long id) throws Exception {
        return portalUserFacade.findUserById(id);
    }

    @RequestMapping(path = "/change-password/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final boolean changePassword(@PathVariable("id") Long id, @RequestBody PortalUserChangePasswordDTO portalUserChangePasswordDTO) throws Exception {
        portalUserChangePasswordDTO.setId(id);
        return portalUserFacade.changePassword(portalUserChangePasswordDTO);
    }

    @RequestMapping(path = "/deactivate-user/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String deactivateUser(@PathVariable("id") Long id) throws Exception {
        portalUserFacade.deactivateUser(id);
        return "true";
    }
}
