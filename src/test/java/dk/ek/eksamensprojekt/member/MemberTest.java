package dk.ek.eksamensprojekt.member;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

@Test
    void shouldCreateMemberWithCorrectValue() {

    // Arrange
    Long accountId = 1L;
    String firstName = "Peter";
    String lastName = "Pedal";
    BeltGrade beltGrade = BeltGrade.DAN_1;

    // Act
    Member member = Member.create(accountId, firstName, lastName, beltGrade);

    // Assert
    assertNotNull(member);
    assertNull(member.getId());
    assertEquals(accountId, member.getAccountId());
    assertEquals(firstName, member.getFirstName());
    assertEquals(lastName, member.getLastName());
    assertEquals(beltGrade, member.getBeltGrade());
}
}
