Misie SOS - System Zarządzania Biblioteką
 
Projekt **Misie SOS** to aplikacja do zarządzania kontem studenta oraz biblioteką, pozwalająca użytkownikom na rezerwację, wypożyczanie i ocenianie zasobów bibliotecznych, sprawdzenie ocem, zapisanie się oraz wypisanie z zajęć.

**Technologie**:
 - **Java 17+**  
- **Spring Boot** (Spring Data JPA, Hibernate, Spring MVC)  
- **HSQLDB** (baza danych w trybie embedded)  
- **JUnit & AssertJ** (testy jednostkowe)  
- **Maven** (zarządzanie zależnościami)

 **Klonowanie repozytorium**:
   git clone https://github.com/monachos/misie.git

**Struktura katalogów**: 
misie-sos
- │── src
- │   ├── main
- │   │   ├── java/pl/atins/misie/sos
- │   │   │   ├── dao          # Warstwa dostępu do bazy danych
- │   │   │   ├── model        # Encje JPA
- │   │   │   ├── service      # Logika biznesowa
- │   │   │   ├── controller   # Kontrolery REST API
- │   │   ├── resources
- │   │   │   ├── applicationContext.xml  # Konfiguracja Springa
- │   │   │   ├── web.xml  # Konfiguracja aplikacji webowej
- │   ├── test
- │   │   ├── java/pl/atins/misie/sos/dao  # Testy DAO
- │   │   ├── java/pl/atins/misie/sos/service  # Testy serwisów

**Współtwórcy**:

- Jakub Puchała - 6485
- Marcin Stachera - 7775
- Patrycja Urban - 7767
- Paweł Milczyński - 7771


