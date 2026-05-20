# GitHub Actions Quick Reference

## What Happens When You Push Code

```
git push origin main
        ↓
GitHub detects push
        ↓
Workflow "Build & Test" starts automatically
        ↓
    ├─ Checkout code
    ├─ Setup Java 21
    ├─ Build with Maven
    ├─ Run tests (JUnit 5)
    ├─ Build Docker image
    └─ Upload artifacts
        ↓
Build succeeds (or fails)
        ↓
Check GitHub Actions tab for results
```

## Quick Actions

### View Build Status
1. Go to GitHub repo
2. Click **Actions** tab
3. See all workflow runs
4. Click on a run to see details

### Download Artifacts
After a successful build:
1. Go to the workflow run
2. Scroll to **Artifacts** section
3. Download JAR or test reports

### Run Locally
```bash
# Simulate GitHub Actions environment locally
brew install act
act push
```

## PR Workflow

```
1. Create feature branch
2. Make changes & commit
3. Push: git push origin feature/your-feature
4. Create Pull Request
5. GitHub Actions automatically runs tests
6. If tests pass ✅ → PR is safe to merge
7. If tests fail ❌ → Fix and push again
8. Once approved → Merge to main
```

## Environment

- **OS**: Ubuntu Latest (latest LTS)
- **Java**: JDK 21 (Temurin)
- **Maven**: Latest (auto-installed)
- **Docker**: Available in runner

## Caching

- ✅ Maven dependencies cached
- ✅ Docker layers cached
- ✅ Significantly speeds up builds

## Configuration

**To enable Docker Hub publishing:**

1. Create Docker Hub token
2. Add GitHub Secrets:
   - `DOCKER_USERNAME`
   - `DOCKER_PASSWORD`
3. Push to main → auto publishes

See `.github/WORKFLOWS.md` for details.

## Monitoring

### Health Check
- Green checkmark = Build passing ✅
- Red X = Build failing ❌
- Yellow dot = Build in progress 🟡

### Badge in README
Shows build status at a glance:
```
[![Build Status](https://github.com/...)](...)
```

## Tips

- Commit messages appear in Actions
- All runs are logged and searchable
- Artifacts kept for 90 days by default
- Failed builds send email notifications
- Workflows are version controlled (in .github/)

## Support

- See `.github/WORKFLOWS.md` for full documentation
- GitHub Actions docs: https://docs.github.com/en/actions
- Troubleshooting: Check workflow logs for errors
