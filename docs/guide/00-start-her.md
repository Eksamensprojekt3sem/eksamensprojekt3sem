# Start her

Denne guide er til hele gruppen. Den er lavet til at kunne læses hurtigt – de fleste sider tager under 5 minutter.

**Husk hvorfor vi gør det her:** Eksamen i systemudvikling handler om *processen*, ikke bare koden. Vi skal kunne vise, at vores udviklingsmodel og vores metoder **har styret arbejdet** – og forklare *hvorfor*. Alt i guiden er derfor lavet, så det automatisk efterlader beviser (issues, PR'er, commits, tags, retrospectives), som vi kan bruge til eksamen.

---

## Læs i denne rækkefølge

| # | Fil | Hvem | Tid |
|---|-----|------|-----|
| 1 | [Installér `gh` + Git](06-installer-gh-cli.md) | Alle, dag 1 | 10 min |
| 2 | [Privat GitHub-mail](05-privat-github-mail.md) | Alle, dag 1 | 5 min |
| 3 | [Sprint 0](01-sprint-0.md) | Alle | 15 min |
| 4 | [Git & GitHub: terminal og browser](04-git-fra-terminalen.md) | Alle – gem som snydeseddel | 15 min |
| 5 | [Commit-beskeder](03-commit-beskeder.md) | Alle | 5 min |
| 6 | [Coding standards](02-coding-standards.md) | Alle der skriver kode | 20 min |
| 7 | [Sådan lever vi op til pensum (SCM, SQA m.m.)](07-pensum-og-eksamen.md) | Alle, før eksamen | 15 min |

Skabeloner ligger i repoet:

- `README.md` og `CONTRIBUTING.md` (roden)
- `.github/ISSUE_TEMPLATE/` – user story, task, bug
- `.github/pull_request_template.md`

---

## De 7 regler (hvis du kun læser én ting)

1. **Intet arbejde uden et issue.** Ingen issue → ingen branch → ingen kode.
2. **Én branch pr. issue.** Navn: `feature/<issue-nr>-<kort-navn>` (fx `feature/12-member-login`).
3. **Aldrig direkte på `main`.** Alt går via Pull Request.
4. **Mindst 1 anden person godkender din PR** – du godkender aldrig din egen.
5. **Commit-besked = "If applied, this commit will …"** på engelsk, i bydeform (*Add*, *Fix*, *Remove*).
6. **Kode, kommentarer og commits er på engelsk.** Issues og PR'er skriver vi på dansk (indholdet i skabelonerne er på dansk), og det samme gælder tekst, brugeren ser i systemet.
7. **Små PR'er, ofte.** Helst under ~300 ændrede linjer.

---

## Terminal først

Branches, commits, push og oprettelse af PR'er gør vi fra terminalen (`git` + `gh`), ikke fra GUI'er. Det giver færre klik, færre fejl, og alle gør det på samme måde – det gør det også nemmere at hjælpe hinanden. IntelliJ må gerne bruges til at *skrive* kode.

**Issues, GitHub Projects, review og merge** gør vi på **github.com**.

> **Windows:** brug **Git Bash** (følger med Git) eller Windows Terminal. Kommandoerne i guiden er til bash. Mac: brug Terminal.
