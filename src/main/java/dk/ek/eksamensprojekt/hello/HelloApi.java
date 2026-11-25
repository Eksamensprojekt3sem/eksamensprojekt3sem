package dk.ek.eksamensprojekt.hello;

import java.util.List;

public interface HelloApi {
    List<GreetingResponse> getAll();

    void createIfNotExists(String message);
}
