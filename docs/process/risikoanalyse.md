# Risikoanalyse og RMMM

Vi arbejder proaktivt med risici: Vi identificerer dem, vurderer sandsynlighed og konsekvens, prioriterer dem og har en plan for at forebygge, overvåge og håndtere dem, før de rammer os. Dokumentet har tre dele:

1. Metode og skalaer
2. Risikotabel (sorteret efter prioritet, med cutoff-linje)
3. RMMM-plan (Risk Mitigation, Monitoring and Management) for hver risiko

## 1. Metode og skalaer

**Identifikation.** Vi har identificeret risici ud fra en tjekliste over risikoområder: produktstørrelse, forretningspåvirkning, interessenter (kunde og brugere), proces, udviklingsmiljø, teknologi og teamets størrelse og erfaring. Hver risiko er placeret i en kategori.

**Vurdering.** Hver risiko vurderes på to skalaer:

| Værdi | Sandsynlighed (S) | Konsekvens (K) for projektet |
|---|---|---|
| 1 | Meget usandsynlig | Ubetydelig |
| 2 | Usandsynlig | Marginal: lille forsinkelse, let at rette |
| 3 | Mulig | Kritisk: kræver ekstra arbejde, kan koste en story |
| 4 | Sandsynlig | Alvorlig: truer sprint goal eller MVP |
| 5 | Meget sandsynlig | Katastrofal: truer projektets succes |

**Eksponering.** Risikoeksponering (RE) = S × K. Den bruges til at sammenligne risici og afgøre, hvor vi lægger indsatsen. Vi har ingen omkostningstal, så konsekvensen er en skala fra 1 til 5 i stedet for en pris.

**Cutoff-linje.** Intet team har ressourcer til at håndtere alle risici lige aktivt. Derfor sorterer vi tabellen og trækker en cutoff-linje. En risiko er over linjen, hvis eksponeringen er **mindst 12**, eller hvis konsekvensen er **5**, uanset sandsynligheden. Det sidste krav sikrer, at usandsynlige, men katastrofale risici (fx at data bliver set af uvedkommende) ikke falder under linjen.

## 2. Risikotabel

Tabellen er sorteret efter eksponering (højest øverst). Ved lige eksponering kommer den risiko med højest konsekvens først.

| Prioritet | ID | Risiko | Kategori | S | K | RE | RMMM |
|---|---|---|---|---|---|---|---|
| 1 | R3 | Projektets omfang bliver for stort. | Produktstørrelse | 4 | 5 | 20 | Afsnit 3, R3 |
| 2 | R2 | Vi misforstår kundens behov. | Interessenter | 4 | 5 | 20 | Afsnit 3, R2 |
| 3 | R1 | Kunden har ikke tilstrækkelig tid til feedback. | Interessenter | 3 | 5 | 15 | Afsnit 3, R1 |
| 4 | R4 | Systemet bliver for kompliceret for klubbens medlemmer. | Interessenter | 3 | 4 | 12 | Afsnit 3, R4 |
| 5 | R6 | Dårlig kommunikation og fravær forsinker gruppens arbejde. | Team | 3 | 4 | 12 | Afsnit 3, R6 |
| 6 | R7 | Tekniske problemer med integration, Docker eller CI/CD forsinker udviklingen. | Udviklingsmiljø | 3 | 4 | 12 | Afsnit 3, R7 |
| 7 | R8 | Utilstrækkelig test medfører fejl i det færdige system. | Proces | 3 | 4 | 12 | Afsnit 3, R8 |
| 8 | R9 | Manglende erfaring med JavaScript i frontend og Spring Security forsinker udviklingen. | Team | 3 | 4 | 12 | Afsnit 3, R9 |
| 9 | R5 | Vi undervurderer opgaver og når ikke vores sprint goals. | Proces | 4 | 3 | 12 | Afsnit 3, R5 |
| 10 | R10 | Adgangsstyring fejler, så brugere kan se data, de ikke må (fx andre medlemmers data). | Teknologi | 2 | 5 | 10 | Afsnit 3, R10 |
| | | **Cutoff-linje** | | | | | |
| 11 | R11 | Krav ændrer sig midt i et sprint. | Interessenter | 3 | 3 | 9 | Afsnit 3, R11 |
| 12 | R12 | Konflikter i Git ødelægger `main`. | Udviklingsmiljø | 3 | 3 | 9 | Afsnit 3, R12 |

**Én kontaktperson.** Vores eneste kontakt til klubben er Flemming (se `interessenter-og-brugere.md`). Han repræsenterer instruktører, medlemmer, forældre og bestyrelse, og vi ser kun resultatet sammen med ham efter hvert sprint. Derfor er sandsynligheden for R2 (misforståelser) sat til 4, og konsekvensen for R1 (manglende tid til feedback) er sat til 5, fordi der ikke er nogen anden at spørge.

**Over linjen (R1-R10):** Risiciene følges aktivt. Monitoring i RMMM gennemgås ved hvert sprint, og forebyggelsen gennemføres som planlagt. R10 ligger over linjen på grund af konsekvens 5, selvom eksponeringen er 10.

**Under linjen (R11 og R12):** Risiciene overvåges, og forebyggelsen er allerede en del af vores faste arbejdsgang (frosne sprintkrav og ruleset på `main`). De får derfor ikke særskilt opfølgning i hvert sprint, men vurderes igen ved hver retrospective.

**Revurdering.** Risikovurderingen er en kontinuerlig proces. Ved hver retrospective vurderer vi sandsynlighed og konsekvens på ny, flytter risici op eller ned i forhold til linjen og tilføjer nye risici, hvis vi opdager dem. Stiger en risiko over linjen (fx hvis Git-konflikter begynder at ramme `main`), får den fuld opfølgning.

## 3. RMMM-plan

For hver risiko beskriver vi, hvordan vi **undgår** den (mitigation), hvilke tegn vi **følger** for at se, om den bliver mere eller mindre sandsynlig (monitoring), og hvad vi **gør**, hvis den rammer os (management).

### R1: Kunden har ikke tilstrækkelig tid til feedback (15)
- **Mitigation:** Flemming er vores eneste kontakt, så vi aftaler sprint review med ham på forhånd for hele projektperioden og sender konkrete spørgsmål, før vi mødes.
- **Monitoring:** Hold øje med aflyste møder, lange svartider og opgaver, der bliver blokeret af manglende feedback.
- **Management:** Fortsæt med de afklarede opgaver, og saml ubesvarede spørgsmål til næste kundemøde. Kan Flemming ikke nå et møde, beder vi om skriftlige svar på de vigtigste spørgsmål i stedet.

### R2: Vi misforstår kundens behov (20)
- **Mitigation:** Udarbejd tydelige user stories og acceptkriterier, og valider dem løbende med Flemming. Fordi alle behov går gennem ham, spørger vi ind til instruktørers, medlemmers og forældres behov og viser ham resultatet efter hvert sprint, så misforståelser opdages tidligt.
- **Monitoring:** Hold øje med uklare krav, ubesvarede spørgsmål og feedback, der ikke stemmer overens med vores forståelse, herunder feedback fra Flemming, der peger på behov, han ikke har nævnt før.
- **Management:** Afklar misforståelser med Flemming, bed ham om at afklare med de øvrige, hvis det er nødvendigt, og tilpas eller omprioritér product backlog.

### R3: Projektets omfang bliver for stort (20)
- **Mitigation:** Prioritér en MVP med de vigtigste funktioner, og estimér arbejdet realistisk.
- **Monitoring:** Sammenlign planlagte og færdiggjorte opgaver ved hvert sprint, og hold øje med gentagne forsinkelser.
- **Management:** Nedprioritér mindre vigtige funktioner i samarbejde med kunden, og fokusér på kernefunktionaliteten.

### R4: Systemet bliver for kompliceret for klubbens medlemmer (12)
- **Mitigation:** Udarbejd enkle brugergrænseflader, og lad Flemming afprøve dem ved hvert sprint review. Kan han få en instruktør eller et medlem til at prøve dem, beder vi om det.
- **Monitoring:** Indsaml Flemmings feedback og eventuel feedback fra instruktører og medlemmer via ham, og observér, om det er svært at forstå eller gennemføre centrale opgaver.
- **Management:** Forenkl problematiske arbejdsgange, og tilpas brugergrænsefladen efter feedback.

### R5: Vi undervurderer opgaver og når ikke vores sprint goals (12)
- **Mitigation:** Nedbryd user stories i mindre tasks, og tag højde for gruppens kapacitet ved sprint planning.
- **Monitoring:** Følg fremdriften gennem sprint backlog og daily scrum, og hold øje med opgaver, der tager længere tid end forventet.
- **Management:** Genplanlæg det resterende arbejde, hjælp hinanden med blokeringer, og justér sprint backlog med fokus på sprint goal.

### R6: Dårlig kommunikation og fravær forsinker gruppens arbejde (12)
- **Mitigation:** Hold daily scrum, dokumentér beslutninger, udarbejd en gruppekontrakt, og del viden om centrale dele af systemet.
- **Monitoring:** Hold øje med manglende deltagelse, uafsluttede opgaver, misforståelser og gentagne brud på gruppeaftaler.
- **Management:** Omfordel opgaver, og tilpas arbejdsplanen. Ved gentagne alvorlige samarbejdsproblemer tages en fælles drøftelse og eventuelt kontakt til vejlederen.

### R7: Tekniske problemer med integration, Docker eller CI/CD forsinker udviklingen (12)
- **Mitigation:** Opsæt og afprøv GitHub Actions, Docker og integrationer tidligt i projektet.
- **Monitoring:** Hold øje med fejlede builds, integrationsfejl og tekniske problemer, der blokerer udviklingen.
- **Management:** Fejlsøg i fællesskab, søg hjælp hos underviseren, og brug midlertidige løsninger, hvor det er muligt.

### R8: Utilstrækkelig test medfører fejl i det færdige system (12)
- **Mitigation:** Skriv unit tests og integrationstests løbende, og inkludér tests i Definition of Done.
- **Monitoring:** Kontrollér testresultater i GitHub Actions, og hold øje med funktioner, der mangler tests eller gentagne gange fejler.
- **Management:** Prioritér og ret kritiske fejl, og gennemfør regressionstest før levering.

### R9: Manglende erfaring med JavaScript og Spring Security forsinker udviklingen (12)
- **Mitigation:** Del viden om vanskelige teknologier i gruppen, og søg hjælp tidligt.
- **Monitoring:** Hold øje med opgaver, der tager længere tid end forventet, eller hvor gruppemedlemmer gentagne gange sidder fast.
- **Management:** Søg vejledning hos underviseren, samarbejd om de vanskelige opgaver, og forenkl løsningen, hvis det er nødvendigt.

### R10: Adgangsstyring fejler, så brugere kan se data, de ikke må (10)
- **Mitigation:** Test 401 (ikke logget ind) og 403 (forkert rolle) på alle endpoints med adgangsstyring, brug kun opdigtede testdata, og lad reviewer kontrollere roller og adgangsregler i hver PR, der tilføjer eller ændrer et endpoint.
- **Monitoring:** Kontrollér, at adgangstests er grønne i GitHub Actions, og hold øje med nye endpoints uden adgangstest og med rapporter om data, brugere kan se, men ikke bør kunne se.
- **Management:** Behandl fejlen som kritisk (se SQA-planen), ret den hurtigst muligt på en `fix/`-branch, og tilføj en test, der dækker den.

### R11: Krav ændrer sig midt i et sprint (9)
- **Mitigation:** Frys sprintets krav, og lad alle ændringer gå gennem Product Owner til næste sprint.
- **Monitoring:** Hold øje med ændringsønsker midt i sprintet og med ændringer i acceptkriterier på issues, der allerede er i sprint backlog.
- **Management:** Læg ændringer i product backlog, og prioritér dem ved næste sprint planning.

### R12: Konflikter i Git ødelægger `main` (9)
- **Mitigation:** Brug ruleset med pull request, review og grøn CI på `main`, hold PR'erne små, og merge `main` ind i branchen før PR (se SCM-planen).
- **Monitoring:** Hold øje med fejlede builds på `main`, hyppige merge-konflikter og PR'er, der ligger åbne i længere tid.
- **Management:** Gå tilbage til seneste baseline (`sprint-<n>`), og ret fejlen på en ny branch via issue og PR.
