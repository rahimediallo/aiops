# GitHub Actions Workflows

Cette section décrit les workflows CI/CD automatisés du projet.

## Workflows Disponibles

### 1. **maven.yml** - Build & Test (Automatique)

**Déclenché par :**
- Push sur `main`, `develop`, ou branches `feature/*`
- Pull Request vers `main` ou `develop`

**Actions :**
1. ✅ Checkout du code
2. ✅ Setup Java 21 (Temurin)
3. ✅ Build Maven (sans tests)
4. ✅ Exécution des tests (JUnit 5)
5. ✅ Upload des résultats de tests
6. ✅ Build de l'image Docker
7. ✅ Upload du JAR généré

**Cache :**
- Maven dependencies cachées pour accélération
- Docker layers cachées

### 2. **docker-publish.yml** - Publish Docker Image (Optionnel)

**Déclenché par :**
- Succès du workflow Maven sur la branche `main`
- Tags version (`v*`)
- Exécution manuelle

**Actions :**
1. ✅ Build Docker image
2. ✅ Push vers Docker Hub (si crédentiels configurés)
3. ✅ Tags automatiques : branch, version, SHA

**Prérequis :**
- Compte Docker Hub
- Secrets GitHub configurés

---

## Configuration des Secrets (Optionnel pour Docker Publish)

Pour activer la publication automatique sur Docker Hub :

### Étape 1 : Créer un token Docker Hub

1. Va sur https://hub.docker.com/settings/security
2. Crée un "Personal Access Token"
3. Copie le token

### Étape 2 : Ajouter les secrets GitHub

1. Va sur ton repo GitHub → **Settings** → **Secrets and variables** → **Actions**
2. Crée deux secrets :
   - **DOCKER_USERNAME** = ton username Docker Hub
   - **DOCKER_PASSWORD** = ton token

### Exemple

```
DOCKER_USERNAME: rahimediallo
DOCKER_PASSWORD: dckr_pat_xxxxxxxxxxxxx
```

### Étape 3 : Vérifier le workflow

- Fais un push sur `main`
- Regarde l'onglet **Actions** dans GitHub
- Les workflows devraient s'exécuter automatiquement

---

## Utilisation Locale (Optionnel)

### Simuler le workflow localement avec Act

Si tu veux tester les workflows localement avant de les pousser :

```bash
# Installation (macOS)
brew install act

# Lancer le workflow Maven
act push -j build

# Lancer le workflow Docker
act push -j build-and-push
```

---

## Résultats & Artifacts

### Après chaque build

**Artifacts disponibles :**
- Test reports : `target/surefire-reports/`
- JAR binaire : `target/aiops-*.jar`
- Docker image : `ai-ops-monitor:sha-xxx`

**Accès :**
- Github Actions tab → Latest workflow run → Artifacts section

---

## Status Badge (Pour README)

Ajoute ce badge au README pour montrer le status du build :

```markdown
[![Build Status](https://github.com/yourusername/ai-ops-monitor/actions/workflows/maven.yml/badge.svg)](https://github.com/yourusername/ai-ops-monitor/actions)
```

Remplace `yourusername` par ton username GitHub.

---

## Troubleshooting

### Tests échouent dans GitHub Actions mais pas localement

```bash
# Vérifie que tu utilises la même version Java
java -version

# Réexécute les tests localement
./mvnw clean test
```

### Docker build échoue

```bash
# Vérifie que le Dockerfile est valide
docker build -t test .

# Simule le build GitHub localement
act push
```

### Secrets non reconnus

1. Vérifie que tu as suivi la section "Configuration des Secrets"
2. Réfraîchis la page après avoir ajouté un secret
3. Les noms doivent correspondre exactement (case-sensitive)

---

## Optimisations

### Cache Maven
- Les dépendances Maven sont cachées automatiquement
- Réduit le temps de build de ~70%

### Cache Docker
- Les layers Docker sont cachés via GitHub Actions Cache
- Réduit le temps de build Docker de ~60%

### Parallélisation
- Build et tests peuvent tourner en parallèle
- Ajustable dans les workflows

---

## Prochaines Améliorations

- [ ] Code coverage reporting (JaCoCo → Codecov)
- [ ] SonarQube scan
- [ ] Linting (Checkstyle, SpotBugs)
- [ ] Security scanning (Dependabot)
- [ ] Performance benchmarks
- [ ] Deploy automatique sur staging
- [ ] Release automation (sémantic-release)

---

## Références

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven Plugin for GH Actions](https://github.com/actions/setup-java)
- [Docker Build Push Action](https://github.com/docker/build-push-action)
- [Act - Local Workflow Testing](https://github.com/nektos/act)
