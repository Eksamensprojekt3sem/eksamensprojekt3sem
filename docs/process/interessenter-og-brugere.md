# Interessenter og brugere

Hvem har en interesse i systemet, og hvem bruger det? Oversigten er baseret på kundemødet 20/11/2026 (`referat-kundemoede-1.md`).

**Vores kontakt til klubben.** Flemming er vores eneste direkte kontakt. Han repræsenterer de øvrige interessenter, og deres behov kender vi gennem ham og kundemødet. Flemming ser, hvad vi har lavet, efter hvert sprint (sprint review). Det betyder, at strategien for de andre interessenter er indirekte: Vi spørger Flemming, og han videregiver.

## Interessenter

| Interessent | Hvad vil de have ud af det | Indflydelse | Interesse | Strategi | Hvordan |
|---|---|---|---|---|---|
| **Flemming (næstformand, kunde)** | Slippe for Holdsports uoverskuelighed og få træninger, gradueringer, fremmøde og nyheder samlet | Høj | Høj | Manage Closely | Sprint review efter hvert sprint, konkrete spørgsmål på forhånd |
| **Instruktører** | Se bæltegrader, gradueringshistorik og fremmøde hurtigt og slippe for manuel fremmøderegistrering og opdatering af bæltegrad | Høj | Høj | Manage Closely | Via Flemming: vi spørger, om en instruktør kan afprøve nye skærmbilleder |
| **Administrator** | Oprette hold, events og roller og have overblik over medlemmer og kontingent | Høj | Høj | Manage Closely | Via Flemming: roller og rettigheder aftales i acceptkriterierne |
| **Medlemmer** | Se kalender og events, tilmelde sig, tjekke ind og følge med i nyheder | Lav | Høj | Keep Informed | Via Flemming: vi holder løsningen enkel, og Flemming vurderer, om den er let at bruge |
| **Forældre** | Styre deres børns tilmeldinger og se deres fremmøde og bæltegrad | Lav | Høj | Keep Informed | Via Flemming: han giver besked om, hvad systemet kan, når det er klar |
| **Bestyrelse (øvrige)** | At klubben får et system, der virker, og som ikke koster tid eller for mange penge | Høj | Lav | Keep Satisfied | Via Flemming: han kan vise resultatet fra sprint review |
| **Udviklerne (os)** | Et afleveret projekt med kvalitet inden for tidsrammen | Høj | Høj | Manage Closely | Scrum, SCM-plan, SQA-plan og risikoanalyse |
| **Cay (eksaminator)** | At processen og dokumentationen følger kravene | Høj | Lav | Keep Satisfied | Dokumentationen følger kravene og er opdateret |

Strategien følger af indflydelse og interesse:

| | Lav interesse | Høj interesse |
|---|---|---|
| **Høj indflydelse** | Keep Satisfied | Manage Closely |
| **Lav indflydelse** | Monitor | Keep Informed |

Flemming sidder i bestyrelsen, men han er vores kunde og kontaktperson, så han er Manage Closely. Resten af bestyrelsen er Keep Satisfied.

Fordi Flemming er den eneste kontakt, er det en risiko, at vi misforstår behov, som han ikke har nævnt, eller at han ikke har tid (se R1 og R2 i `risikoanalyse.md`).

## Brugere og roller i systemet

| Rolle | Hvem | Hvad kan de |
|---|---|---|
| **Medlem** | Klubbens medlemmer | Se kalender, events og nyheder, tilmelde og afmelde sig, se eget fremmøde, rette egne oplysninger |
| **Forælder** | Forælder til medlemmer | Det samme som medlem, men for sine børn, og kan oprette og knytte børn til sin konto. Ser kun egne børns oplysninger |
| **Instruktør** | Trænere/instruktører | Oprette events, registrere gradueringer, rette fremmøde, se medlemmer, hold og historik |
| **Administrator** | Bestyrelse og admin | Alt instruktøren kan, plus oprette hold, give roller, melde medlemmer ud og se kontingent |
| **Tablet** | Fast konto i klubben | Kun indtjekning til træning |

Samme person kan have flere roller. Hver rolle får kun adgang til det, den må (se adgangstests i `sqa-plan.md`, R10).

Interessenter, der ikke bruger systemet: Cay og udviklerne.
