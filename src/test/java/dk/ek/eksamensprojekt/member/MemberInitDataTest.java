package dk.ek.eksamensprojekt.member;

import dk.ek.eksamensprojekt.account.AccountApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberInitData {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberInitData memberInitData;

    @Autowired
    private AccountApi accountApi;


}
