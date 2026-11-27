package dk.ek.eksamensprojekt.member;

import dk.ek.eksamensprojekt.account.AccountApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
class MemberInitData implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final AccountApi accountApi;

    MemberInitData(MemberRepository memberRepository, AccountApi accountApi) {
        this.memberRepository=memberRepository;
        this.accountApi=accountApi;
    }

    @Override
    public void run(String... args) {

        Long count = memberRepository.count();

        if (count > 0) {
            return;
        }

        Long member1Id = accountApi.getAccountId("member1@example.dk");
        Long member2Id = accountApi.getAccountId("member2@example.dk");
        Long parent1Id = accountApi.getAccountId("parent1@example.dk");
        Long parent2Id = accountApi.getAccountId("parent2@example.dk");

        List<Member> members = List.of(Member.create(member1Id, "Peter", "Hansen", BeltGrade.KYU_3),
        Member.create(member2Id, "Peter", "Larsen", BeltGrade.DAN_1),
        Member.create(parent1Id, "Petra", "Jensen", BeltGrade.KYU_10),
        Member.create(parent1Id, "Emma", "Jensen", BeltGrade.KYU_8),
        Member.create(parent1Id, "Oliver", "Jensen", BeltGrade.KYU_5),
        Member.create(parent1Id, "Lucas", "Jensen", BeltGrade.KYU_2),
        Member.create(parent2Id, "Sofie", "Nielsen", BeltGrade.KYU_6),
        Member.create(parent2Id, "Mikkel", "Nielsen", BeltGrade.KYU_4),
        Member.create(parent2Id, "Freja", "Nielsen", BeltGrade.KYU_9),
        Member.create(parent2Id, "Anders", "Nielsen", BeltGrade.KYU_1));

        memberRepository.saveAll(members);

    }

}
