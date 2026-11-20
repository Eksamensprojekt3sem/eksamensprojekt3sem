# Installér Git og GitHub CLI (`gh`)

Du skal bruge to ting: **Git** (versionsstyring) og **`gh`** (GitHub fra terminalen). Tag kun dit eget styresystem.

Tjek først, om du allerede har dem:

```bash
git --version
gh --version
```

---

## macOS

Vi bruger **Homebrew** (pakkehåndtering).

**1. Installér Homebrew** (spring over, hvis `brew --version` virker):

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

Følg instruktionerne i terminalen til sidst (der står ofte 1–2 linjer, du skal kopiere for at få `brew` i din PATH).

**2. Installér Git og gh:**

```bash
brew install git gh
```

**3. Tjek:**

```bash
git --version
gh --version
```

> Vil du ikke bruge Homebrew? Download `gh` fra <https://cli.github.com> og Git fra <https://git-scm.com/downloads>.

---

## Windows

Vi bruger **winget** (følger med Windows 10/11).

**1. Åbn PowerShell** og installér:

```powershell
winget install --id Git.Git -e
winget install --id GitHub.cli -e
```

**2. Luk PowerShell og åbn et nyt vindue** (ellers kender terminalen ikke de nye programmer).

**3. Åbn nu "Git Bash"** (Start-menuen) – det er den terminal, vi bruger i guiderne – og tjek:

```bash
git --version
gh --version
```

> Virker `winget` ikke? Download installere her: Git → <https://git-scm.com/downloads/win>, GitHub CLI → <https://cli.github.com> (vælg `.msi`).
> Ved Git-installationen kan du blot klikke "Next" – standardvalgene er fine.

---

## Log ind på GitHub (alle platforme)

```bash
gh auth login
```

Svar sådan:

| Spørgsmål | Svar |
|-----------|------|
| Where do you use GitHub? | **GitHub.com** |
| Preferred protocol for Git operations? | **SSH** |
| Authenticate Git with your GitHub credentials? | **Yes** |
| How would you like to authenticate? | **Login with a web browser** |

Du får en engangskode. Tryk Enter, indtast koden i browseren og godkend.

Tjek at alt virker:

```bash
gh auth status
```

Du skal se `✓ Logged in to github.com`.

---

## Næste skridt

1. [Gør din GitHub-mail privat](05-privat-github-mail.md) – **før** din første commit!
2. Klon projektet:
   ```bash
   gh repo clone <organisation>/<repo>
   cd <repo>
   ```
3. Læs [Git & GitHub: terminal og browser](04-git-fra-terminalen.md).

## Fejlfinding

| Problem | Løsning |
|---------|---------|
| `gh: command not found` / `not recognized` | Luk og åbn terminalen igen. På Mac: tjek `brew` er i PATH. På Windows: brug Git Bash/nyt PowerShell-vindue. |
| `gh auth login` åbner ikke browser | Kopiér linket og koden manuelt ind i browseren. |
| `Permission denied` / `403` ved push | Kør `gh auth status`. Er du medlem af organisationen? Bed om invitation. |
| Spørger om brugernavn/adgangskode ved push | Kør `gh auth setup-git`. |
| Opdatér `gh` senere | Mac: `brew upgrade gh` · Windows: `winget upgrade --id GitHub.cli` |
