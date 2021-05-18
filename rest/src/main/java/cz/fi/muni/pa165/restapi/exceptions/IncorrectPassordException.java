package cz.fi.muni.pa165.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="login.incorrectPassword")
public class IncorrectPassordException extends Exception {
}
