import { showGreetings } from "../hello/hello-list.js";
import { MemberPage } from "../member/member-page.js";

export async function HomePage() {
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

export function NotFoundPage() {
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

export function LoginPage() {
    const div = document.createElement("div");
    const h1 = document.createElement("h1");
    h1.textContent = "Log ind";
    const p = document.createElement("p");
    p.textContent = "Login-siden kommer snart.";
    div.append(h1, p);
    return div;
}

// Hver side: sti, tekst i menuen, om den skal vises i menuen, og funktionen der bygger den
export const pages = [
    { path: "/", label: "Forside", icon: "i-home", nav: true, render: HomePage },
    { path: "/login", label: "Log ind", icon: "i-person", nav: true, render: LoginPage },
    { path: "/members/:id", label: "Medlem", icon: "i-user", nav: false, render: MemberPage },
];