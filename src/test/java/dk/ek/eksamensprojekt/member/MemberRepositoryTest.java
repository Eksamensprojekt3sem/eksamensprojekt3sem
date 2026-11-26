package dk.ek.eksamensprojekt.member;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldSaveAndRetrieveMember() {

        // Arrange
        Member member = Member.create(
                1L, "Peter", "Pedal", BeltGrade.DAN_1
        );

        // Act
        Member savedMember = memberRepository.saveAndFlush(member);
        Long id = savedMember.getId();

        entityManager.clear();

        Member retrievedMember = memberRepository.findById(id)
                .orElseThrow();

        // Assert
        assertNotNull(id);
        assertEquals(1L, retrievedMember.getAccountId());
        assertEquals("Peter", retrievedMember.getFirstName());
        assertEquals("Pedal", retrievedMember.getLastName());
        assertEquals(BeltGrade.DAN_1, retrievedMember.getBeltGrade());
    }

    @Test
    void shouldFindMemberByFirstName() {

        // Arrange
        memberRepository.save(
                Member.create(1L, "Peter", "Pedal", BeltGrade.DAN_1)
        );

        memberRepository.save(
                Member.create(2L, "Manden", "Med den Gule Hat", BeltGrade.DAN_1)
        );

        // Act
        List<Member> result = memberRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        "Peter", "Peter"
                );

        // Assert
        assertEquals(1, result.size());
        assertEquals("Peter", result.getFirst().getFirstName());
    }

    @Test
    void shouldFindMemberByLastName() {

        // Arrange
        memberRepository.save(
                Member.create(1L, "Peter", "Pedal", BeltGrade.DAN_1)
        );

        memberRepository.save(
                Member.create(2L, "Anders", "Jensen", BeltGrade.DAN_1)
        );

        // Act
        List<Member> result = memberRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        "Jensen", "Jensen"
                );

        // Assert
        assertEquals(1, result.size());
        assertEquals("Jensen", result.getFirst().getLastName());
    }

    @Test
    void shouldFindMembersByPartialName() {

        // Arrange
        memberRepository.save(
                Member.create(1L, "Peter", "Pedal", BeltGrade.DAN_1)
        );

        memberRepository.save(
                Member.create(2L, "Anders", "Petersen", BeltGrade.DAN_1)
        );

        memberRepository.save(
                Member.create(3L, "Mikkel", "Jensen", BeltGrade.DAN_1)
        );

        // Act
        List<Member> result = memberRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        "Pet", "Pet"
                );

        // Assert
        assertEquals(2, result.size());

        assertTrue(result.stream()
                .anyMatch(member -> member.getFirstName().equals("Peter")));

        assertTrue(result.stream()
                .anyMatch(member -> member.getLastName().equals("Petersen")));
    }

    @Test
    void shouldIgnoreCaseWhenSearching() {

        // Arrange
        memberRepository.save(
                Member.create(1L, "Peter", "Pedal", BeltGrade.DAN_1)
        );

        // Act
        List<Member> result = memberRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        "PETER", "PETER"
                );

        // Assert
        assertEquals(1, result.size());
        assertEquals("Peter", result.getFirst().getFirstName());
    }

    @Test
    void shouldReturnEmptyListWhenNoMembersMatch() {

        // Arrange
        memberRepository.save(
                Member.create(1L, "Peter", "Pedal", BeltGrade.DAN_1)
        );

        // Act
        List<Member> result = memberRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        "Mikkel", "Mikkel"
                );

        // Assert
        assertTrue(result.isEmpty());
    }
}

