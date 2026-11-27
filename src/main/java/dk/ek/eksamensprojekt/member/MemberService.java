package dk.ek.eksamensprojekt.member;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
class MemberService {

    private final MemberRepository memberRepository;

    MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    List<MemberResponse> getAll(String name) {

        List<Member> members;

        if (name == null || name.isBlank()) {
            members = memberRepository.findAll();

        } else {

            members = memberRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);

        }
        return members.stream()
                .sorted(Comparator.comparing(Member::getLastName)
                        .thenComparing(Member::getFirstName))
                .map(member -> new MemberResponse(
                        member.getId(),
                        member.getFirstName(),
                        member.getLastName(),
                        member.getBeltGrade()
                )).toList();
    }
}
