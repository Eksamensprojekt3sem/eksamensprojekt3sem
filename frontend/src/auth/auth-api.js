import {sendForm} from "../common/api.js";

// Sender e-mail og adgangskode til Spring, som tjekker dem
// false = ved 401 (forkert login) så der skal vi ikke redirectes da vi allerede er på den side
export function login(email, password) {
    return sendForm("/api/login", {email, password}, false);
}