import {sendJSON} from "./api.js";
import {router} from "./router.js";
import {clearUser} from "./session.js";

// Elementerne fra index.html, som filen styrer
const sidebar = document.querySelector("#sidebar");
const scrim = document.querySelector("#scrim"); // mørk baggrund bag menuen på mobil
const toggle = document.querySelector("#menu-toggle");
const logoutButtons = document.querySelectorAll(".logout-button"); // én i sidebaren, én i topbaren

// Åbner (true) eller lukker (false) menuen på mobil
function setOpen(open) {
    // CSS'en skubber sidebaren ind på skærmen, når den har klassen is-open
    sidebar.classList.toggle("is-open", open);
    scrim.hidden = !open;
}

// Menuknappen åbner menuen, hvis den er lukket, og lukker den, hvis den er åben
toggle.addEventListener("click", () => setOpen(!sidebar.classList.contains("is-open")));

// Klik på den mørke baggrund lukker menuen
scrim.addEventListener("click", () => setOpen(false));

// Klik på et link eller en knap i menuen lukker den
sidebar.addEventListener("click", (event) => {
    if (event.target.closest("a, button")) setOpen(false);
}, true);

// Escape lukker menuen
document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") setOpen(false);
});

// "Log ud": send kaldet til serveren, glem brugeren, og gå derefter til login-siden
async function logOut() {
    try {
        await sendJSON("POST", "/api/logout");
        clearUser();
        router.navigate("/login");
    } catch (error) {
        // Logud fejlede, så brugeren bliver på siden
        console.error(error);
    }
}

for (const button of logoutButtons) {
    button.addEventListener("click", logOut);
}