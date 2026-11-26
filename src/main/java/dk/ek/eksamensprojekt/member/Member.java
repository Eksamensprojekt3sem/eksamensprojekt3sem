package dk.ek.eksamensprojekt.member;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
class Member {

    private static final int NAME_MAX_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false, length = NAME_MAX_LENGTH)
    private String firstName;

    @Column(nullable = false, length = NAME_MAX_LENGTH)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BeltGrade beltGrade;

    protected Member() {
    }

    private Member(Long accountId,
                   String firstName,
                   String lastName,
                   BeltGrade beltGrade) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.beltGrade = beltGrade;
    }

    static Member create(
            Long accountId,
            String firstName,
            String lastName,
            BeltGrade beltGrade
    ) {

        return new Member(
                accountId,
                firstName,
                lastName,
                beltGrade
        );
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BeltGrade getBeltGrade() {
        return beltGrade;
    }
}
