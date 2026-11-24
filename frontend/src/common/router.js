import { showGreetings } from "../hello/hello-list.js";

const router = new Navigo("/");

async function render(pageComponent) {
    const app = document.querySelector("#app");
    const el = await pageComponent();

    app.replaceChildren(el);
    router.updatePageLinks();
}

async function HomePage() {
    const div = document.createElement("div");
    const h1 = document.createElement("h1");
    h1.textContent = "Velkommen til Hokuto-Kai";
    const p = document.createElement("p");
    p.textContent = "Her kommer klubbens nye system.";

    const greetings = document.createElement("div");
    await showGreetings(greetings);

    div.append(h1, p, greetings);
    return div;
}

function NotFoundPage() {
    const div = document.createElement("div");
    const h1 = document.createElement("h1");
    h1.textContent = "Siden findes ikke";
    const a = document.createElement("a");
    a.href = "/";
    a.textContent = "Gå til forsiden";
    a.setAttribute("data-navigo", "");
    div.append(h1, a);
    return div;
}

router
    .on("/", () => render(HomePage))
    .notFound(() => render(NotFoundPage))
    .resolve();