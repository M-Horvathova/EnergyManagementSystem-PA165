package cz.fi.muni.pa165.restapi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cz.fi.muni.pa165.dto.portalUser.*;
import cz.fi.muni.pa165.entity.UserRole;
import cz.fi.muni.pa165.facade.PortalUserFacade;
import cz.fi.muni.pa165.restapi.exceptions.IncorrectPassordException;
import cz.fi.muni.pa165.restapi.exceptions.NotExistingUserException;
import cz.fi.muni.pa165.restapi.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private static final int ITEMS_PAGE_COUNT = 10;
    private final PortalUserFacade portalUserFacade;

    @Autowired
    public AuthController(PortalUserFacade portalUserFacade) {
        this.portalUserFacade = portalUserFacade;
    }

    @RequestMapping(path = "/user/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String register(@RequestBody PortalUserRegistrationDTO portalUserRegistrationDTO) throws Exception {
        PortalUserDTO portalUserDTO = portalUserFacade.findUserByEmail(portalUserRegistrationDTO.getEmail());
        if (portalUserDTO != null) {
            throw new UserAlreadyExistsException();
        }

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

    @RequestMapping(path = "/user/login",
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

    @RequestMapping(path = "/user/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final String editUser(@PathVariable("id") Long id, @RequestBody PortalUserChangeBasicInfoDTO portalUserChangeBasicInfoDTO) throws Exception {
        portalUserChangeBasicInfoDTO.setId(id);
        portalUserFacade.updateBasicUserInfo(portalUserChangeBasicInfoDTO);
        return "true";
    }

    @RequestMapping(path = "/user/{id}",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PortalUserDTO getUser(@PathVariable("id") Long id) throws Exception {
        return portalUserFacade.findUserById(id);
    }

    @RequestMapping(path = "/user/change-password/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final boolean changePassword(@PathVariable("id") Long id, @RequestBody PortalUserChangePasswordDTO portalUserChangePasswordDTO) throws Exception {
        portalUserChangePasswordDTO.setId(id);
        return portalUserFacade.changePassword(portalUserChangePasswordDTO);
    }

    @RequestMapping(path = "/users/{page}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PortalUserListingDTO getUsers(@PathVariable("page") int page) throws Exception {
        PortalUserListingDTO users = portalUserFacade.getAllUsers(page, ITEMS_PAGE_COUNT);
        return users;
    }
}
