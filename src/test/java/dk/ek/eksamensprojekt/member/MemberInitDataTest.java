package dk.ek.eksamensprojekt.member;

import dk.ek.eksamensprojekt.account.AccountApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberInitDataTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberInitData memberInitData;

    @Autowired
    private AccountApi accountApi;

    @Test
    void shouldSeedMembersOnStartup() {
        List<Member> members = memberRepository.findAll();

        assertThat(members).hasSize(10);

        assertThat(members)
                .extracting(Member::getBeltGrade)
                .contains(BeltGrade.KYU_10, BeltGrade.KYU_3, BeltGrade.DAN_1);

        assertThat(members)
                .filteredOn(member -> member.getFirstName().equals("Peter"))
                .hasSize(2);

        assertThat(members)
                .filteredOn(member -> member.getFirstName().startsWith("Pet"))
                .hasSize(3);
    }

    @Test
    void shouldNotDuplicateMembers() {
        long countBefore = memberRepository.count();

        memberInitData.run();

        long countAfter = memberRepository.count();

        assertThat(countBefore).isEqualTo(10);
        assertThat(countAfter).isEqualTo(countBefore);
    }

    @Test
    void shouldSeedMembersWithValidAccountIds() {
        Long member1Id = accountApi.getAccountId("member1@example.dk");
        Long member2Id = accountApi.getAccountId("member2@example.dk");
        Long parent1Id = accountApi.getAccountId("parent1@example.dk");
        Long parent2Id = accountApi.getAccountId("parent2@example.dk");

        List<Member> members = memberRepository.findAll();

        assertThat(members)
                .extracting(Member::getAccountId)
                .containsOnly(member1Id, member2Id, parent1Id, parent2Id);

        assertThat(members)
                .filteredOn(member -> member.getAccountId().equals(parent1Id))
                .hasSize(4);

        assertThat(members)
                .filteredOn(member -> member.getAccountId().equals(parent2Id))
                .hasSize(4);

        assertThat(members)
                .filteredOn(member -> member.getAccountId().equals(member1Id))
                .hasSize(1);

        assertThat(members)
                .filteredOn(member -> member.getAccountId().equals(member2Id))
                .hasSize(1);
    }
}
