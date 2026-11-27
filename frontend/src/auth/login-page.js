// Login-siden. Vises uden sidemenu og topbar.
import { initLoginForm } from "./auth-login.js";

const SVG_NS = "http://www.w3.org/2000/svg";

// De fields der er i formen
const FIELDS = [
    { name: "email", id: "login-email", placeholder: "navn@eksempel.dk", type: "email", label: "E-mail", autocomplete: "username", maxLength: 200 },
    { name: "password", id: "login-password", type: "password", label: "Adgangskode", autocomplete: "current-password", maxLength: 72 },
];

// Fjerner sidebaren, topbaren, og kolonnerne (fra .app)
function enterAuthLayout() {
    document.querySelector(".sidebar").hidden = true;
    document.querySelector(".topbar").hidden = true;
    document.querySelector(".app").classList.add("app--auth");
}

// Tilføjer sidebaren, topbaren og kolonnerne (fra .app) igen
export function leaveAuthLayout() {
    document.querySelector(".sidebar").hidden = false;
    document.querySelector(".topbar").hidden = false;
    document.querySelector(".app").classList.remove("app--auth");
}

// Opretter et ikon fra ikonarket (/img/icons.svg) og returnerer det som et <svg>-element, klar til at blive sat ind på siden.
function createIcon(name, extraClass = "") {
    const svg = document.createElementNS(SVG_NS, "svg");
    svg.setAttribute("class", `icon ${extraClass}`.trim());

    const use = document.createElementNS(SVG_NS, "use");
    use.setAttribute("href", `/img/icons.svg#i-${name}`);

    svg.append(use);
    return svg;
}

// Bygger brand feltet over formen
function buildBrand() {
    const header = document.createElement("header");
    header.className = "auth-brand";

    const mark = document.createElement("img");
    mark.className = "brand-mark";
    mark.src = "/img/hokuto-kai-maerke.svg";
    mark.alt = "";

    const name = document.createElement("div");
    name.className = "brand-name";
    name.textContent = "Hokuto-Kai";

    const tagline = document.createElement("span");
    tagline.className = "text-muted text-sm";
    tagline.textContent = "Medlemssystem";

    header.append(mark, name, tagline);
    return header;
}

// Bygger fejlbesked boksen
function buildAlert() {
    const box = document.createElement("div");
    box.className = "alert alert--danger";
    box.setAttribute("role", "alert");
    box.hidden = true;

    const title = document.createElement("p");
    title.className = "alert__title";
    title.textContent = "Kunne ikke logge ind";

    const text = document.createElement("p");
    text.className = "alert__text";
    text.textContent = "E-mail eller adgangskode er forkert. Prøv igen.";

    // Titel og tekst ligger i én div, så de fylder én kolonne ved siden af ikonet
    const content = document.createElement("div");
    content.append(title, text);

    box.append(createIcon("alert", "alert__icon"), content);

    return { box, text };
}

// Bygger et felt
function buildField(field) {
    const wrapper = document.createElement("div");
    wrapper.className = "field";

    const star = document.createElement("span");
    star.className = "req";
    star.textContent = " *";

    const label = document.createElement("label");
    label.className = "label";
    label.htmlFor = field.id;
    label.append(field.label, star);

    const input = document.createElement("input");
    input.className = "input";
    input.id = field.id;
    input.name = field.name;
    input.type = field.type ?? "text";
    input.placeholder = field.placeholder ?? "";
    input.autocomplete = field.autocomplete;
    input.maxLength = field.maxLength;
    input.required = true;

    const error = document.createElement("small");
    error.className = "error-msg";
    error.hidden = true;

    wrapper.append(label, input, error);
    return wrapper;
}

// Bygger opret konto noten i bunden af siden
function buildSignupNote() {
    const note = document.createElement("p");
    note.className = "auth-note";
    note.textContent = "Ny i klubben? ";

    const link = document.createElement("a");
    link.textContent = "Opret konto";

    link.href = "kommer senere";
    link.target = "_blank";
    link.rel = "noopener noreferrer";

    note.append(link);
    return note;
}


// Bygger selve siden
export function LoginPage() {
    enterAuthLayout();

    const page = document.createElement("div");
    page.className = "auth-shell";

    // Kortet med formularen
    const form = document.createElement("form");
    form.className = "card auth-card";

    const body = document.createElement("div");
    body.className = "card__body stack"; // .stack giver ens afstand

    const title = document.createElement("h1");
    title.id = "login-title";
    title.textContent = "Log ind";

    const loginAlert = buildAlert();

    const submitBtn = document.createElement("button");
    submitBtn.className = "btn btn--block btn--primary";
    submitBtn.type = "submit";
    submitBtn.textContent = "Log ind";

    // Rækkefølgen her er rækkefølgen på siden
    body.append(title, loginAlert.box, ...FIELDS.map(buildField), submitBtn);
    form.append(body);

    page.append(buildBrand(), form, buildSignupNote());

    initLoginForm(form, loginAlert); // får formularen til at sende login

    return page;
}

