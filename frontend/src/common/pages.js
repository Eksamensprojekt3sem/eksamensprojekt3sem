import { HomePage } from "../home/home-page.js";
import { LoginPage } from "../auth/login-page.js";

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

// Hver side: sti, tekst i menuen, om den skal vises i menuen, og funktionen der bygger den
export const pages = [
    { path: "/", label: "Oversigt", icon: "i-home", nav: true, render: HomePage },
    { path: "/login", label: "Log ind", icon: "i-person", nav: false, render: LoginPage },
];