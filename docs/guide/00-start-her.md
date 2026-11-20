# Sprint 0 – fundamentet

**Formål:** Gøre klar, så Sprint 1 kan starte med at levere værdi. I Sprint 0 leverer vi *ikke* features, men **aftaler, plan og et kørende skelet**.

**Tidsboks:** Ca. 10–15 % af projektperioden (ved 4 uger: ca. 2–3 arbejdsdage). Hold den stram – Sprint 0 må ikke æde projektet.

> **Eksamens-pointe:** Opgaven siger, at udviklingsmodellen *aktivt skal styre* projektet – det er ikke nok at beskrive den bagefter. Sprint 0 er stedet, hvor vi vælger og indstiller modellen, **før** vi skriver kode.

---

## Overblik

```
A. Aftaler ──► B. Værktøj ──► C. Analyse & plan ──► D. Teknisk skelet ──► E. Sprint 1-planlægning
```

Hver del har en **checkliste** og et **output** (artefakt), som vi gemmer i `docs/process/`.

---

## A. Aftaler (dag 1)

- [ ] **Gruppekontrakt** *(krav fra opgaven – skal være på plads før projektstart)*. Skabelon nederst.
- [ ] **Vælg udviklingsmodel** (fx Scrum, evt. kombineret med elementer fra XP/Kanban). Skriv 5–10 linjer i `docs/process/udviklingsmodel.md`:
    - Hvorfor passer modellen til *vores* projekt (størrelse, kunde, usikkerhed, 4 uger)?
    - Hvordan har vi **tilpasset** den (sprintlængde, hvilke events, hvilke roller)?
    - Hvad **fravælger** vi, og hvorfor?
    - Hvilke styrker/svagheder forventer vi? *(Vi tjekker op på det i retrospective og til eksamen.)*
- [ ] **Roller:** Product Owner, Scrum Master (kan rotere), udviklere. Hvem er kontaktperson til kunden?
- [ ] **Rytme:** sprintlængde (forslag: 1 uge), daglig stand-up (kort, fast tid), sprint review, retrospective.
- [ ] **Kommunikation:** hvor snakker vi (Teams/Discord)? Hvor lever beslutninger (GitHub – ikke i chatten)?
- [ ] **Working agreements:** mini-kontrakt for koden – "Vi laver PR'er, vi reviewer inden for 24 timer, vi skriver tests …" (peg på `CONTRIBUTING.md`).

**Output:** `gruppekontrakt.md`, `udviklingsmodel.md`

---

## B. Værktøj (dag 1 – alle, parallelt)

Hver person gør dette:

- [ ] Installér Git + `gh` → [06-installer-gh-cli.md](06-installer-gh-cli.md)
- [ ] Gør GitHub-mail privat og sæt `git config` → [05-privat-github-mail.md](05-privat-github-mail.md)
- [ ] `gh auth login`
- [ ] Klon repoet:
  ```bash
  gh repo clone <org>/<repo>
  cd <repo>
  ```
- [ ] Prøv hele flowet på et lille testissue: opret issue (GitHub) → `gh issue develop` → commit → `gh pr create --base main` → review og merge (GitHub). Slet bagefter.

**Output:** Alle kan det samme flow. (Det er vores *SCM-proces* i praksis.)

---

## C. Analyse og planlægning (dag 1–2)

- [ ] **Kunde & vision:** Hvem er kunden? Hvad er problemet? 3–5 linjers vision i `README.md`.
- [ ] **Interessenter & brugere:** hvem bruger systemet (roller/personas)?
- [ ] **Product backlog:** skriv **user stories** som issues på GitHub (*Issues → New issue → User story*). Ingen trimning endnu – saml det hele.
- [ ] **Acceptkriterier** på hver story (Given/When/Then – se skabelonen).
- [ ] **Prioritering:** MoSCoW (Must/Should/Could/Won't) på alle stories. Won't-listen er også et resultat – det er et *begrundet fravalg*.
- [ ] **Estimering:** Planning Poker i timer (1 / 2 / 4 / 8 – alle viser tal samtidig). Estimatet sættes på **tasks**, ikke på storyen (storyen har kun en grov størrelse).
- [ ] **Risikoanalyse:** lav en tabel i `docs/process/risikoanalyse.md`:

  | ID | Risiko | Sandsynlighed (1–5) | Konsekvens (1–5) | Eksponering (S×K) | Forebyggelse | Beredskab |
    |----|--------|--------------------|------------------|-------------------|--------------|-----------|

  Lav en **RMMM-plan** (Risk Mitigation, Monitoring, Management) for de 3–5 største risici. Gennemgå listen igen i hver retrospective.
- [ ] **SQA-plan** (kvalitetssikring, 1 side): code reviews på alle PR'er, tests, Definition of Done, coding standards, hvilken test-dækning vi sigter mod.
- [ ] **SCM-plan** (konfigurationsstyring, 1 side): hvad er konfigurationsobjekter (kode, docs, issues), branch-strategi, navngivning, review/change control, baselines (tags pr. sprint). *Se [07-pensum-og-eksamen.md](07-pensum-og-eksamen.md).*
- [ ] **Metrikker:** vælg 3–5 og aftal hvor vi læser dem (se listen i 07).

**Output:** Product backlog (issues), `risikoanalyse.md`, `sqa-plan.md`, `scm-plan.md`, `metrikker.md`

---

## D. Teknisk skelet (dag 2–3)

Mål: et **"walking skeleton"** – den tyndeste mulige gennemgang af hele systemet, der kører.

- [ ] Opret Spring Boot-projekt (Spring Initializr) med afhængigheder aftalt i gruppen.
- [ ] Opret **package by feature** med tom struktur (inkl. `<Feature>Api`) → [02-coding-standards.md](02-coding-standards.md)
- [ ] Opret `common/` med `ApiExceptionHandler` og de tre exceptions (`NotFoundException`, `ConflictException`, `InvalidInputException`).
- [ ] Forbind til database (lokalt – fx H2 eller MySQL i Docker). Brug `application.properties` med pladsholdere (fx `${DB_URL}`), og læg de rigtige værdier i `.env` (står i `.gitignore`). `.env.example` med samme nøgler, men uden værdier, ligger i Git; **ingen hemmeligheder i Git.**
- [ ] Én lille "hello"-feature igennem alle lag (entity → repository → service → controller → visning) + 1 test.
- [ ] **Build-tjek** (GitHub Actions kører `mvn verify` på hver PR), hvis I har tid/behov.
- [ ] Alle kan køre projektet lokalt efter at have læst `README.md`.

**Output:** Kørende skelet på `main`, tagget `sprint-0`.

---

## E. Planlæg Sprint 1 (slutningen af Sprint 0)

- [ ] Aftal **Definition of Ready** og **Definition of Done** (forslag nedenfor).
- [ ] Vælg **sprint goal** (én sætning).
- [ ] Vælg stories fra toppen af backlog (ud fra prioritet og vores kapacitet).
- [ ] Bryd dem ned i **tasks** (skabelonen *Task* – oprettes på GitHub). Hver task skal kunne færdiggøres på 1–8 timer.
- [ ] Sæt issues i GitHub Projects (kolonner: `Backlog → Sprint backlog → In progress → In review → Done`).
- [ ] Giv hver task en ansvarlig (assignee).
- [ ] Opret milestone `Sprint 1` og tilføj issues.

---

## Definition of Ready (en story må kun komme i sprintet, hvis …)

- [ ] Den er skrevet som *As a … I want … so that …*
- [ ] Den har acceptkriterier
- [ ] Den er estimeret
- [ ] Den er prioriteret
- [ ] Gruppen forstår den (ingen åbne spørgsmål)

## Definition of Done (en task/story er først færdig, når …)

- [ ] Koden følger vores [coding standards](02-coding-standards.md)
- [ ] Der er skrevet/opdateret tests, og de er grønne
- [ ] Koden bygger lokalt (`mvn verify`)
- [ ] PR er reviewet og godkendt af mindst 1 anden
- [ ] Acceptkriterierne er opfyldt og tjekket af en anden end udvikleren
- [ ] PR er merget til `main` og branchen er slettet
- [ ] Issuet er lukket (via `Closes #nr`)
- [ ] Relevant dokumentation er opdateret

---

## Sprint-ritualer (gentages hver sprint)

| Event | Hvornår | Hvad | Bevis til eksamen |
|-------|---------|------|-------------------|
| Sprint planning | Start | Sprint goal + tasks | Milestone + issues |
| Daily stand-up | Dagligt, 10 min | I går / i dag / blokeringer | (Valgfrit: kort log) |
| Sprint review | Slut | Demo for kunde/PO | Notat i `docs/process/sprint-N.md` |
| Retrospective | Slut | Hvad gik godt/skidt/ændrer vi? | **SPI** – forbedringspunkter, som vi *faktisk* tager med |
| Baseline | Slut | `git tag sprint-N` | SCM – baseline |

---

## Bilag: Gruppekontrakt (skabelon)

Kopiér til `docs/process/gruppekontrakt.md`. Hold den kort – 1 side.

```markdown
# Gruppekontrakt

Gruppe: <navn>      Dato: <dato>      Medlemmer: <navne>

## Mål og ambition
- Vi sigter mod karakter/ambition: ...

## Arbejdsfordeling og roller
- Product Owner: ...   Scrum Master (roterer hver sprint): ...
- Kontaktperson til kunden: ...

## Forventninger til deltagelse
- Mødetider (fast): ...
- Hvis man er forhindret, giver man besked senest: ...
- Forventet arbejdsindsats pr. uge: ...

## Kommunikation
- Kanal til hurtig snak: ...   Beslutninger skrives i: GitHub (issue/PR)
- Svartid på beskeder: ...

## Beslutninger
- Vi træffer beslutninger ved: konsensus / flertal / ...
- Ved uenighed: ...

## Kvalitet og samarbejde
- Vi følger CONTRIBUTING.md (PR'er, review inden 24 timer, tests).
- Alle bidrager til kode OG dokumentation.

## Konflikter og konsekvenser
- Hvis aftaler brydes: 1) snak 1-til-1, 2) tag det på gruppemødet, 3) kontakt underviser.

## Underskrifter
<navne + dato>
```