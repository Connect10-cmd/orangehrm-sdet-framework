# OrangeHRM Selenium Test Framework

A focused Java 17, Selenium 4, TestNG, and Maven UI automation framework. The project emphasizes reusable framework capabilities and a small number of high-value authentication and PIM workflows rather than a large collection of isolated tests.

## Architecture

- `com.sachin.framework.config`: thread-safe environment configuration (`-Denv=dev|qa|uat`). JVM properties can override settings such as `url` and `browser`.
- `com.sachin.framework.driver`: one WebDriver per TestNG thread, with Chrome, Firefox, and Edge selection.
- `com.sachin.framework.pages`: BasePage shared explicit waits/actions; page objects model UI behavior and state without assertions.
- `com.sachin.framework.components`: reusable page sections, including side navigation.
- `com.sachin.framework.utils`: centralized waits, JSON test-data loading, and screenshot storage.
- `com.sachin.tests`: assertion-owning tests, per-method browser lifecycle, data providers, and an `ITestListener` for diagnostics.

Each test gets a fresh browser session and closes it in an always-run teardown. This avoids state leakage between authentication/business workflows and avoids unsafe reuse of a mutable browser session. WebDriver is thread-local, but TestNG execution remains sequential by default: the public demo is shared mutable state. The employee lifecycle case creates a unique employee and deletes that same record.

## Run

```text
mvn clean test
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
mvn test -Denv=qa
mvn test -Denv=uat -Dbrowser=firefox
```

Groups include `smoke`, `sanity`, `regression`, `critical`, `authentication`, and `pim`. Environment files are in `src/test/resources/config/`. The included dev URL is the public OrangeHRM demo. QA/UAT files intentionally start with that same reachable demo URL as a runnable example; replace the URL in each file with the corresponding managed environment before using those environments. Use CI-managed secrets and do not commit private credentials.

Login fixtures live in `src/test/resources/testdata/login-data.json`; employee fixture values live in `employee-data.json`. The demo account is public sample data, not a production credential. `TestDataReader` centralizes JSON access so test code is not coupled to a particular parsing library.

## Test diagnostics and reporting readiness

`TestListener` records test duration, exception, current URL, browser metadata, and a failure screenshot path in the TestNG result attributes. Failure screenshots are written under `screenshots/failures/` with unique timestamped names; pass screenshots are not captured by default. Framework logs use SLF4J backed by Log4j2 and are written to the console and rolling `logs/framework.log`.

The existing ExtentReports dependency is retained for a future reporter adapter. Listener result attributes provide a neutral diagnostics boundary for future ExtentReports or Allure integration; no report adapter is currently enabled.

## Locator and wait policy

Prefer semantic `id`/`name` locators, then stable attributes and text-based selectors. Locators based on OrangeHRM visible labels, placeholders, and table classes are documented in the page/component classes as moderately stable UI contracts. Explicit waits are centralized in `WaitUtils`; there are no fixed sleeps.

## Business coverage

- Authentication: login page smoke check, valid login (dashboard UI + URL + user session), invalid username/password, empty-field validation, and logout.
- PIM: authenticated navigation and one isolated create → search → delete employee lifecycle.

The PIM lifecycle test uses the public demo's live data store. For stable CI, point the environment configuration to a dedicated test instance with a resettable test account.
