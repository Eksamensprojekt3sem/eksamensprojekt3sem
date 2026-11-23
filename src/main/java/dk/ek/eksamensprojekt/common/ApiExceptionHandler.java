package dk.ek.eksamensprojekt.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ProblemDetail notFound(NotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                "Ressourcen blev ikke fundet."
        );
    }

        @ExceptionHandler(ConflictException.class)
        ProblemDetail conflict(ConflictException exception) {
            return ProblemDetail.forStatusAndDetail(
                    HttpStatus.CONFLICT,
                    "Handlingen er i konflikt med de eksisterende oplysninger."
            );
        }

    @ExceptionHandler(InvalidInputException.class)
    ProblemDetail invalid(InvalidInputException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Ugyldige oplysninger."
        );
    }
}
