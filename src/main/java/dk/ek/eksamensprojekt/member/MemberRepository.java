package dk.ek.eksamensprojekt.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName
    );
}
