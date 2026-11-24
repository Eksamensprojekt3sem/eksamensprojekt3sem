package dk.ek.eksamensprojekt.common;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.assertj.core.api.Assertions.assertThat;


public class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void notFound_exception_returns404() {
        // Arrange
        NotFoundException exception = new NotFoundException("Member not found");

        //Act
        ProblemDetail result = handler.notFound(exception);

        // Assert
        assertThat(result.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getDetail()).isEqualTo("Member not found");
    }

    @Test
    void conflict_exception_returns409() {
        // Arrange
        ConflictException exception = new ConflictException("Member already exists");

        // Act
        ProblemDetail result = handler.conflict(exception);

        // Assert
        assertThat(result.getStatus()).isEqualTo(409);
    }

    @Test
    void invalidInput_exception_returns400() {
        // Arrange
        InvalidInputException exception = new InvalidInputException("Invalid member data");

        // Act
        ProblemDetail result = handler.invalid(exception);

        // Assert
        assertThat(result.getStatus()).isEqualTo(400);
    }
}
