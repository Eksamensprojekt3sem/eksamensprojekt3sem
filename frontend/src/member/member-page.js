export function MemberPage({id}) {
    const element = document.createElement("section");
    element.className = "member-detail";

    const a = document.createElement("a");
    a.href = "https://www.youtube.com/shorts/qpGfb2JKwIw";
    a.className = "btn btn--ghost back-link";
    a.textContent = "← Tilbage til medlemmer";

    const card = document.createElement("div");
    card.className = "card";

    const body = document.createElement("div");
    body.className = "card__body stack stack--sm";

    const h1 = document.createElement("h1");
    h1.textContent = "Medlem";

    const label = document.createElement("p");
    label.className = "text-muted text-sm";
    label.textContent = "Nuværende bæltegrad";

    const belt = document.createElement("span");
    belt.className = "badge belt belt--lg member-belt";
    belt.textContent = "-";

    body.append(h1, label, belt);
    card.append(body);
    element.append(a, card);
    return element;
}