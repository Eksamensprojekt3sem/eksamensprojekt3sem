# Udviklingsmodel

**Valg:** Scrum, som er en inkrementel model (Incremental). Vi leverer i afgrænsede increments (sprints), hvor hvert increment bygger videre på det forrige. De første increments fungerer samtidig som en slags prototype, der hjælper os med at afklare kravene til de næste.

## Hvorfor passer modellen til vores projekt?

- **Krav:** Vi kender kravene i store træk, men ikke i detaljer, og kunden kan først tage stilling til dem, når der er noget, der kører. Med Incremental får kunden værdi tidligt, og vi kan afdække de resterende krav undervejs.
- **Tid og ressourcer:** Slutdatoen (16. december) og teamet på fire personer er fastlagt. Vi vil ikke give køb på kvaliteten (Definition of Done), så det er omfanget, vi skruer på (Cays process-trekant: Time, Quality, Resources). Derfor prioriterer vi kravene med MoSCoW og bygger det vigtigste først.
- **Risiko:** Vi har et kørende system efter hvert sprint, så risikoen for, at hele projektet fejler, bliver mindre.

## Hvordan har vi tilpasset den?

- **Sprintlængde:** 1 uge. Sprint 0 (23.-24. november) bruger vi til at aftale arbejdsform, lave en plan og bygge et kørende skelet. Derefter kommer tre sprints: 25. november-1. december, 2.-8. december og 9.-15. december. 16. december er buffer til aflevering.
- **Events og roller:** Sprint planning, daglig stand-up, sprint review og retrospective. Marcus er Product Owner og kontaktperson til kunden, Adam er Scrum Master, og alle fire udvikler.
- **Framework activities:** Communication (kontakt med kunden), planning (planlægning), modeling (analyse og design), construction (kodning og test) og deployment (aflevering og drift). Vi gennemgår dem alle i hvert sprint, bare i lille format. Task sets tilpasser vi til projektet (adaptability).
- **Umbrella activities:** Risk management (risikoanalyse, som vi gennemgår ved hver retrospective), software quality assurance (tests og Definition of Done), technical reviews (code review på alle PR'er), software configuration management (branches, PR'er og tags pr. sprint) og measurement (metrikker som burn down chart og estimeret tid mod brugt tid).
- **XP-praksisser:** Vi låner tre praksisser fra Extreme Programming (XP) og bruger dem sammen med Scrum. Vi bruger continuous integration, så ændringer bliver integreret og testet løbende i stedet for samlet til sidst. Vi har fælles coding standards, så koden ser ens ud, uanset hvem der har skrevet den. Og vi følger collective code ownership: Hele teamet ejer koden, og alle må rette i alle dele af systemet, hvilket code review på PR'erne understøtter.
- **Redskaber:** User stories med MoSCoW, tasks estimeret i timer (planning poker) og et GitHub Project som sprintboard.

## Hvad fravælger vi, og hvorfor?

- **Waterfall:** Den kræver, at kravene er velkendte og stabile, og kunden kan ikke bruge noget, før det hele er færdigt. Overraskelser sent i forløbet bliver dyre.
- **V-model:** Vi har hverken ekstreme kvalitetskrav eller et eksisterende system, vi skal integrere med. Derfor tester vi løbende i hvert sprint i stedet.
- **Spiral:** Den er drevet af risikoen for, om noget overhovedet kan lade sig bygge teknisk. Vores største risici er tid og krav, og vi bruger teknologi, vi kender.
- **Prototyping alene:** Kunden har mere konkrete ønsker end blot overordnede mål, og vi har brug for faste leverancer. Derfor bygger vi hvert increment videre i stedet for at smide det væk.
- **XP (Extreme Programming) som samlet metode:** XP består af mange praksisser, der hænger tæt sammen, og den forudsætter en kunde, der er med i teamet hele tiden. Det har vi ikke, da vi kun har kontakt med kunden via Product Owner og ved sprint review. XP giver heller ikke de faste rammer om roller og events, som vi har brug for i et nyt team. Vi vælger derfor Scrum som ramme og tager kun de XP-praksisser med, der passer til os: continuous integration, coding standards og collective code ownership.
- **Unified Process:** For meget proces (faser og workflows) i forhold til et så kort projekt.

## Hvilke styrker og svagheder forventer vi?

- **Styrker:** Kunden får værdi tidligt, den samlede risiko er lavere, og de vigtigste funktioner bliver testet mest. Kravene til et sprint er frosset, så vi kan arbejde i ro.
- **Svagheder:** Ændringer midt i et sprint må vente til det næste. Vi har kun tre sprints at lære af i retrospectives. Ritualerne fylder forholdsvis meget i et lille team. Estimering er svært, når man er nybegynder. Product Owner og Scrum Master har desuden mindre tid til at kode.
