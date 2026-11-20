# Git & GitHub: terminal og browser

## Hvad gør vi hvor?

| I **terminalen** (`git` + `gh`) | På **github.com** (browser) |
|---------------------------------|-----------------------------|
| Branch (koblet til issue), skift, slet | Oprette og redigere **issues** |
| `add`, `commit`, `push`, `pull` | **GitHub Projects**-boardet |
| Opdatere branch med `main` | **Review** af Pull Requests |
| **Oprette** Pull Request (`gh pr create`) | **Merge** af Pull Requests |
| Tags | Milestones og labels |

**Værktøjer:** `git` (versionsstyring) + `gh` (GitHub fra terminalen). Installation: [06-installer-gh-cli.md](06-installer-gh-cli.md).

---

## Den daglige arbejdsgang (TL;DR)

```bash
# 1. Find dit issue på GitHub (Projects-boardet) – noter issue-nummeret, fx 12
git switch main && git pull                  # 2. Hent nyeste main
gh issue develop 12 --name feature/12-member-login --checkout   # 3. Branch knyttet til issue #12
# ... skriv kode ...
git status                                   # 4. Hvad har jeg ændret?
git commit                                   # 5. Commit ("If applied, this commit will ...")
git push -u origin feature/12-member-login   # 6. Push (første gang med -u)
gh pr create --base main                     # 7. Opret Pull Request
# 8. På GitHub: review → Approve → Squash and merge → Delete branch
git switch main && git pull                  # 9. Klar til næste opgave
```

---

## 1. Før du starter

```bash
git status                       # Hvor er jeg? Er der ændringer?
git branch                       # Lokale branches (* = den du står på)
```

## 2. Skift branch

```bash
git switch main                  # Skift til en eksisterende branch
git switch feature/12-member-login
git switch -                     # Skift tilbage til forrige branch
```

> Har du uncommittede ændringer, der er i vejen? Se **Hjælp** nedenfor (stash).

Skift til en branch, som en anden har lavet (fx for at teste den):

```bash
git fetch                        # Hent nyt fra GitHub
git switch feature/15-booking    # Git finder den automatisk på remote
```

## 3. Lav en branch, der er koblet til et issue 

Dette er **den eneste måde** vi laver branches på:

```bash
git switch main && git pull
gh issue develop 12 --name feature/12-member-login --checkout
```

Det gør tre ting: opretter branchen på GitHub, **kobler den til issue #12** (synlig under "Development" på issuet), og skifter dig over på den.

Navngivning: **`<type>/<issue-nr>-<kort-navn-med-bindestreger>`**

| Type | Brug til | Eksempel |
|------|----------|----------|
| `feature/` | Ny funktionalitet / task | `feature/12-member-login` |
| `fix/` | Fejlrettelse | `fix/20-null-in-booking` |
| `docs/` | Kun dokumentation | `docs/8-update-readme` |
| `chore/` | Opsætning, config, refaktor | `chore/3-add-ci` |

Se hvilke branches der hænger på et issue:

```bash
gh issue develop --list 12
```

## 4. Commit

```bash
git status                       # Se hvad der er ændret
git diff                         # Se selve ændringerne (ikke-staged)
git add src/main/java/.../Member.java    # Tilføj én fil
git diff --staged                # Tjek det, du er ved at committe
git commit -m "Add member login endpoint"
```

Regler for beskeder: [03-commit-beskeder.md](03-commit-beskeder.md).

Ret den sidste commit (kun hvis du **ikke** har pushet):

```bash
git commit --amend               # Ret besked
git add glemt-fil.java && git commit --amend --no-edit   # Tilføj glemt fil
```

## 5. Push

```bash
git push -u origin feature/12-member-login   # Første gang
git push                                     # Efterfølgende
```

## 6. Hold din branch opdateret med main

Gør det jævnligt (og altid **før** du opretter PR), så konflikter opdages tidligt:

```bash
git fetch origin
git merge origin/main
```

**Konflikt?** Git skriver, hvilke filer. Så:

1. Åbn filen – find `<<<<<<<`, `=======`, `>>>>>>>`.
2. Ret til den rigtige version, **fjern markørerne**.
3. ```bash
   git add <filen>
   git commit            # afslut merge
   ```
4. Kør tests, og `git push`.

Usikker? Afbryd med `git merge --abort`, og bed om hjælp.

## 7. Opret Pull Request

```bash
git push                                  # Sørg for alt er pushet
gh pr create --base main                  # Interaktivt – udfylder vores PR-skabelon
```

`--base main` fortæller eksplicit, at PR'en skal **ind i `main`**. (Uden flaget bruger `gh` repoets standard-branch, som som regel også er `main` – men vi skriver det altid, så der aldrig er tvivl, og så PR'en ikke ender i en forkert branch ved en fejl.)

- Skriv `Closes #12` i PR-beskrivelsen → issuet lukkes automatisk ved merge.
- Ikke færdig men vil have feedback? Tilføj `--draft`.
- Åbn PR'en i browseren: `gh pr view --web`.

## 8. Review og merge – på GitHub

Review og merge gør vi i **browseren**, ikke i terminalen.

**Review:**

1. Åbn PR'en på GitHub → fanen **Files changed**.
2. Læs koden. Klik på `+` ved en linje for at skrive en kommentar.
3. Tjek reviewer-listen i PR-skabelonen.
4. Klik **Review changes** → vælg **Approve**, **Request changes** eller **Comment** → **Submit review**.

**Reviewer-spørgsmål:** Virker det? Følger det standards? Er det læseligt? Er der tests? Kan jeg forklare, hvad koden gør?

Vil du **køre koden lokalt** inden du godkender:

```bash
gh pr checkout 7                 # Hent PR'ens branch
./mvnw verify                    # Kør tests
git switch main                  # Tilbage bagefter
```

**Merge (når PR er godkendt og grøn):**

1. Klik **Squash and merge** (samler branchens commits til én commit på `main` – ren historik).
2. Bekræft.
3. Klik **Delete branch**.

## 9. Ryd op lokalt efter merge

```bash
git switch main
git pull
git fetch --prune                          # Fjern referencer til slettede remote branches
git branch -d feature/12-member-login      # Slet din lokale kopi af branchen
```

## 10. Slet en branch

```bash
# Lokalt (sikker – nægter hvis ikke merget)
git branch -d feature/12-member-login

# Lokalt (tvunget – du taber ikke-merget arbejde!)
git branch -D feature/12-member-login

# På GitHub (hvis du ikke trykkede "Delete branch" ved merge)
git push origin --delete feature/12-member-login
```

> Man kan ikke slette den branch, man står på. Skift først til `main`.

## 11. Tags / baselines (ved sprint-slut)

```bash
git switch main && git pull
git tag -a sprint-1 -m "Sprint 1 baseline: member login and registration"
git push origin sprint-1
```

## 12. Hjælp – jeg har rodet i det

| Situation | Kommando |
|-----------|----------|
| Jeg har ændret en fil og vil fortryde (ikke committet) | `git restore <fil>` |
| Jeg har `git add`'et noget, som ikke skal med | `git restore --staged <fil>` |
| Jeg vil gemme ændringer midlertidigt og skifte branch | `git stash` … senere `git stash pop` |
| Jeg har lavet en commit (ikke pushet), som skal fortrydes – men beholde koden | `git reset --soft HEAD~1` |
| En commit er pushet/merget og skal fortrydes | `git revert <commit-hash>` (laver en ny "fortryd"-commit) |
| Jeg kom til at committe på `main` ved en fejl (ikke pushet) | **Spørg gruppen først** |
| Hvad har der egentlig sket? | `git log --oneline --graph --decorate -15` |
| Hvem ændrede denne linje? | `git blame <fil>` |

### Gør ALDRIG

- `git push --force` (især på `main`). Hvis du tror, du har brug for det: spørg gruppen.
- `git add .` uden først at have kigget på `git status` (så kommer `.env` og `target/` med). 
- Committe direkte på `main`.
- Arbejde på en branch i flere dage uden at merge `main` ind.
