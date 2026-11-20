# Sådan lever vi op til pensum og eksamen

Opgaven siger: **processen styrer projektet**, valg skal **begrundes**, og vi skal løbende **dokumentere**. Det er ikke nok at beskrive en model bagefter.

Denne side oversætter pensum til *konkrete ting vi gør* og *bevis vi kan pege på til eksamen*.

> Husk fra "Gode råd": brug fagbegreberne, men kan du ikke huske ordet, så forklar det med dine egne ord – det er forståelsen, der tæller. Til eksamen kan vi også blive spurgt om emner, vi **ikke** har brugt. Fravalg skal vi kunne begrunde.

---

## Oversigt: emne → hvad vi gør → bevis

| Emne | Hvad vi gør i projektet | Bevis (artefakt) |
|------|------------------------|------------------|
| **Udviklingsmodel** | Vælger og tilpasser model i Sprint 0; bruger den aktivt (sprints, events, backlog) | `docs/process/udviklingsmodel.md`, milestones, retrospectives |
| **Agile principper** | Korte iterationer, kundefeedback i review, tilpasning af planen | Sprint reviews, ændringer i backlog |
| **Projektplanlægning/-styring** | Product backlog, MoSCoW, estimering, sprint planning, GitHub Projects | Projects-board, milestones, estimater på tasks |
| **Risikostyring** | Risikoanalyse (S × K = eksponering), RMMM-plan, opfølgning hver retro | `risikoanalyse.md` (opdateret pr. sprint) |
| **SQA** (kvalitetssikring) | Code review på alle PR'er, tests, DoD, coding standards | PR-reviews, CI-resultater, `sqa-plan.md` |
| **SCM** (konfigurationsstyring) | Se detaljer nedenfor | Branches, commits, PR'er, tags, `scm-plan.md` |
| **SPI** (procesforbedring) | Retrospective hver sprint → konkrete forbedringer → tjek om de virkede | `docs/process/sprint-N.md` |
| **Metrikker** | Vælger få proces-/projekt-/produktmetrikker og måler dem pr. sprint | `metrikker.md` |
| **Vedligeholdelse** | Coding standards, modulær arkitektur, dokumentation, refaktorering | `CONTRIBUTING.md`, struktur i koden |

---

## SCM – hvordan vores GitHub-flow *er* konfigurationsstyring

SCM handler typisk om: **identifikation, change control, versionsstyring, status accounting og audit/review**. Vores flow dækker dem alle:

| SCM-aktivitet | Hos os |
|---------------|--------|
| **Identifikation af konfigurationsobjekter** | Kode, tests, docs, issues. Faste navne (`feature/12-…`, `MemberService`), faste mapper. |
| **Versionsstyring** | Git; én branch pr. issue; `main` er altid stabil. |
| **Change control** (kontrolleret ændring) | Ændring = issue → branch → PR → review → merge. Ingen direkte ændringer på `main`. |
| **Sporbarhed (traceability)** | Krav (user story) → task → branch (koblet til issuet) → commits → PR (`Closes #nr`) → test. Alt linker til hinanden. |
| **Baselines** | `git tag sprint-N` ved sprint-slut (+ evt. GitHub Release). |
| **Status accounting** | GitHub Projects-board + issue-status viser, hvad der er ændret, og i hvilken tilstand. |
| **Audit / review** | PR-review med tjekliste; retrospective gennemgår processen. |

**Det betyder, at skabelonerne og reglerne ikke er bureaukrati – de er vores SCM-bevis.** Hvis vi følger dem, kan vi til eksamen vise et konkret krav hele vejen til den kode, der opfylder det.

---

## SQA – hvordan vi sikrer kvalitet

- **Forebyggende:** coding standards, Definition of Ready/Done, commit-regler.
- **Opdagende:** code review (alle PR'er), tests, evt. CI-build.
- **Evaluerende:** retrospective, metrikker (fx fejl pr. sprint, review-tid, testdækning).

---

## Forslag til metrikker (vælg 3–5, ikke alle)

| Type | Metrik | Hvor læser vi det |
|------|--------|-------------------|
| **Proces** | Hvor lang tid en PR venter på review | `gh pr list --state merged --json createdAt,mergedAt` |
| **Proces** | Antal commits/PR'er pr. person pr. sprint | `git shortlog -sn --since="1 week ago"` |
| **Projekt** | **Velocity** (estimerede timer færdiggjort pr. sprint) | GitHub Projects |
| **Projekt** | **Burndown** (resterende points) | Projects-board / egen tabel |
| **Projekt** | Estimeret vs. faktisk tid | Issues |
| **Produkt** | Antal bugs fundet (`bug`-label) | `gh issue list --label bug --state all` |
| **Produkt** | Testdækning, antal tests | Maven/JaCoCo |

Skriv i `metrikker.md`: *hvad* vi måler, *hvorfor*, og *hvad vi lærte*. Det er den sidste del, der giver point.

---

## Dokumentér beslutninger (let version)

Når vi træffer en vigtig beslutning (model, arkitektur, fravalg), så skriv 3–5 linjer i `docs/decisions/NNN-titel.md`:

```markdown
# 001 – Use modular monolith instead of microservices
Date: <dato>
Context: 4 weeks, 4 developers, one customer.
Decision: One Spring Boot app, split into feature modules.
Alternatives: Microservices (too much overhead), layered monolith (spaghetti risk).
Consequences: Simple deployment; module boundaries need discipline (see coding standards).
```

Det giver os præcis det, eksaminator spørger efter: *hvorfor*, *alternativer* og *konsekvenser*.

---

## Forberedelse til eksamen (i grupper)

For hvert emne, stil jer selv disse 5 spørgsmål (fra "Gode råd"):

1. Hvad går emnet ud på, og hvad er formålet?
2. Hvilke centrale fagbegreber hører til?
3. Hvordan og hvornår anvendes det?
4. Hvordan har vi anvendt det – eller hvordan *kunne* vi have gjort?
5. Hvilke styrker, svagheder og begrænsninger har det?

**Øv jer i at forklare højt for hinanden**, uden slides. Den individuelle præsentation er **ca. 9 minutter** – hold fokus på faglighed, brug eksempler fra vores artefakter, og undgå bare at genfortælle, hvad vi har programmeret.

**Typiske eksamensspørgsmål vi kan forberede:**
- Hvorfor valgte I netop den model, og hvad ville I gøre anderledes?
- Hvordan har jeres risikoanalyse ændret sig undervejs?
- Hvordan sikrer jeres SCM-proces sporbarhed fra krav til kode?
- Hvad viser jeres metrikker, og hvad gjorde I ved det?
- Hvilke emner valgte I fra, og hvordan kunne de have hjulpet?
