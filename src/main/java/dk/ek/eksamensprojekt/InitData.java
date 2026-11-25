package dk.ek.eksamensprojekt;

import dk.ek.eksamensprojekt.hello.HelloApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class InitData implements CommandLineRunner {
    private final HelloApi helloApi;

    InitData(HelloApi helloApi) {
        this.helloApi = helloApi;
    }

    @Override
    public void run(String... args) throws Exception {
        helloApi.createIfNotExists("Hej fra databasen!");
    }
}
