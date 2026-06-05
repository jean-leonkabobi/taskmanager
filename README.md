# TaskManager

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Vue](https://img.shields.io/badge/Vue-3.5-4FC08D.svg?logo=vue.js)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F.svg?logo=spring-boot)
![License](https://img.shields.io/badge/license-MIT-green.svg)

Application moderne de gestion de tâches avec drag & drop, mode sombre et tableau de bord statistique.

## Fonctionnalités

- **Authentification JWT** - Inscription et connexion sécurisées
- **CRUD des tâches** - Créer, lire, modifier et supprimer des tâches
- **Drag & Drop** - Réorganiser les tâches par glisser-déposer
- **Tableau de bord** - Statistiques et taux d'avancement
- **Mode sombre/clair** - Thème personnalisable
- **Responsive** - Adapté à tous les écrans
- **Design moderne** - Inspiré de Linear, Notion et Asana

## Technologies

### Frontend
- **Vue 3** - Framework JavaScript progressif
- **Pinia** - Gestion d'état
- **Vue Router** - Navigation
- **Tailwind CSS** - Styles utilitaires
- **Axios** - Requêtes HTTP
- **Lucide Icons** - Icônes modernes
- **Vue Draggable** - Drag & drop

### Backend
- **Spring Boot 3.2** - Framework Java
- **Spring Security** - Authentification JWT
- **PostgreSQL** - Base de données
- **JPA/Hibernate** - ORM
- **Swagger** - Documentation API

## Charte graphique

  Couleur     Code                  Utilisation 
  
  Primary     #2563EB    Boutons, liens, éléments actifs 
  
  Secondary   #1E293B    Sidebar, titres, navbar 
  
  Success     #22C55E    Tâches terminées 
  
  Warning     #F59E0B    Priorité moyenne 
  
  Error       #EF4444    Suppression, priorité haute 
  
  Background  #F8FAFC      Fond principal 
  
  Card        #FFFFFF           Cartes 

**Police** : Inter / Poppins  

**Style** : Minimaliste, moderne, professionnel

## Installation

### Prérequis
- Node.js 18+
- Java 17+
- PostgreSQL
- Maven

### Backend

cd taskmanager-backend

# Créer la base de données PostgreSQL
createdb taskmanager_db
# Lancer l'application
./mvnw spring-boot:run
Le backend démarre sur http://localhost:8080/api

### Frontend
cd taskmanager-frontend
# Installer les dépendances
npm install
# Lancer le serveur de développement
npm run dev
Le frontend démarre sur http://localhost:5173

### API Endpoints
### Authentification
POST   /api/auth/register    - Inscription
POST   /api/auth/login       - Connexion

### Tâches
GET    /api/tasks             - Liste des tâches
POST   /api/tasks             - Créer une tâche
GET    /api/tasks/{id}        - Détail d'une tâche
PUT    /api/tasks/{id}        - Modifier une tâche
DELETE /api/tasks/{id}        - Supprimer une tâche
PATCH  /api/tasks/{id}/status - Changer le statut
PUT    /api/tasks/reorder     - Réorganiser les tâches

### Tableau de bord
GET    /api/dashboard/stats   - Statistiques

### Structure du projet
taskmanager/
├── taskmanager-backend/          # API Spring Boot

│   └── src/main/java/com/taskmanager/

│       ├── config/               # Configuration

│       ├── controller/           # Contrôleurs REST

│       ├── service/              # Logique métier

│       ├── repository/           # Accès aux données

│       ├── model/                # Entités JPA

│       ├── dto/                  # Objets de transfert

│       └── security/             # Sécurité JWT

│
├── taskmanager-frontend/         # Application Vue 3

│   └── src/

│       ├── components/           # Composants réutilisables

│       ├── views/                # Pages

│       ├── stores/               # Stores Pinia

│       ├── services/             # Services API

│       ├── composables/          # Hooks réutilisables

│       ├── router/               # Configuration des routes

│       └── utils/                # Utilitaires

│
└── screenshots/                  # Captures d'écran

### Compte de test
  Email: jeanleon@example.com
  
  Mot de Passe: password123

### Auteur
Jean-Leon KABOBI
Email : jeanleon.kabobi@gmail.com / kabobi.jeanleon.dev@gmail.com
