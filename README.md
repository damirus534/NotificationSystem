# System zgłoszeniowy
Projekt realizowany w ramach procesu rekrutacyjnego do firmy Link Technologies sp.z.o.o.

# Zadania i założenia 
1) Założyć projekt na GitHub
2) Projekt będzie zawierał dwa pod projekty (Web i Mobile)
3) Projekt Webowy powinien być napisany przy użyciu frameworka Springa oraz Tapestry 5
dane powinny być zapisywane w bazie danych MariaDB
4) Projekt Webowy powinien umożliwić logowanie do aplikacji
5) Projekt Webowy powinien posiadać funkcjonalność CRUD dla modelu zgłoszenia:
Id zgłoszenia
Data zgłoszenia
Osoba zgłaszająca
Osoba przypisana – (konto użytkownika w systemie)
Treść Zgłoszenia
Adres Zgłoszenia
6) Projekt Webowy powinien wystawiać API REST-owe do pobierania zgłoszeń
7) Projekt Webowy powinien posiadać możliwość tworzenia użytkowników wraz z loginami i
hasłami do systemu. Dane te będą wymagane w aplikacji mobilnej.
8) Aplikacja Mobilna na platformie Android powinna umożliwiać logowanie do systemu
(mechanizm uprawnień w aplikacji webowej)
9) Aplikacja Mobilna powinna pokazywać listę zgłoszeń przypisanych do danego użytkownika
10) Aplikacja Mobilna powinna umożliwić edytowanie i zapisywanie zmian w polach
dozwolonych w konfiguracji ról i uprawnień definiowanych po stronie aplikacji webowej.
11) Aplikacja Mobilna powinna uruchamiać mapę z nawigacją do adresu zapisanego w danym
zgłoszeniu.

# Technologie
## Aplikacja webowa
- Tapestry
- Java
- HTML
- CSS
## Serwer
- Iava
- Spring Boot
- Spring MVC
- JPA
## Aplikacja mobilna
- Kotlin
- Coroutines
- Jetpack Navigation (z SafeArgsami)
- ViewBindings (XML)
- Retrofit

# Aplikacja webowa
## Ekrany
### Logowanie
<img width="956" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/598e9855-ae3a-4908-8261-ed4816b4955d">

### Logowanie - odrzucona walidacja
<img width="956" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/bc07f821-6954-48d8-863f-d08bd7aeccb9">

### Panel Administratora 
<img width="954" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/ffa6b9a9-fe24-4ad4-9624-059802e537a0">

### Panel Administratora - edycja uprawnień
<img width="956" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/7c8d4f12-dbbb-4e83-a6be-c1166b8f4dd1">

### Panel Administratora - dodawanie użytkowników
<img width="956" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/8f057e24-22e2-418a-8a42-bb13a2922607">

### Panel Zgłoszeniowy (dla użytkowników)
<img width="955" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/c1d7e38e-0d7a-404d-b92f-88f2d98de14a">









