Misie SOS - System Zarządzania Biblioteką
 
Projekt **Misie SOS** to aplikacja do zarządzania kontem studenta oraz biblioteką, pozwalająca użytkownikom na rezerwację, wypożyczanie i ocenianie zasobów bibliotecznych, sprawdzenie ocem, zapisanie się oraz wypisanie z zajęć.

**Technologie**:
 - **Java 17+**  
- **Spring Boot** (Spring Data JPA, Hibernate, Spring MVC)  
- **HSQLDB** (baza danych w trybie embedded)  
- **JUnit & AssertJ** (testy jednostkowe)  
- **Maven** (zarządzanie zależnościami)

**Struktura katalogów**: 

```
misie-sos
│── src
│   ├── main
│   │   ├── java/pl/atins/misie/sos
│   │   │   ├── dao                                          # Warstwa dostępu do bazy danych
|   |   |   |   |-impl                                       # Implementacja DAO
│   │   │   ├── model                                        # Encje JPA
|   |   |   |── SosApplication
|   |   |── resources
|   |   |   |── application.properties
|   |   |   |── create-db.sql                                # skrypt inicjalizujący bazę danych 
│   │   ├── webapp/WEB-INF
│   │   │   |── applicationContext.xml                       # Konfiguracja Springa
|   |   |   ├── rootApplicationContext.xml                   # Konfiguracja Springa
│   │   │   ├── web.xml                                      # Konfiguracja aplikacji webowej
│   ├── test
│   │   ├── java                                             # Testy jednostkowe DAO
|   |   ├── resources/applicationContext-test.xml            # Konfiguracja Spring używana w testach
│── pom.xml                                                  # Konfiguracja Maven
│── .gitignore                                               # Ignorowane pliki w repozytorium
```

**Współtwórcy**:

- Jakub Puchała - 6485
- Marcin Stachera - 7775
- Patrycja Urban - 7767
- Paweł Milczyński - 7771


