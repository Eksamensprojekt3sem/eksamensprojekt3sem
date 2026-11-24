package dk.ek.eksamensprojekt.hello;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class GreetingService implements HelloApi{
    private final GreetingRepository repository;

    GreetingService(GreetingRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GreetingResponse> getAll() {
        return repository.findAll().stream()
                .map(greeting -> new GreetingResponse(
                        greeting.getId(), greeting.getMessage())
                )
                .toList();
    }

    @Override
    @Transactional
    public void createIfNotExists(String message) {
        if(!repository.existsByMessage(message)){
            repository.save(Greeting.create(message));
        }
    }
}
