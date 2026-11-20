# Coding standards

Mål: at koden ser ud, som om **én person** har skrevet den. Det gør review hurtigere, fejl nemmere at finde og eksamen lettere ("hvorfor ser jeres arkitektur sådan ud?").

> **Fokus:** Dette er et systemudviklingsprojekt. Vi holder arkitekturen enkel og konsekvent – hellere få regler, der følges, end mange, der ignoreres.

**Sprog:** Al kode (klasser, metoder, variabler, kommentarer) og alle commits er på **engelsk**. Tekst, brugeren ser (labels, fejlbeskeder i UI, e-mails), er på **dansk**. Issues og PR'er skriver vi på dansk, fordi indholdet i skabelonerne er på dansk.

---

## 1. Arkitektur: modulær monolit

Én applikation (én `.jar`, én database), men opdelt i **tydeligt adskilte moduler** (features). Hvert modul ejer sit eget område og har **ét offentligt "kontrakt"-interface**, som de andre moduler bruger.

```
Ét deploybart system
┌────────────────────────────────────────────────────┐
│  member      │  training     │  grading   │  ...   │   ← moduler (features)
│  MemberApi   │  TrainingApi  │  GradingApi│        │   ← kontrakten udadtil
└────────────────────────────────────────────────────┘
      Moduler snakker KUN sammen via hinandens <Feature>Api
```

**Hvorfor?** Enkelt at udvikle og køre (ét projekt), men vi undgår "spaghetti": ændringer i ét modul smitter ikke tilfældigt ind i andre. Hvis systemet engang skulle splittes op, er grænserne allerede tegnet.

**Reglerne:**

1. **Hvert modul har et offentligt `<Feature>Api`-interface** (fx `MemberApi`) og de få `public` records, det returnerer (fx `MemberInfo`). Det er **det eneste**, andre moduler må bruge. `MemberService` implementerer interfacet.
2. **Alt andet i modulet er package-private** (ingen `public`): entity, repository, service, controller, request/response-records. Så **stopper compileren** dig, hvis du forsøger at bruge dem fra et andet modul.
3. **Ingen cirkulære afhængigheder** (A kalder B, B kalder A). Opstår det, mangler vi et tredje modul – eller skal en af dem ikke kende den anden.
4. **Hvert modul ejer sine egne tabeller.** Andre moduler gemmer kun et **ID** (`Long memberId`) og slår oplysninger op via `MemberApi` – de peger aldrig med en `@ManyToOne` ind i et andet moduls entity.
5. **`common/` er lille** og indeholder kun det, alle bruger (exceptions, `ApiExceptionHandler`, evt. konfiguration). Forretningslogik hører aldrig hjemme i `common/`.

### Eksempel: modulets kontrakt

```java
package dk.ek.eksamensprojekt.member;

public interface MemberApi {

    MemberInfo getMember(Long memberId);

    Optional<MemberInfo> findMemberByEmail(String email);
}
```

```java
package dk.ek.eksamensprojekt.member;

public record MemberInfo(Long id, String name, String email) {}
```

Et andet modul (fx `training`) bruger det sådan:

```java
@Service
class AttendanceService {

    private final MemberApi memberApi;   // ← interfacet, ikke MemberService/MemberRepository

    AttendanceService(MemberApi memberApi) {
        this.memberApi = memberApi;
    }

    void registerAttendance(Long trainingId, Long memberId) {
        MemberInfo member = memberApi.getMember(memberId);   // kaster NotFoundException hvis den ikke findes
        // ...
    }
}
```

> **Tommelfingerregel:** Tilføj kun metoder til `<Feature>Api`, når et *andet modul* faktisk har brug for dem. Controlleren i samme modul bruger bare servicen direkte.

---

## 2. Package by feature

Vi organiserer efter **feature** (hvad systemet *gør*), ikke efter teknisk lag.

```
src/main/java/dk/ek/eksamensprojekt/
├── EksamensprojektApplication.java
├── common/
│   ├── ApiExceptionHandler.java
│   ├── NotFoundException.java
│   ├── ConflictException.java
│   └── InvalidInputException.java
├── member/
│   ├── MemberApi.java               (public interface)
│   ├── MemberInfo.java              (public record)
│   ├── Member.java                  (entity)
│   ├── MemberRepository.java
│   ├── MemberService.java           (implements MemberApi)
│   ├── MemberController.java
│   ├── CreateMemberRequest.java
│   ├── UpdateMemberRequest.java
│   └── MemberResponse.java
├── training/
│   └── ...
└── grading/
    └── ...

src/main/resources/static/    (frontend: én HTML-side + JS og CSS)
├── index.html                       (den eneste HTML-side, indeholder <main id="app">)
├── main.js                          (starter appen og routeren)
├── common/                          (delt: api.js, router.js, base.css med .btn, .form-field, .alert)
└── member/
    ├── member-api.js                (alle fetch-kald til /api/members)
    ├── member-list.js               (view: tegner listen i #app)
    ├── member-detail.js
    ├── member-create.js
    ├── member-update.js
    └── member.css
```

**Ikke sådan** (package by layer): `controllers/`, `services/`, `repositories/`, `entities/` for hele systemet.

**Regler:**
- Ét modul = én mappe med **alt** der hører til featuren – også frontend-filerne (JS og CSS) har en `member`-mappe under `static/`.
- Ingen undermapper som `dto/` i modulet. Request-/response-records ligger sammen med resten, så de kan være package-private.
- Tests spejler strukturen: `src/test/java/.../member/MemberServiceTest.java`.

---

## 3. Navngivning

### Ét fælles verbum for hver handling

Vi bruger **de samme ord** hele vejen igennem – i URL, controller, service, request-klasse, HTML-side og CSS. Så kan man gætte sig til navnet.

| Handling | HTTP | URL | Controller/service-metode | Request / response | View (JS) og route | HTML id / CSS |
|----------|------|-----|---------------------------|--------------------|----------|---------------|
| Se alle | `GET` | `/api/members` | `getAll()` | `List<MemberResponse>` | `member-list.js` · `#/members` | `member-list` |
| Se én | `GET` | `/api/members/{id}` | `getById(id)` | `MemberResponse` | `member-detail.js` · `#/members/{id}` | `member-detail` |
| Opret | `POST` | `/api/members` | `create(request)` | `CreateMemberRequest` | `member-create.js` · `#/members/new` | `member-create-form` |
| Opdatér | `PUT` | `/api/members/{id}` | `update(id, request)` | `UpdateMemberRequest` | `member-update.js` · `#/members/{id}/edit` | `member-update-form` |
| Slet | `DELETE` | `/api/members/{id}` | `delete(id)` | – | (knap) | `member-delete-button` |

Andre handlinger får et **tydeligt verbum** på samme måde: `ActivateMemberRequest`, `member-activate-button`, `POST /api/members/{id}/activation`.

### Java

| Hvad | Konvention | Eksempel |
|------|------------|----------|
| Klasse | `PascalCase`, navneord | `MemberService` |
| Metode / variabel | `camelCase`, verbum | `findMemberByEmail()` |
| Konstant | `UPPER_SNAKE_CASE` | `NAME_MAX_LENGTH` |
| Package | `lowercase`, ental | `member`, `grading` |
| Kontrakt | `<Feature>Api` + `<Feature>Info` | `MemberApi`, `MemberInfo` |
| Request | `<Handling><Entity>Request` | `CreateMemberRequest`, `UpdateMemberRequest` |
| Response | `<Entity>Response` | `MemberResponse` |
| DB-tabel | `snake_case`, flertal | `club_members` |
| Test | `<Klasse>Test` | `MemberServiceTest` |
| Testmetode | `metode_betingelse_resultat` | `create_duplicateEmail_throwsConflict` |

### HTML, CSS og JavaScript

| Hvad | Konvention | Eksempel |
|------|------------|----------|
| Filnavne (html, css, js) | `kebab-case`, små bogstaver | `member-list.js`, `member.css` |
| Views (JS-moduler) | `static/<feature>/<feature>-<handling>.js` | `member/member-create.js` |
| API-kald | `static/<feature>/<feature>-api.js`, én funktion pr. endpoint | `member/member-api.js` → `getMembers()` |
| CSS-klasser | `kebab-case`, **feature først**, derefter hvad det er | `.member-card`, `.member-card__title` |
| CSS-modifier (tilstand/variant) | `--` efter navnet | `.member-card--inactive`, `.btn--primary` |
| Delte klasser | uden feature-præfiks | `.btn`, `.form-field`, `.alert` |
| HTML `id` | `<feature>-<handling>-<element>` | `member-create-form`, `member-email-input` |
| `data-`-attributter | `data-<feature>-<navn>` | `data-member-id="12"` |
| JS-variabler/funktioner | `camelCase`, verbum + feature | `loadMembers()`, `createMember()`, `renderMemberList()` |
| JS-konstanter | `UPPER_SNAKE_CASE` | `API_BASE_URL` |

**Regler:**
- **Én HTML-side:** `static/index.html` med `<main id="app">`. Hver view er et JS-modul, der tegner ind i `#app`. Vi routes med hash (`#/members/12`), så serveren ikke skal videresende URL'er.
- **Al kommunikation med serveren går via `fetch` i `<feature>-api.js`** (med delt hjælp i `common/api.js`), JSON ind og ud. Views kalder aldrig `fetch` direkte.
- **Fejl fra API'et** (`ProblemDetail`: `detail` og `errors` pr. felt, se afsnit 8) vises som dansk tekst ved det rigtige felt.
- **Brugerdata sættes med `textContent`, aldrig med `innerHTML`** (ellers risikerer vi XSS).
- Scripts indlæses som ES-moduler (`<script type="module">`). Ingen frameworks og intet byggetrin.
- **Ingen styling med `id`** – brug klasser. `id` bruges til label/for, JS og anker-links.
- **Ingen inline styles** (`style="..."`) og ingen inline `onclick`. CSS i `.css`, JS i `.js`.
- Hver knap/form har et **navn, der siger hvad den gør**: `member-create-form`, ikke `form1` eller `myForm`.
- Tekst brugeren ser er på dansk; **klasse-, id- og filnavne er på engelsk**.

---

## 4. Entity (JPA)

Entity = en række i databasen. Entityen **beskytter sine egne regler** (invarianter): man kan ikke lave eller ændre en `Member` til en ugyldig tilstand.

```java
@Entity
@Table(name = "club_members")
class Member {

    static final int NAME_MAX_LENGTH = 100;
    static final int EMAIL_MAX_LENGTH = 254;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = NAME_MAX_LENGTH)
    private String name;

    // Unique: one member per email address
    @Column(nullable = false, unique = true, length = EMAIL_MAX_LENGTH)
    private String email;

    @Column(nullable = false)
    private LocalDate joinedOn;

    protected Member() {
        // Required by JPA
    }

    private Member(String name, String email, LocalDate joinedOn) {
        updateDetails(name, email);
        this.joinedOn = joinedOn;
    }

    static Member create(String name, String email, LocalDate joinedOn) {
        return new Member(name, email, joinedOn);
    }

    void updateDetails(String name, String email) {
        String validName = required(name, "name");
        String validEmail = normalizeEmail(required(email, "email"));

        this.name = validName;
        this.email = validEmail;
    }

    // "  Anna@Example.COM " and "anna@example.com" are the same address
    static String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidInputException("A member must have a " + field);
        }
        return value.trim();
    }

    Long getId() { return id; }
    String getName() { return name; }
    String getEmail() { return email; }
    LocalDate getJoinedOn() { return joinedOn; }
}
```

### Hvorfor `create()` og `updateDetails()` i stedet for en `public` konstruktør og `set...()`?

1. **Reglerne ligger ét sted.** Alle der opretter eller ændrer en `Member`, går igennem samme validering. Med en åben konstruktør og setters kan man (ved en fejl) glemme at validere.
2. **Navnet siger, hvad der sker.** `Member.create(...)` og `updateDetails(...)` er tydeligere end `new Member(...)` og fem `set`-kald.
3. **Alt-eller-intet ved ændring.** Vi validerer *alle* værdier, før vi ændrer et eneste felt – så ender en entity aldrig halvt opdateret.
4. **Privat konstruktør** tvinger alle til at bruge `create()`. Den `protected` tomme konstruktør er kun til JPA.
5. **Package-private** (`class Member`, ikke `public`): kun modulet kan bruge entityen – se regel 2 i afsnit 1.

> **To lag af validering – begge er gode:** `@Valid` på request-records giver pæne fejlbeskeder pr. felt til brugeren (se afsnit 7). Entityens `required(...)` er **sidste sikkerhedsnet**, så ugyldige data aldrig kan nå databasen – uanset hvor kaldet kommer fra.

**Øvrige regler:**
- Felter altid `private`. Kun getters, der faktisk bruges. **Ingen setters** – brug metoder med mening (`updateDetails`, `activate`).
- Relationer: `@ManyToOne(fetch = FetchType.LAZY)` **inden for samme modul**. Undgå `@ManyToMany` – lav en rigtig entity (fx `Enrollment`).
- **Returnér aldrig entities fra controllers** – brug response-records.

---

## 5. Repository

Kun databaseadgang. **Ingen forretningslogik.**

```java
interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
```

**Regler:**
- Interface, der udvider `JpaRepository<Entity, IdType>`. Package-private.
- Brug **derived queries** (`findByEmail`) først. Skriv `@Query` kun, når det er nødvendigt.
- Returtyper: `Optional<T>` for én, `List<T>` for flere – aldrig `null`.

---

## 6. Service

Her bor **forretningslogikken** og transaktionerne. Servicen implementerer modulets `Api`.

```java
@Service
@Transactional(readOnly = true)
class MemberService implements MemberApi {

    private final MemberRepository memberRepository;

    MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // ---- Used by our own controller ----

    public List<MemberResponse> getAll() {
        return memberRepository.findAll().stream().map(this::toResponse).toList();
    }

    public MemberResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public MemberResponse create(CreateMemberRequest request) {
        String email = Member.normalizeEmail(request.email());
        if (memberRepository.existsByEmail(email)) {
            throw new ConflictException("Email is already registered: " + email);
        }

        Member member = Member.create(request.name(), request.email(), LocalDate.now());
        return toResponse(memberRepository.save(member));
    }

    @Transactional
    public MemberResponse update(Long id, UpdateMemberRequest request) {
        Member member = findOrThrow(id);
        member.updateDetails(request.name(), request.email());
        return toResponse(member);   // JPA saves the change when the transaction commits
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.delete(findOrThrow(id));
    }

    // ---- The public contract (MemberApi), used by other features ----

    @Override
    public MemberInfo getMember(Long memberId) {
        Member member = findOrThrow(memberId);
        return new MemberInfo(member.getId(), member.getName(), member.getEmail());
    }

    @Override
    public Optional<MemberInfo> findMemberByEmail(String email) {
        return memberRepository.findByEmail(Member.normalizeEmail(email))
                .map(member -> new MemberInfo(member.getId(), member.getName(), member.getEmail()));
    }

    // ---- Helpers ----

    private Member findOrThrow(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found with id: " + id));
    }

    private MemberResponse toResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }
}
```

**Regler:**
- **Constructor injection** (ikke `@Autowired` på felter).
- `@Transactional(readOnly = true)` på klassen, `@Transactional` på metoder der ændrer data.
- Kast **egne exceptions** (`NotFoundException`, `ConflictException`, `InvalidInputException`) – aldrig `RuntimeException("fejl")`.
- Servicen tager og returnerer **request/response-records** (eller `*Info` til andre moduler) – aldrig entities.
- Bliver servicen meget stor (>300 linjer), så del den op i flere klasser *i samme modul*.

---

## 7. Request, response og controller

### Request- og response-records

Én request pr. handling – **de er sjældent ens**. Ved opret kræves alt, ved opdatér er det ofte færre felter.

```java
record CreateMemberRequest(
        @NotBlank @Size(max = Member.NAME_MAX_LENGTH) String name,
        @NotBlank @Email @Size(max = Member.EMAIL_MAX_LENGTH) String email
) {}

record UpdateMemberRequest(
        @NotBlank @Size(max = Member.NAME_MAX_LENGTH) String name,
        @NotBlank @Email @Size(max = Member.EMAIL_MAX_LENGTH) String email
) {}

record MemberResponse(Long id, String name, String email) {}
```

Hvorfor to næsten ens records? Fordi de **ændrer sig i hver sin retning**: senere får opret måske et felt for startdato, mens opdatér ikke må ændre den.

### Controller

Tynd. Controlleren **oversætter HTTP ↔ service** – intet andet. Vi bruger `ResponseEntity`, så statuskode (og evt. headers) står tydeligt i koden.

```java
@RestController
@RequestMapping("/api/members")
class MemberController {

    private final MemberService memberService;

    MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    ResponseEntity<List<MemberResponse>> getAll() {
        return ResponseEntity.ok(memberService.getAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<MemberResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getById(id));
    }

    @PostMapping
    ResponseEntity<MemberResponse> create(@Valid @RequestBody CreateMemberRequest request) {
        MemberResponse created = memberService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);   // 201
    }

    @PutMapping("/{id}")
    ResponseEntity<MemberResponse> update(@PathVariable Long id,
                                          @Valid @RequestBody UpdateMemberRequest request) {
        return ResponseEntity.ok(memberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();               // 204
    }
}
```

**Regler:**
- **Ingen forretningslogik og ingen repository-kald** i controlleren.
- Brug `@Valid` på request body. Valideringsregler ligger på request-recorden.
- **Ingen `@ResponseStatus`** – returnér `ResponseEntity`.
- **Ingen `try/catch`** i controlleren – det klarer `ApiExceptionHandler` (afsnit 8).

### REST-URL'er

- **Flertal, små bogstaver, navneord:** `/api/members`, `/api/trainings`.
- **Ingen verber i URL'en** – verbet er HTTP-metoden. `/api/createMember` `POST /api/members`
- Underressourcer: `/api/trainings/{id}/attendances`
- Brug `kebab-case` ved flere ord: `/api/grading-events`.

### Hvilken HTTP-metode?

| Metode | Bruges til | Har body? |
|--------|-----------|-----------|
| `GET` | Hente data (ændrer ingenting) | Nej |
| `POST` | Oprette noget nyt (eller udføre en handling) | Ja |
| `PUT` | **Erstatte/opdatere** en eksisterende ressource | Ja |
| `DELETE` | Slette | Nej |

### Hvilken statuskode, og hvornår?

| Kode | Navn | Bruges når … | Kommer fra |
|------|------|--------------|------------|
| `200` | OK | `GET` lykkedes, eller `PUT` lykkedes og vi returnerer den opdaterede ressource | `ResponseEntity.ok(...)` |
| `201` | Created | `POST` oprettede noget nyt | `ResponseEntity.status(HttpStatus.CREATED).body(...)` |
| `204` | No Content | Det lykkedes, og der er **ingen body** (typisk `DELETE`) | `ResponseEntity.noContent().build()` |
| `400` | Bad Request | Klienten sendte noget **ugyldigt**: manglende felt, forkert format, ugyldig JSON, forkert type i URL | `@Valid` fejler, `InvalidInputException` |
| `401` | Unauthorized | Man er **ikke logget ind** | Spring Security |
| `403` | Forbidden | Man er logget ind, men **må ikke** (forkert rolle) | Spring Security |
| `404` | Not Found | Ressourcen med det id **findes ikke** | `NotFoundException` |
| `409` | Conflict | Kaldet er gyldigt, men **konflikter med tilstanden**: email findes allerede, forretningsregel brudt, to har ændret samme række | `ConflictException` |
| `500` | Internal Server Error | **Vores bug.** Vi kaster aldrig 500 med vilje | Uventede exceptions |

**Hurtig huskeregel:**
- *Er selve forespørgslen forkert?* → `400`
- *Findes det, jeg beder om, ikke?* → `404`
- *Er forespørgslen fin, men det "støder ind i" noget, der allerede er?* → `409`

---

## 8. Exception handling

**Ét sted** til at oversætte exceptions til HTTP-svar: `ApiExceptionHandler` i `common/`. Features kaster bare (underklasser af) tre exceptions – så skal et nyt modul **ikke** have sin egen handler.

```java
package dk.ek.eksamensprojekt.common;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}

public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) { super(message); }
}
```

Vil vi have en mere specifik exception, arver vi fra en af dem: `class MemberNotFoundException extends NotFoundException`.

```java
package dk.ek.eksamensprojekt.common;
@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ProblemDetail notFound(NotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    ProblemDetail conflict(ConflictException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    ProblemDetail invalidInput(InvalidInputException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // @Valid failed, e.g. a blank name. Each field's problem goes in "errors",
    // so a form can show the message next to the right field.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail invalidRequest(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Invalid request: check the highlighted fields");
        problem.setProperty("errors", errors);
        return problem;
    }

    // The body is not valid JSON, or contains an unknown value
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ProblemDetail unreadableBody(HttpMessageNotReadableException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Malformed request body");
    }

    // A path variable or query parameter has the wrong type, e.g. /api/members/abc
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ProblemDetail typeMismatch(MethodArgumentTypeMismatchException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Invalid value for '" + ex.getName() + "'");
    }
}
```

> Bruger vi `@Version` (optimistisk låsning) på en entity, tilføjer vi også en handler for `OptimisticLockingFailureException` → `409`. Det gør vi først, når vi har brug for det.

**Regler:**
- Services og entities **kaster** exceptions. Controllere **fanger ikke**. `ApiExceptionHandler` **oversætter**.
- Ingen `catch (Exception e) {}` der sluger fejl. Aldrig tomme catch-blokke.
- Send **aldrig** stacktraces eller SQL-fejl til klienten.
- Fejlteksten i koden er på engelsk. Hvis brugeren skal se den, oversætter UI-laget den til dansk.

| Exception | Statuskode |
|-----------|-----------|
| `NotFoundException` | `404` |
| `ConflictException` | `409` |
| `InvalidInputException` | `400` |
| `@Valid` fejler | `400` (med `errors` pr. felt) |
| Alt andet (bug) | `500` |

---

## 9. Tests

Vi vil gerne skrive tests, men det er nyt for os. Derfor: **start småt, og følg en opskrift.** Alt vi skal bruge følger med `spring-boot-starter-test` (JUnit 5, Mockito, AssertJ, MockMvc).

### Hvad skal vi teste først? (i prioriteret rækkefølge)

1. **Forretningsregler** – dem, der står i acceptkriterierne ("email må ikke findes i forvejen").
2. **Fejlsituationer** – ugyldigt input, findes ikke, konflikt.
3. **Den lykkelige vej** (det normale tilfælde).

Tommelfingerregel: **mindst én test pr. acceptkriterie** i en user story. Det giver os også sporbarhed fra krav til test (SCM/SQA!).

### Fire slags tests

| Slags | Tester | Hurtig? | Bruger | Eksempel i |
|-------|--------|---------|--------|------------|
| **Entity-test** | Regler i entityen | ⚡ meget | Kun JUnit | `MemberTest` |
| **Service-test** | Forretningslogik | ⚡ meget | JUnit + Mockito | `MemberServiceTest` |
| **Repository-test** | Vores queries | lidt langsom | `@DataJpaTest` + H2 | `MemberRepositoryTest` |
| **Controller-test** | HTTP, statuskoder, validering | lidt langsom | `@WebMvcTest` + MockMvc | `MemberControllerTest` |

**Start med entity- og service-tests** – de er hurtige og dækker det meste af forretningsreglerne.

### Opskriften: Arrange – Act – Assert

Hver test har tre dele:

```java
@Test
void create_blankName_throwsInvalidInput() {
    // Arrange: sæt det op, du skal bruge
    String blankName = " ";

    // Act + Assert: kald koden og tjek resultatet
    assertThatThrownBy(() -> Member.create(blankName, "anna@example.com", LocalDate.now()))
            .isInstanceOf(InvalidInputException.class);
}
```

Navn: `metode_betingelse_forventetResultat`. Én test tester **én ting**.

### 1. Entity-test (ren Java – ingen Spring)

```java
class MemberTest {

    @Test
    void create_validValues_trimsAndNormalizesEmail() {
        Member member = Member.create("  Anna ", "  Anna@Example.COM ", LocalDate.of(2026, 1, 5));

        assertThat(member.getName()).isEqualTo("Anna");
        assertThat(member.getEmail()).isEqualTo("anna@example.com");
    }

    @Test
    void updateDetails_blankEmail_leavesMemberUnchanged() {
        Member member = Member.create("Anna", "anna@example.com", LocalDate.now());

        assertThatThrownBy(() -> member.updateDetails("New Name", " "))
                .isInstanceOf(InvalidInputException.class);

        assertThat(member.getName()).isEqualTo("Anna");   // ikke halvt opdateret
    }
}
```

### 2. Service-test (Mockito spiller databasen)

`@Mock` laver en "falsk" repository, så vi ikke behøver en rigtig database.

```java
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    void create_duplicateEmail_throwsConflict() {
        when(memberRepository.existsByEmail("anna@example.com")).thenReturn(true);
        CreateMemberRequest request = new CreateMemberRequest("Anna", "anna@example.com");

        assertThatThrownBy(() -> memberService.create(request))
                .isInstanceOf(ConflictException.class);

        verify(memberRepository, never()).save(any());   // vi gemte ikke noget
    }

    @Test
    void getById_unknownId_throwsNotFound() {
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.getById(99L))
                .isInstanceOf(NotFoundException.class);
    }
}
```

### 3. Repository-test

Tester, at vores queries virker mod en rigtig (in-memory) database. Kræver `com.h2database:h2` med scope `test` i `pom.xml`.

```java
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void existsByEmail_savedMember_returnsTrue() {
        memberRepository.save(Member.create("Anna", "anna@example.com", LocalDate.now()));

        assertThat(memberRepository.existsByEmail("anna@example.com")).isTrue();
    }
}
```

### 4. Controller-test

Tester HTTP-laget uden at starte hele appen. Servicen er en mock.

```java
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean   // (i Spring Boot før 3.4: @MockBean)
    MemberService memberService;

    @Test
    void create_blankName_returns400() throws Exception {
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "", "email": "anna@example.com"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_unknownId_returns404() throws Exception {
        when(memberService.getById(99L)).thenThrow(new NotFoundException("Member not found"));

        mockMvc.perform(get("/api/members/99"))
                .andExpect(status().isNotFound());
    }
}
```

### Kør testene

```bash
./mvnw test        # kør alle tests
./mvnw verify      # build + tests (det vi kræver før en PR)
```

I IntelliJ: højreklik på `src/test` → *Run Tests*.

**Regler:**
- Tests ligger i **samme package** som koden (så de kan se package-private klasser).
- Et nyt stykke forretningslogik uden test **godkendes ikke** i review.
- Tests må ikke afhænge af hinanden eller af rækkefølgen.
- Fejler en test, **fikser vi den før merge** – ingen `@Disabled` uden en grund i kommentaren.

---

## 10. Øvrige regler

- **Ingen magic numbers/strings** – brug konstanter (`NAME_MAX_LENGTH`) eller konfiguration.
- **Metoder < ~30 linjer.** Gør én ting.
- **Kommentarer forklarer *hvorfor*, ikke *hvad*.** Hvis koden kræver en kommentar for at blive forstået, så omdøb eller del den op.
- **Ingen død kode / udkommenteret kode** på `main`. Git husker det for os.
- **Ingen hemmeligheder i Git.** `application.properties` bruger pladsholdere (fx `${DB_PASSWORD}`), og de rigtige lokale værdier ligger i `.env`, som står i `.gitignore`. `.env.example` med de samme nøgler, men uden værdier, ligger i Git, så alle kan se, hvad de skal udfylde.
- **Formatering:** 4 mellemrum, linjer < 120 tegn. Lad IntelliJ formatere (`Ctrl+Alt+L` / `⌥⌘L`) inden commit.
- **Imports:** ingen `*`-imports (undtagen i tests for `assertThat`, `when`, `verify` osv.).

---

## Tjekliste til reviewer

- [ ] Ligger klasserne i det rigtige feature-modul?
- [ ] Bruger modulet kun andre moduler via deres `<Feature>Api`? Er alt andet package-private?
- [ ] Er entityen oprettet/ændret via `create()` / `update...()` – ingen setters?
- [ ] Er controlleren tynd, bruger `ResponseEntity` og den rigtige statuskode?
- [ ] Er der en request-klasse pr. handling (`Create…Request`, `Update…Request`)?
- [ ] Kaster vi `NotFoundException` / `ConflictException` / `InvalidInputException`?
- [ ] Er navne i HTML/CSS/JS i `kebab-case` og siger, hvad de gør (`member-create-form`)?
- [ ] Går alle API-kald via `<feature>-api.js`, og bruger vi `textContent` i stedet for `innerHTML` med brugerdata?
- [ ] Er der tests til acceptkriterierne, og er de grønne?
- [ ] Er kode, kommentarer og commits på engelsk?