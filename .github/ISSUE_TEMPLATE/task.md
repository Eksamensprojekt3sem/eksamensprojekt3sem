---
name: Task
about: En konkret opgave under en user story
title: "US-<nr>-T<nr>: "
labels: task
---

Del af #<story-nummer>

**Estimat:** <1 / 2 / 4 / 8> timer
**Blocked by:** #<nr> / intet <!-- sæt det også under Relationships -->

### Hvad skal laves
<!-- Konkret: klasse, metode, endpoint eller side. -->

### Filer
-

### Trin
- [ ]

### Branch
```bash
git switch main && git pull
gh issue develop <dette-issue-nummer> --name feature/<dette-issue-nummer>-<kort-navn> --checkout
```

### Definition of Done
- [ ] Acceptkriterier opfyldt
- [ ] Koden følger coding standards
- [ ] Tests skrevet og grønne, også i CI (`./mvnw verify`)
- [ ] Reviewet af mindst én anden og merget til main, branchen er slettet
- [ ] Ingen hemmeligheder eller rigtige personoplysninger committet. Testdata kun i `InitData`
- [ ] Virker, når man starter projektet fra en ren main
- [ ] Dokumentation opdateret (hvis relevant)
