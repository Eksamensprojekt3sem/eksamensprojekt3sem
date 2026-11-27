package dk.ek.eksamensprojekt.account;

/**
 * The account feature's public contract: the only way other features use it.
 * Other features store an accountId and look the account up here.
 */
public interface AccountApi {


    /**
     * The id of the account with this email, ignoring case and surrounding spaces.
     * @throws dk.ek.eksamensprojekt.common.NotFoundException if there is no account with this email
     */
    Long getAccountId(String email);
}
