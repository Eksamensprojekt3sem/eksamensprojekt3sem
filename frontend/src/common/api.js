import {router} from "./router.js";
import {clearUser} from "./session.js";

// En fejl, brugeren kan se.
// - status: HTTP-status, eller 0 hvis serveren ikke kunne nås
// - fieldErrors: { email: "..." } når validering fejlede (400)
export class ApiError extends Error {
    constructor(status, message, fieldErrors = {}) {
        super(message);
        this.status = status;
        this.fieldErrors = fieldErrors;
    }
}

// redirectOn401: sæt til false, hvis 401 ikke skal sende brugeren til login (fx ved GET /api/me ved opstart)
export function getJSON(url, redirectOn401 = true) {
    return request("GET", url, {redirectOn401});
}

// Returnerer JSON-indholdet, eller null hvis svaret er tomt eller ikke er JSON
async function readJSON(response) {
    try {
        return await response.json();
    } catch (error) {
        return null;
    }
}

// redirectOn401: sæt til false, hvis fejlen skal vises på siden (fx ved login)
export function sendJSON(method, url, body = undefined, redirectOn401 = true) {
    return request(method, url, {json: body, redirectOn401});
}

// Springs formLogin læser kun formularfelter: sendForm("/api/login", new FormData(form), false)
export function sendForm(url, formData, redirectOn401 = true) {
    return request("POST", url, {form: formData, redirectOn401});
}

async function request(method, url, {json, form, redirectOn401 = true} = {}) {
    const options = {
        method,
        headers: {Accept: "application/json"}
    };

    if (json !== undefined) {
        options.headers["Content-Type"] = "application/json";
        options.body = JSON.stringify(json);
    }

    if (form !== undefined) {
        // fetch sætter selv Content-Type for URLSearchParams
        options.body = new URLSearchParams(form);
    }

    // Spring afviser skrivende kald uden denne header med 403. GET læser kun.
    if (method !== "GET") {
        options.headers["X-XSRF-TOKEN"] = csrfToken();
    }

    let response;

    try {
        response = await fetch(url, options);
    } catch (error) {
        throw new ApiError(0, "Kan ikke nå serveren. Tjek din forbindelse, og prøv igen.");
    }

    if (response.status === 204) {
        return null;
    }

    const data = await readJSON(response);

    if (!response.ok) {
        // Ikke logget ind (eller sessionen er udløbet): glem brugeren, og gå til login
        if (response.status === 401 && redirectOn401) {
            clearUser();
            router.navigate("/login");
        }
        throw new ApiError(response.status, errorMessage(response.status, data), data?.errors ?? {});
    }

    return data;
}

// Spring skriver tokenet i cookien XSRF-TOKEN. Læses ved hvert kald, fordi det skifter efter login.
function csrfToken() {
    const match = document.cookie.match(/(?:^|;\s*)XSRF-TOKEN=([^;]+)/);

    return match ? decodeURIComponent(match[1]) : "";
}

// Spring Boot sender fejl som ProblemDetail: {"status": 409, "detail": "..."}
function errorMessage(status, data) {
    if (data?.detail) {
        return data.detail;
    }

    if (status === 401) {
        return "Du skal logge ind.";
    }

    if (status === 403) {
        return "Du har ikke adgang til det.";
    }

    if (status >= 500) {
        return `Serveren havde et problem (HTTP ${status}). Prøv igen om lidt.`;
    }

    return `Forespørgslen fejlede (HTTP ${status}).`;
}