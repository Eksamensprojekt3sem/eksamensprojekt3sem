import { fetchGreetings } from "./hello-api.js";

export async function showGreetings(container){
    try {
        const greetings = await fetchGreetings();

        const list = document.createElement("ul");

        for (const greeting of greetings){
            const item = document.createElement("li");
            item.textContent = greeting.message;
            list.appendChild(item);
        }

        container.appendChild(list);
    } catch (error){
        const errorText = document.createElement("p");
        errorText.classList.add("error");
        errorText.textContent = error.message;
        container.appendChild(errorText);
    }
}