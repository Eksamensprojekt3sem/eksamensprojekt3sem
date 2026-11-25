package dk.ek.eksamensprojekt.hello;

import dk.ek.eksamensprojekt.common.InvalidInputException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
class Greeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    protected Greeting(){
        // required by JPA
    }

    private Greeting(String message){
        if(message == null || message.isBlank()){
            throw new InvalidInputException("Beskeden må ikke være tom.");
        }

        this.message = message.trim();
    }

    static Greeting create(String message){
        return new Greeting(message);
    }

    Long getId() {
        return id;
    }

    String getMessage() {
        return message;
    }
}
