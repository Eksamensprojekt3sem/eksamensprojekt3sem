import {pages, NotFoundPage} from "./pages.js";
import {leaveAuthLayout} from "../auth/login-page.js";
import {getUser, loadUser} from "./session.js";

// Routeren bruger Navigo, som er indlæst som script i index.html.
// Eksporteres, så andre filer (fx shell.js og api.js) kan skifte side.
export const router = new Navigo("/");

// Bygger menuen i sidebaren ud fra listen over sider i pages.js
function renderNav() {
    const list = document.querySelector("#nav");
    list.replaceChildren(); // tøm listen, så menuen ikke bliver dobbelt

    for (const page of pages) {
        // Sider med nav: false skal ikke vises i menuen
        if (!page.nav) {
            continue;
        }

        const a = document.createElement("a");
        a.className = "nav__link";
        a.href = page.path;
        a.append(createIcon(page.icon), page.label); // ikon efterfulgt af teksten
        a.setAttribute("data-navigo", ""); // får Navigo til at håndtere klikket uden at genindlæse siden

        const li = document.createElement("li");
        li.append(a);
        list.append(li);
    }
}

// Giver det menupunkt, der svarer til den aktuelle side, klassen is-active
function markActiveLink() {
    const links = document.querySelectorAll(".nav__link");

    for (const a of links) {
        a.classList.toggle("is-active", a.pathname === location.pathname);
    }
}

// Laver et ikon fra filen img/icons.svg, fx createIcon("i-home")
function createIcon(name) {
    // SVG-elementer skal laves med createElementNS, ellers vises de ikke
    const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute("class", "icon");

    const use = document.createElementNS("http://www.w3.org/2000/svg", "use");
    use.setAttribute("href", "/img/icons.svg#" + name);

    svg.append(use);
    return svg;
}

// Viser en side i #app
async function render(pageComponent, data) {
    const app = document.querySelector("#app");

    leaveAuthLayout();
    const el = await pageComponent(data); // siden bygges (nogle venter på data) --- siden får fx { id: "1" }

    app.replaceChildren(el); // erstat det gamle indhold med den nye side

    // Links på den nye side skal også håndteres af Navigo
    router.updatePageLinks();
    markActiveLink();
}

// Må brugeren se siden? Ikke logget ind sendes til login, og logget ind skal man ikke se login igen
// Returnerer false, hvis brugeren er sendt et andet sted hen
function allowed(page) {
    if (!getUser() && !page.public) {
        router.navigate("/login");
        return false;
    }

    if (getUser() && page.path === "/login") {
        router.navigate("/");
        return false;
    }

    return true;
}

// Menuen bygges, før routeren starter, så linkene er med, når siden vises første gang
renderNav();

// En rute for hver side i listen. Vagten tjekker, før siden vises
for (const page of pages) {
    router.on(page.path, ({data}) => {
        if (allowed(page)) {
            render(page.render, data);
        }
    });
}

// Adresser, der ikke findes, viser "Siden findes ikke". Uden login går man til login i stedet
router.notFound(() => {
    if (getUser()) {
        render(NotFoundPage);
    } else {
        router.navigate("/login");
    }
});

// Find ud af, om der allerede er en session, før routeren starter.
// Hent brugeren først, så allowed ved, om man er logget ind
await loadUser();

// Find ud af, hvilken side adressen i browseren hører til, og vis den
router.resolve();