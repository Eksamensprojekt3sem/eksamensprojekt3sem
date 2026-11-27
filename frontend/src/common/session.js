import {getJSON} from "./api.js";

// Brugeren der er logget ind, eller null. Kommer fra GET /api/me
let currentUser = null;

export function getUser() {
    return currentUser;
}

export function clearUser() {
    currentUser = null;
}

// Spørger serveren, hvem der er logget ind. Kaldes ved opstart og lige efter login
export async function loadUser() {
    try {
        currentUser = await getJSON("/api/me", false);
    } catch (error) {
        currentUser = null;
    }
    return currentUser;
}