# scl_Meteo_Auriane_Nathan
# Projet SCL – Application Météo

## Description

Cette application Java permet de :

1. Saisir une **latitude** et une **longitude**.
2. Appeler une **API météo** afin de récupérer les informations météorologiques.
3. Désérialiser les données JSON à l’aide de **Jackson**.
4. Récupérer les informations sur le **pays** via un service web.
5. Insérer les données météo, la station météo et le pays dans une **base de données**.

Le projet utilise **Maven** pour la gestion des dépendances.

---

# Technologies utilisées

* Java
* Maven
* Jackson (désérialisation JSON)
* API météo (OpenWeatherMap)
* API pays HE-ARC
* Base de données Oracle
* Git / GitHub

---

# Structure du projet

```
src
├── app
│   └── Main.java
│
├── business
│   ├── Meteo.java
│   ├── Pays.java
│   └── StationMeteo.java
│
├── exception
│   ├── PaysExisteDeja.java
│   └── StationExisteDeja.java
│
├── persistence
│   └── DBConnection.java
│
├── repository
│   ├── MeteoRepository.java
│   ├── PaysRepository.java
│   └── StationRepository.java
│
├── service
│   ├── ApiCallService.java
│   ├── IOWMManager.java
│   ├── MeteoDeserializer.java
│   ├── OWMManager.java
│   ├── PaysDeserializer.java
│   ├── PaysService.java
│   └── StationMeteoDeserializer.java
│
└── tools
    ├── EnvProperties.java
    └── Log.java
```

---

# Configuration du projet

Avant d'exécuter l'application, il faut compléter le fichier .env contenant les paramètres suivants :

```
USER=
PASSWORD=
HOST=
PORT=
SID=

APIKEY=
```

### Paramètres de base de données

| Paramètre | Description                             |
| --------- | --------------------------------------- |
| USER      | Nom d'utilisateur de la base de données |
| PASSWORD  | Mot de passe de la base                 |
| HOST      | Adresse du serveur de base de données   |
| PORT      | Port de connexion                       |
| SID       | Identifiant de la base Oracle           |

### Clé API météo

| Paramètre | Description            |
| --------- | ---------------------- |
| APIKEY    | Clé API OpenWeatherMap |

---

# Exécution

Lancer la classe :

```
Main.java
```

L'application demande ensuite :

```
Entrer une latitude :
Entrer une longitude :
```

Exemple :

```
Entrer une latitude : 46.99
Entrer une longitude : 6.93
```

L'application :

1. Appelle l’API météo
2. Désérialise les données JSON
3. Récupère le pays correspondant
4. Insère les données dans la base de données

---

# Auteurs

Projet réalisé par Auriane Ndonfack et Nathan Favre
