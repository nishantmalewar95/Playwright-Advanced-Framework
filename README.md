# Next-Gen Enterprise Banking Automation Ecosystem (ParaBank)

[![Tech Stack: Playwright-Java](https://img.shields.io/badge/Tech%20Stack-Playwright--Java-green.svg)](https://playwright.dev/java/)
[![Test Runner: TestNG](https://img.shields.io/badge/Test%20Runner-TestNG-orange.svg)](https://testng.org/)
[![Architecture: Strict POM](https://img.shields.io/badge/Architecture-Strict%20POM-blue.svg)]()
[![AI Triage: MCP Enabled](https://img.shields.io/badge/AI%20Triage-MCP%20Enabled-blueviolet.svg)]()

An enterprise-grade, highly scalable test automation framework engineered from the ground up utilizing the **Playwright-Java** ecosystem paired with **TestNG**. This framework implements a resilient automation architecture targeting the complex transactional workflows of the **ParaBank** core banking platform (`parabank.parasoft.com`). It serves as a blueprint for isolating fragile UI layers, handling asynchronous state mutation, and leveraging AI-driven diagnostics to eliminate flaky test cycles.

---

## 🛠 Technical Stack & Dependency Matrix

*   **Core Engine:** Playwright-Java (v1.x+) - Leveraging native Chromium/Webkit/Firefox browser contexts.
*   **Test Architecture:** TestNG (v7.x+) - Orchestrating multi-threaded execution loops, assertion layers, and test hooks.
*   **Design Pattern:** Decoupled Page Object Model (POM) with dynamic state management.
*   **Infrastructure & Cloud Containerization:** Dockerized execution environments configured for headless execution.
*   **AI Diagnostics Track:** Model Context Protocol (MCP) custom utilities paired with LLM context engines for automated Trace Log Parsing.
*   **Data Layer:** Secure Type-Safe Configuration Readers handling environment-specific property files and runtime injectors.

---

## 📐 Architecture Blueprint

The framework enforces a strict separation of concerns to guarantee long-term maintainability and prevent regression suite degradation:
├── .github/workflows/       # CI/CD pipeline definitions (GitHub Actions)
├── src/
│   ├── main/java/com/parabank/ecosystem/
│   │   ├── drivers/         # BrowserContext & Page lifecycle provisioning
│   │   ├── config/          # Encapsulated Config Readers & Property loaders
│   │   ├── pages/           # Strict Page Objects (Encapsulated Locators & Actions)
│   │   │   ├── LoginPanel.java
│   │   │   ├── AccountOverviewPage.java
│   │   │   ├── TransferFundsPage.java
│   │   │   └── BillPayPage.java
│   │   └── utils/           # Shared utility classes & Custom MCP Connectors
│   └── test/java/com/parabank/ecosystem/tests/
│       ├── base/            # BaseTest configuration, Hooks, and Tracing setup
│       └── banking/         # E2E Business Process Regression Suites
├── src/test/resources/
│   ├── testdata/            # Environment matrices and application configs
│   └── testng.xml           # Thread-count allocation & Suite topology configurations
└── pom.xml                  # Unified Maven Dependency Matrix

### Key Engineering Features:
1. **Asynchronous UI Resiliency:** Replaced archaic, flake-prone thread sleeps with Playwright’s native **auto-waiting** logic and **web-first assertions**. The framework actively syncs with ParaBank's asynchronous AJAX loaders and background network requests.
2. **Immutable State Security:** Banking user context and states are localized within independent TestNG threads. Dynamic runtime test variables are managed via non-destructive config engines, preventing concurrent mutation risks during high-concurrency parallel loops.
3. **AI-Powered Triage Engine (MCP):** Integrated a custom post-execution handler utilizing the **Model Context Protocol (MCP)**. Upon any test failure, the utility interceptor parses the binary Playwright trace files, network logs, and DOM snapshots, routing them to an LLM context window to generate an instantaneous Root Cause Analysis (RCA) report.

---

## 🏦 Covered End-to-End Banking Workflows

The suite validates critical path financial transactions with absolute data integrity:

*   **Dynamic User Registration & IAM Lifecycle:** Handles programmatic generation of test identities, multi-factor field validation, and subsequent profile generation.
*   **Multi-Factor Access Verification:** Structural validation of strict credential assertion gates and error-path resilience.
*   **Multi-Tier Account Provisioning:** Automated creation of checking and savings variants across complex backend endpoints (hybrid UI/API triggers).
*   **Transactional Ledger Processing (Funds Transfer):** Orchestrates funds relocation across newly provisioned balances, asserting ledger consistency across database and front-end states.
*   **Account Ledger Auditing:** Deep-DOM scanning of dynamic transaction rows to certify transaction descriptions, tracking numbers, and balanced accounts post-transfer.

---

## ⚙️ Local Installation & Configuration

### Prerequisites
*   Java Development Kit (JDK) 17 or higher
*   Apache Maven 3.8+

### Step 1: Clone and Initialize
```bash
git clone [https://github.com/nishantmalewar95/Playwright-Advanced-Framework.git](https://github.com/nishantmalewar95/Playwright-Advanced-Framework.git)
cd Playwright-Advanced-Framework
mvn clean install -DskipTests
Step 2: Configure Environment Parameters
Navigate to src/test/resources/testdata/config.properties and define your target execution variables:

Properties
env=prod
browser=chromium
headless=true
mcp.triage.enabled=true
🚀 CI/CD Pipeline Execution Commands
The suite is fine-tuned for high-velocity DevOps pipelines. Use the following profiles to trigger containerized or headless parallel test execution configurations:

Run Full Regression Suite (Headless Parallel Loop)
Bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml -Dheadless=true
Execute Isolated Banking Workflows
Bash
mvn test -Dtest=TransferFundsTest,AccountProvisioningTest
Analyze Post-Failure AI Triage Logs
Bash
# Triggers custom MCP pipeline to generate markdown RCA summaries
mvn exec:java -Dexec.mainClass="com.parabank.ecosystem.utils.McpTriageProcessor"
