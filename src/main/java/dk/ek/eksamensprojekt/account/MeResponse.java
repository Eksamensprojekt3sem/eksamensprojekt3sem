package dk.ek.eksamensprojekt.account;

import java.util.List;

record MeResponse(
        String username,
        List<String> roles
) {
}
