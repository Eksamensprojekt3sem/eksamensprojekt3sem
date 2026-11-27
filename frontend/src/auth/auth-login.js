import {login} from "./auth-api.js";
import {loadUser} from "../common/session.js";
import {router} from "../common/router.js";

const LOGIN_ERROR = "Forkert e-mail eller adgangskode";

// Får login-formularen til at virke. Kaldes fra LoginPage, når siden er bygget
export function initLoginForm(form, loginAlert) {
    const submitBtn = form.querySelector("button[type='submit']");

    // Slår knappen fra og viser en spinner, mens vi venter på serveren
    function setLoading(isLoading) {
        submitBtn.disabled = isLoading;

        if (isLoading) {
            const spinner = document.createElement("span");
            spinner.className = "spinner";

            submitBtn.replaceChildren(spinner, "Logger ind…");
        } else {
            submitBtn.replaceChildren("Log ind");
        }
    }

    form.addEventListener("submit", async (event) => {
        event.preventDefault(); // siden må ikke genindlæses
        hideLoginErrors(loginAlert);

        const data = new FormData(form);

        setLoading(true);

        try {
            await login(data.get("email"), data.get("password"));
            await loadUser(); // routeren skal vide, hvem der er logget ind, før vi går videre
            router.navigate("/");
        } catch (error) {
            showLoginErrors(loginAlert, error);
        } finally {
            setLoading(false); // også ved fejl, ellers bliver knappen ved med at snurre
        }
    });
}

// Skjuler fejlen fra sidste forsøg
function hideLoginErrors(loginAlert) {
    loginAlert.box.hidden = true;
}

// 401 er forkert login. Andre fejl (fx serveren er nede) viser deres egen tekst
function showLoginErrors(loginAlert, error) {
    loginAlert.text.textContent = error.status === 401 ? LOGIN_ERROR : error.message;
    loginAlert.box.hidden = false;
}