# Contributing to AI Ops Monitor

We love contributions! Here's how to get involved.

## Development Setup

### Prerequisites
- Java 21
- Maven 3.9+
- Docker (optional, for containerized development)
- Git

### Local Setup

```bash
# 1. Clone the repository
git clone <REPO_URL>
cd ai-ops-monitor

# 2. Create a feature branch
git checkout -b feature/your-feature-name

# 3. Set environment variables
export OPENAI_API_KEY=sk-your-key-here

# 4. Build the project
./mvnw clean build

# 5. Run tests
./mvnw test

# 6. Start the app
./mvnw spring-boot:run
```

---

## Code Structure

```
src/main/java/com/rahim/aiops/
├── application/          # Application services, DTOs
│   ├── dto/             # Request/Response models
│   ├── mapper/          # Data transformation
│   └── service/         # Business logic
├── domain/              # Domain models, enums
│   ├── enums/           # Severity, Status
│   ├── model/           # Incident domain object
│   └── port/            # Port interfaces (repositories)
├── infrastructure/      # Technical implementations
│   └── persistence/     # Database entities, repositories
└── presentation/        # REST controllers
    ├── controller/      # REST endpoints
    └── exception/       # Exception handling
```

## Development Guidelines

### Architecture Principles
- **Layered Architecture**: Separate domain, application, infrastructure, presentation
- **Dependency Injection**: Use Spring's @Component, @Service, @Repository
- **Ports & Adapters**: Use interfaces (ports) for key dependencies
- **Clean Code**: Keep methods small, focused, and testable

### Coding Standards
- Follow Spring conventions
- Use Lombok for boilerplate (getters, constructors, logging)
- Prefer records for immutable DTOs
- Write meaningful variable names
- Add comments for complex logic

### Testing Requirements
- Write unit tests for all services
- Use JUnit 5 + Mockito
- Aim for >80% code coverage
- Test happy path and error cases
- Mock external dependencies (AI, databases)

#### Example Test
```java
@ExtendWith(MockitoExtension.class)
class MyServiceTest {
    @Mock
    private DependencyPort dependency;
    
    @InjectMocks
    private MyService service;
    
    @Test
    void myMethod_shouldReturnExpectedValue() {
        // Arrange
        when(dependency.call()).thenReturn(value);
        
        // Act
        Result result = service.myMethod();
        
        // Assert
        assertThat(result).isNotNull();
        verify(dependency).call();
    }
}
```

---

## Git Workflow

### 1. Create Feature Branch
```bash
git checkout -b feature/your-feature
# or
git checkout -b bugfix/issue-description
# or
git checkout -b docs/documentation-update
```

### 2. Make Changes
- Keep commits atomic and focused
- Write descriptive commit messages

```bash
git add .
git commit -m "feat: add new incident analysis service"
# Commit message format:
# feat: new feature
# fix: bug fix
# test: add tests
# docs: documentation
# refactor: code refactoring
```

### 3. Push & Create PR
```bash
git push origin feature/your-feature
```

Create a Pull Request on GitHub with:
- Clear title and description
- Reference any related issues
- Ensure CI/CD passes

### 4. Code Review
- Address review comments
- Update the PR accordingly
- Get approval before merging

### 5. Merge
Once approved, merge to main and delete the feature branch.

---

## Running Tests

### All Tests
```bash
./mvnw test
```

### Specific Test Class
```bash
./mvnw -Dtest=com.rahim.aiops.application.service.IncidentServiceTest test
```

### With Coverage Report
```bash
./mvnw test jacoco:report
# Report generated in target/site/jacoco/index.html
```

---

## Docker Development

### Build Docker Image
```bash
docker build -t ai-ops-monitor:dev .
```

### Run in Docker
```bash
docker run -p 8080:8080 \
  -e OPENAI_API_KEY=sk-... \
  ai-ops-monitor:dev
```

### Docker Compose for Full Stack
```bash
docker-compose up -d
docker-compose logs -f ai-ops-monitor
```

---

## Code Review Checklist

Before submitting a PR, ensure:

- [ ] Code follows project style guidelines
- [ ] New tests added for new functionality
- [ ] All tests pass (`./mvnw test`)
- [ ] No hardcoded secrets or credentials
- [ ] Documentation updated if needed
- [ ] Commit messages are clear and descriptive
- [ ] Branch is up-to-date with main

---

## Adding New Features

### 1. Domain-Driven Approach
```java
// 1. Define domain model
public record MyEntity(...) { }

// 2. Create port interface
public interface MyPort {
    MyEntity save(MyEntity entity);
}

// 3. Implement adapter
@Repository
public class MyAdapter implements MyPort { }

// 4. Create service
@Service
public class MyService {
    private final MyPort port;
    
    public void process() {
        // Use port interface
    }
}

// 5. Create controller
@RestController
public class MyController {
    private final MyService service;
    
    @PostMapping("/endpoint")
    public ResponseEntity<?> handle(...) { }
}

// 6. Add tests for each layer
```

### 2. Follow Package Structure
- New services go in `application/service/`
- New DTOs go in `application/dto/`
- New controllers go in `presentation/controller/`
- Entities go in `infrastructure/persistence/entity/`

### 3. Add Tests Alongside Code
- Service test in `src/test/java/.../service/`
- Controller test in `src/test/java/.../controller/`

---

## Reporting Issues

Use GitHub Issues to report:
- **Bug**: Describe what's broken and how to reproduce
- **Enhancement**: Suggest improvements or new features
- **Documentation**: Point out unclear or missing docs

Include:
- Environment (Java version, OS)
- Steps to reproduce
- Expected vs actual behavior
- Stack trace (if applicable)

---

## Documentation

### README.md
- Project overview, features, tech stack
- Quick start and installation
- API endpoints overview

### DOCKER.md
- Docker setup, Docker Compose config
- Usage examples, troubleshooting

### GETTING_STARTED.md
- Quick start for new users
- Common commands

### Code Comments
- Add Javadoc for public methods
- Explain why, not what (code already shows what)
- Link to related documentation or PRs

---

## CI/CD Integration

When you push code:
1. ✅ Tests automatically run
2. ✅ Build verification
3. ✅ Code quality checks (if configured)
4. ✅ Results appear in PR

Make sure your changes:
- Compile without errors
- Pass all tests
- Don't break existing functionality

---

## Questions?

- Check existing issues and PRs
- Review DOCKER.md and GETTING_STARTED.md
- Ask in project discussions
- Contact maintainers

---

**Thank you for contributing! 🙏**
