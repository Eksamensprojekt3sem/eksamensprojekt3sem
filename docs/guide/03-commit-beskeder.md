# Gode commit-beskeder

**Alt i commits er på engelsk** – også hvis koden handler om danske ting. Kun tekst, som *brugeren ser i systemet*, er på dansk.

## Den ene regel

Subject-linjen skal kunne færdiggøre denne sætning:

> **If applied, this commit will …** *Add member login page*

Så skriver du i **bydeform (imperativ)** – som om du giver en ordre til koden.

| ✅ Godt | ❌ Dårligt | Hvorfor |
|---------|-----------|---------|
| `Add member registration form` | `Added member registration form` | Datid → bydeform |
| `Fix null pointer in booking service` | `fixed bug` | Hvilken bug? |
| `Remove unused imports` | `stuff` | Siger intet |
| `Rename Member to ClubMember` | `Changes` | Hvad ændrede sig? |
| `Validate email before saving` | `Validating email.` | Ingen punktum, bydeform |

## Gode vaner

- **Én logisk ændring pr. commit.** Ikke "fix login, add menu og rename ting".
- **Commit tit, men kun kode der virker** (det skal kunne bygge).
- **Aldrig** commit: hemmeligheder, adgangskoder, `.env`, `target/`, `.idea/`.
- Tjek før du committer: `git status` og `git diff --staged`.

## Hurtigt i terminalen

```bash
git commit -m "Add member registration form"
```

Ret den **sidste** commit-besked (før du har pushet):

```bash
git commit --amend
```

## Tjekliste før du committer

- [ ] Starter subject med et bydeform-verbum? (*Add, Fix, Remove, Rename, Update, Refactor*)
- [ ] Under 50 tegn, intet punktum?
- [ ] På engelsk?
