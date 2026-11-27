export function HomePage() {
    const page = document.createElement("div");
    page.className = "stack stack--lg";

    const header = document.createElement("header");
    header.className = "page-header";

    const titleBox = document.createElement("div");
    const h1 = document.createElement("h1");
    h1.textContent = "Oversigt";
    const p = document.createElement("p");
    p.textContent = "Velkommen til Hokuto-Kai";
    titleBox.append(h1, p);
    header.append(titleBox);

    // Tom plads til rolle-menuen. Punkterne tilføjes i en senere opgave
    const menu = document.createElement("nav");
    menu.className = "home-menu";
    menu.setAttribute("aria-label", "Rolle-menu");

    page.append(header, menu);
    return page;
}