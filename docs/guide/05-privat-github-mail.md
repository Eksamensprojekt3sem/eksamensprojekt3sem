# Skjul din rigtige mail på GitHub

**Problem:** Hver commit indeholder din mail. Repos er offentlige/delte, og din private mail ender i historikken for evigt.
**Løsning:** GitHub giver dig en **noreply-adresse**, som ser sådan ud:

```
123456789+dit-brugernavn@users.noreply.github.com
```

(Tallene er dit GitHub bruger-ID.) Commits med den adresse bliver stadig knyttet til din profil.

> Gør det **før din første commit**. Gamle commits kan ikke ændres uden at omskrive historikken – det skal vi ikke.

---

## Trin 1 – Slå det til på GitHub (browser, 1 min)

1. Gå til **github.com → Settings → Emails**.
2. Sæt **Keep my email addresses private**.
3. Sæt **Block command line pushes that expose my email**
   (så GitHub afviser dine pushes, hvis du ved en fejl bruger din rigtige mail).
4. Kopiér din noreply-adresse, der står øverst på siden (`...@users.noreply.github.com`).

## Trin 2 – Fortæl Git, at den skal bruge adressen (terminal)

Efter du har installeret `gh` og logget ind (`gh auth login`), kan du få din noreply-adresse udskrevet:

```bash
gh api user --jq '"\(.id)+\(.login)@users.noreply.github.com"'
```

Sæt navn og mail (**erstat med dine egne værdier**):

```bash
git config --global user.name "Dit Navn"
git config --global user.email "123456789+dit-brugernavn@users.noreply.github.com"
```

> **Windows PowerShell:** brug samme `git config`-kommandoer. `gh api`-linjen virker bedst i Git Bash.

## Trin 3 – Tjek at det virker

```bash
git config --global user.name
git config --global user.email
```

Skriv en test-commit og tjek forfatteren:

```bash
git log -1 --format="%an <%ae>"
```

Du skal se din noreply-adresse – **ikke** din rigtige mail.

## Fejlfinding

| Problem | Løsning |
|---------|---------|
| Push afvist med "private email address" | Du har brugt din rigtige mail. Kør Trin 2, og lav derefter `git commit --amend --reset-author --no-edit` på den seneste commit, og push igen. |
| Flere commits har den forkerte mail | Sig det til gruppen – vi finder en løsning sammen. Ret ikke historik på egen hånd. |
| Mail sat for hele computeren, men et andet repo skal bruge en anden | Kør `git config user.email "..."` (uden `--global`) inde i det repo. |
| Commits vises ikke på min GitHub-profil | Tjek, at ID-nummeret og brugernavnet i adressen er rigtigt. |

## Tjekliste

- [ ] "Keep my email addresses private" er slået til
- [ ] "Block command line pushes that expose my email" er slået til
- [ ] `git config --global user.email` viser `…@users.noreply.github.com`
- [ ] En test-commit viser noreply-adressen
