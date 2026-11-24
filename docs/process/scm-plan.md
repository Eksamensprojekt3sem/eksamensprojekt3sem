# SCM-plan (konfigurationsstyring)

Planen beskriver, hvordan vi holder styr på ændringer i projektet. Den bygger på vores GitHub-flow. `CONTRIBUTING.md` er den praktiske vejledning til udviklere (hvordan man gør), mens denne plan er den styrende beskrivelse (hvad der gælder, og hvorfor). Ved uoverensstemmelse gælder denne plan.

## 1. Identifikation af konfigurationsobjekter
Det, vi holder styr på og versionsstyrer i Git og GitHub:

| Objekt | Hvor | Eksempel |
|---|---|---|
| Kode | `src/main/java` (backend), `frontend/` (frontend) | `MemberService.java`, `member-api.js` |
| Databasestruktur | Entity-klasser (JPA genererer tabeller ud fra dem) og repositories i modulets mappe | `Member.java`, `MemberRepository.java` |
| Tests | `src/test` | `MemberServiceTest.java` |
| Konfiguration | `pom.xml` (afhængigheder), `application.properties` (databaseopsætning m.m.), `.env.example` | Hemmeligheder ligger kun i `.env`, som ikke er i Git |
| Dokumentation | `docs/`, `README.md`, `CONTRIBUTING.md`, guides | `product-backlog.md`, denne plan |
| Krav og opgaver | GitHub Issues (user stories og tasks) | `US-001: Logge ind og ud` |
| Proces | `.github/` (issue- og PR-skabeloner, workflows) | `pull_request_template.md` |

Ændringer i entity-klasser er ændringer i databasestrukturen og behandles med ekstra omhu i review, fordi de kan påvirke eksisterende data.

**Placering og kodenavne.** Hvert modul har sin egen mappe (fx `member/`). Klasser følger faste mønstre: `<Feature>Api`, `<Feature>Info` og `<Klasse>Test`.

## 2. Versionsstyring og branch-strategi
- Vi bruger Git, og `main` er altid stabil og kan bygges og køre. Ingen arbejder direkte på den.
- Én branch pr. issue, oprettet fra issuet: `<type>/<issue-nr>-<kort-navn>` (`feature/`, `fix/`, `docs/`, `chore/`). Eksempel: `feature/142-login-endpoint`.
- Branches er korte og slettes efter merge.

## 3. Navngivning
| Hvad | Mønster | Eksempel |
|---|---|---|
| User story | `US-<nr>: <titel>` | `US-001: Logge ind og ud` |
| Task | `US-<nr>-T<nr>: <beskrivelse>` | `US-001-T3: Opret login-endpoint` |
| Branch | `<type>/<issue-nr>-<kort-navn>` | `feature/142-login-endpoint` |
| Commit | Engelsk, bydeform | `Add login endpoint` |
| PR-titel | `<Imperativ> (US-<nr>-T<nr>)` | `Add login endpoint (US-001-T3)` |
| Baseline | `sprint-<n>` | `sprint-2` |

**User story-format.** I issuets beskrivelse skrives en user story som "Som [rolle] vil jeg [mål], så [formål]" og får acceptkriterier.

**Sammenhæng mellem de to nummereringer.** Hver user story og hver task er et GitHub-issue med et automatisk issue-nummer (fx #142). `US-001-T3` er navnet i issuets titel, og issue-nummeret bruges i branch og `Closes #142`. Man kan derfor altid slå fra det ene til det andet.

## 4. Change control (review og godkendelse)
En ændring følger altid samme vej: issue → branch → Pull Request → review → merge. Det gælder ændringer i kode, tests, konfiguration og dokumentation.

1. Ingen ændring uden issue.
2. PR'en følger skabelonen og har `Closes #<issue-nr>`.
3. Build og tests (GitHub Actions) skal være grønne, før PR'en kan merges. Det er et påkrævet statustjek på `main`.
4. Mindst én anden end forfatteren godkender (ruleset på `main`, forældede godkendelser afvises, og samtaler skal være løst).
5. Forfatteren merger `main` ind i sin branch først, så konflikter løses af den, der kender ændringen.
6. PR'en squash-merges, og branchen slettes.
7. En PR uden tests for ny logik godkendes ikke.

Ændringer til krav i et sprint sker kun via Product Owner, og de lægges i product backlog til næste sprint.

## 5. Baselines
En baseline er en milepæl, hvor en version af projektet er gennemgået og godkendt, og som herefter er grundlaget for videre udvikling. En baseline kan kun ændres via change control (issue, PR og review).

Ved hvert sprints slutning tagges `main`:

```bash
git tag -a sprint-<n> -m "Sprint <n> baseline"
git push origin sprint-<n>
```

Tagget er en fast version, vi kan vende tilbage til, og det er den version, der demonstreres ved sprint review. Til tagget kan der oprettes en GitHub Release. Den sidste baseline er afleveringsversionen.

Et tag flyttes aldrig. Opdages en fejl i en baseline, rettes den via et nyt issue og en ny PR og kommer med i næste baseline.

## 6. Sporbarhed
User story → task → branch (koblet til issuet) → commits → PR → test. Alt linker til hinanden via issue-nummeret, så vi kan gå fra et krav til den kode og de tests, der opfylder det, og omvendt.

## 7. Status accounting
Status accounting handler om at kunne se, hvad status er nu, og hvad der er ændret.

- **Status nu:** GitHub Project viser status for hver story og task (Product backlog, Sprint backlog, In progress, Ready for review, In review, Done).
- **Hvad der er ændret:** Ændringshistorikken kan ses via de lukkede issues, de mergede PR'er og forskellen mellem to baselines, fx en compare på GitHub mellem `sprint-1` og `sprint-2` eller `git log sprint-1..sprint-2`.

## 8. Audit
- **Løbende:** Alle PR'er reviewes af en anden, før de merges, så vi sikrer, at koden virker, har tests og følger reglerne.
- **Før hver baseline:** Vi kontrollerer, at alle issues markeret Done i sprintet er merget, at testene er grønne på `main`, og at dokumentationen passer til koden.
- **Retrospective:** Ved hvert sprints afslutning vurderer vi, om vi har fulgt planen, og om den skal justeres.
