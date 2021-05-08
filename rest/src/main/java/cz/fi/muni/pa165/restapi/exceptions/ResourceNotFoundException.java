package cz.fi.muni.pa165.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Patrik Valo
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="The requested resource was not found")
public class ResourceNotFoundException extends RuntimeException {

}
