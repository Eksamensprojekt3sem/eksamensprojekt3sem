export function MemberPage({id}) {
    const element = document.createElement("section");

    const a = document.createElement("a");
    a.href = "/members";
    a.textContent = "← Tilbage til medlemmer";
    a.setAttribute("data-navigo", "");

    const h1 = document.createElement("h1");
    h1.textContent = "Medlem";

    element.append(a, h1);
    return element;
}