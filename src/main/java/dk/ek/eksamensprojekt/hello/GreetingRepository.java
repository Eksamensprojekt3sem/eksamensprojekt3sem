package dk.ek.eksamensprojekt.hello;

import org.springframework.data.jpa.repository.JpaRepository;

interface GreetingRepository extends JpaRepository<Greeting, Long> {
    boolean existsByMessage(String message);
}
