import { pages, NotFoundPage } from "./pages.js";

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

// Viser en side i #app
async function render(pageComponent, data) {
    const app = document.querySelector("#app");
    const el = await pageComponent(data); // siden bygges (nogle venter på data) --- siden får fx { id: "1" }

    app.replaceChildren(el); // erstat det gamle indhold med den nye side

    // Links på den nye side skal også håndteres af Navigo
    router.updatePageLinks();
    markActiveLink();
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

// Menuen bygges, før routeren starter, så linkene er med, når siden vises første gang
renderNav();

// En rute for hver side i listen
for (const page of pages) {
    router.on(page.path,({data}) => render(page.render, data));
}

// Adresser, der ikke findes, viser "Siden findes ikke"
router.notFound(() => render(NotFoundPage));

// Find ud af, hvilken side adressen i browseren hører til, og vis den
router.resolve();