# Contributing

Everyone on the team follows these rules. They are short on purpose. Details are in [`docs/guide/`](docs/guide/00-start-her.md).

> **Language:** all code, comments and commits are in **English**. Issues and pull requests are written in **Danish** (the content of the templates is Danish). Text the end user sees in the UI is Danish.

## The rules

1. **No work without an issue.** No issue → no branch → no code.
2. **One branch per issue**, created from the issue:
   ```bash
   git switch main && git pull
   gh issue develop <issue-nr> --name feature/<issue-nr>-<short-name> --checkout
   ```
3. **Never commit directly to `main`.** Everything goes through a Pull Request.
4. **At least one other person approves** your PR. Never approve your own.
5. **Keep PRs small** (aim for under ~300 changed lines) and review within **24 hours**.
6. **Tests are part of the work.** A PR without tests for new logic will not be approved.
7. **Merge `main` into your branch** before opening the PR.

## Branch names

`<type>/<issue-nr>-<short-name>`

| Type | Use for |
|------|---------|
| `feature/` | New functionality / tasks |
| `fix/` | Bug fixes |
| `docs/` | Documentation only |
| `chore/` | Setup, config, refactoring |

Example: `feature/12-member-login`

## Commit messages

The subject must complete the sentence **"If applied, this commit will …"**

```
Add member registration form
```

- Imperative mood (*Add, Fix, Remove*), max 50 characters, no period
- English only
- One logical change per commit

## Pull requests

```bash
git push -u origin <branch>
gh pr create --base main      # the PR template is filled in automatically
```

Review and merge are done **on GitHub**: approve the PR, then **Squash and merge** and **Delete branch**.

## Issue types

| Template | Use for |
|----------|---------|
| **User story** | A requirement from the user's point of view (with acceptance criteria) |
| **Task** | A 1-8 hour piece of work belonging to a story (estimate is set on tasks) |

Create issues **on GitHub**: *Issues → New issue → choose a template*. Put `Closes #<nr>` in the PR description so the issue closes on merge.

## Definition of Done

- [ ] Follows the coding standards
- [ ] Tests written and passing (`./mvnw verify`)
- [ ] PR approved by at least one other person
- [ ] Merged to `main`, branch deleted, issue closed
- [ ] Documentation updated if relevant

## Code standards (summary)

- **Modular monolith, package by feature:** one folder per feature with entity, repository, service, controller and request/response records
- Modules use each other **only via the public `<Feature>Api` interface**; everything else is package-private
- **Thin controllers** returning `ResponseEntity`, logic in services, request/response records in and out (never entities)
- Entities are created and changed through `create(...)` / `update...(...)` and validate themselves
- Errors are thrown as `NotFoundException`, `ConflictException` or `InvalidInputException` and handled in one `ApiExceptionHandler`
- **Frontend:** a single-page app in `src/main/resources/static/` (one `index.html`, JS modules per feature). All API calls go through `<feature>-api.js`, and user data is set with `textContent`, never `innerHTML`
- No secrets in Git

Full version: [`docs/guide/02-coding-standards.md`](docs/guide/02-coding-standards.md)

## Sprint baselines

At the end of each sprint: `git tag -a sprint-<n> -m "Sprint <n> baseline"` and `git push origin sprint-<n>`.