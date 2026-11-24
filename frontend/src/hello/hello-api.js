export async function fetchGreetings(){
    const response = await fetch("/api/greetings");

    if(!response.ok){
        throw new Error("Kunne ikke hente hilsner (status " + response.status + ")");
    }

    return await response.json();
}