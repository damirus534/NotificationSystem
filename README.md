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

### Panel Zgłoszeniowy - dodawanie zgłoszeń 
<img width="955" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/43677c28-82bf-4758-82d2-5bffc29b953a">

### Panel Zgłoszeniowy - dodawanie zgłoszeń - odrzucona walidacja
<img width="955" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/cf9fa2b9-7c98-4069-9eb5-df537fbb0c46">

### Panel Zgłoszeniowy - edycja i usuwanie notyfikacji 
<img width="956" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/3cbb6595-9575-4c2c-97c7-1a6d86eceb6c">


## Opis
Aplikacja webowa pozawala na logowanie użytkowników do aplikacji
Zadanie 4 ✅

Aplikacja webowa posiada funkcjonalność CRUD dla modelu zgłoszenia (wraz z sprawdzaniem uprawnień)
Zadanie 5 ✅

Aplikacja webowa posiada możliwość tworzenia użytkowników wraz z loginami i hasłami. 
Zadanie 7 ✅

Aplikacja webowa pozwala na konfiguracje uprawnień do edycji pól zgłoszeń i dostępu do aplikacji mobilnej
Zadanie 10.5 ✅

## Dodatkowo
- Aplikacja webowa posiada zabezpieczenia do poruszania się między stronami. Po przejściu na adres, który wymaga konkretnych obiektów wystąpi błąd i zostanie wyświetlony komunikat.
### Strona z brakiem dostępu do strony
<img width="956" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/5b0a86cc-3df1-45e3-80d0-8d540dc50c63">

- Aplikacja webowa posiada zabezpieczenie w postaci szyfrowania dla obiektów przekazywanych jako @PageActivationContext w url.
### Przykładowy url z zaszyfrowanymi danymi
``` http://localhost:8080/web/notification/ntyVr2Gb4aftUhNAhTde20ttXjHFPi7stG4savhKY1npzACGMLXjXK6AkBXQgCOIi$002brQL17dgukmQuuBsbX5hNOu$002fWcwGXd2GotdtV5t5aYzvw1HEu9BtvqRSTztf0kO0S8F$002f$002b7BXi5eiwko$002fRGFDg1zraYaLh5fiUM524GmrVuXSZCBPJpjRn6xgbSbHAu6XJmlU2WUFxv29TMh1Raerg$003d$003d ```

## Poprawki/Usprawnienia, które wprowadziłbym jakby było więcej czasu
- Spróbowałbym jeszcze raz zrobić grida z checkboxami, który edytowałby się na bieżąco. Miałem problem z dwustronnym bindingiem. Pojawiał się błąd z read-only property, która miała poprawnie ustawiony getter i setter.
- Dodałbym możliwość uzunięcia bezpośrednio zgłoszenia z tabeli. Miałem problem z Action-linkiem, który nie działał prawidłowo w loopie, nawet nadając unikalny context.
- Czysto kodowo to zrobiłbym porządek z plikami css i tml przygotowywując gotowe componenty layoutów. Stworzyłbym także klasy po których bym dziedziczył aby nie kopiować tyle kodu w moich plikach Page.java
- Obsługa strony 404
- Stworzył system paginacji dla list i tabel

# Serwer
Spełnia swoją role jako bufor między aplikacjami klienckimi a bazą danych.
## Opis
Serwer łączy się z bazą danych MariaDB
Zadanie 3 ✅

Serwer wystawia API REST-owe do pobierania zgłoszeń. W zasadzie też użytkowników. Pozwala na edycje notyfikacji
Zadanie 6 ✅

## Dodatkowo
- Stworzono dwie klasy z testami jednostkowymi

## Poprawki/Usprawnienia które wprowadziłbym jakby było więcej czasu
- Stworzyłbym system czasowych tokenów autoryzujących użytkownika - sesje
- Poprawić odpowiedzi z kontrolerów, aby tam były typy generyczne
- Stworzyłbym filtr dla requestów, które są błędne, aby odpowiedzi trafiające z serwera z błędami były bardziej "fancy"
- Przygotowałbym testy dla kluczowych metod. Niestety nie pisze w formie TDD, więc testowanie jest dla mnie czasochłonne
- Zrobiłbym bardziej rozbudowaną obsługę wyjątków, które mogę wystąpić w systemie, aby użytkownik wiedział lepiej co poszło nie tak

# Aplikacja mobilna
## Ekrany
### Logowanie
<img width="212" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/878f1735-f5bd-4103-9f3c-a725a2850268">

### Logowanie - Z błędem
<img width="209" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/a2179ae7-68bf-495f-bd09-4e327ea94d0e">

### Logowanie - Puste pola
<img width="211" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/310e8cde-ce3e-47af-8989-a31351a75451">

### Ekran z przypisanymi do zalogowanego użytkownika notyfikacjami
<img width="215" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/00fc5fc9-70d8-44ed-b338-36bfad01f8ce">

### Ekran edycji notyfikacji
<img width="215" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/96726a76-4bf4-4576-94ee-50e0fc4325be">

### Ekran edycji notyfikacji - Z błędem
<img width="213" alt="image" src="https://github.com/damirus534/NotificationSystem/assets/61499883/38e5d13f-753e-4cad-8001-50e3e537ac99">

## Opis
Aplikacja mobilna umożliwia logowanie do systemu. Sprawdza dostęp do aplikacji w formie uprawnienia na użytkowniku.
Zadanie 8 ✅

Aplikacja mobilna wyświetla liste z przypisanymi dla zalogowanego użytkownika zgłoszeniami.
Zadanie 9 ✅

Aplikacja mobilna pozwala na edycje zgłoszenia. Pozwala tylko na edycje pól tych, dla których użytkownik ma uprawnienia.
Zadanie 10 ✅

Aplikacja mobilna uruchamia mape z nawigacją do adresu zapisanego w zgłoszeniu
Zadanie 11 ✅

## Poprawki i usprawnienia które zrobiłbym posiadając więcej czasu.
- Zaprojektował widoki horyzontalne
- Stworzył klasy do czytelniejszej obsługi odpowiedzi z serwera
- Dla listy z zgłoszeniami zaimplementował system paginacji.
- Przygotował testy jednostkowe
- Poprawił walidatory i zrobił je bardziej generyczne

# Instalacja i testy
## Projekt Springowy i Tapestry
Należy:
Samodzielnie uruchomić baze MariaDB.
Pobrać projekt, następnie otworzyć w IDE.
Skonfigurować plik application.properties w folderze web\damian\src\main\resources.
To jest moja konfiguracja.
```
server.port:8080

#db - credentials
spring.datasource.url=jdbc:mariadb://localhost:3306/recrutainmentdb?useSSL=false
spring.datasource.username=root
spring.datasource.password=root
#db - config
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
--spring.jpa.hibernate.ddl-auto=create-drop --to odkomentować przy pierwszym uruchomieniu
spring.jpa.hibernate.ddl-auto=update --to zakomentować przy pierwszym uruchomieniu
logging.level.org.hibernate.sql=DEBUG
logging.level.org.springframework.jdbc.core=DEBUG
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

``` 
## Aplikacja mobilna
Aplikacja mobilna ma tak skonfigurowane połączenie z serwerem (bo jest lokalnie) że działa tylko z emulatora AVD. Dlatego wymagane jest także uruchomienie projektu z Android Studio IDE na tej samej maszynie co aplikacje springową.

# Podsumowanie
Bardzo dobrze sie bawiłem w trakcie realizowania tego projektu. Nabyłem umiejętności bardziej wnikliwego czytania dokumentacji, zwłaszcza dla warstwy z Tapestry nie jest ona zbyt obszerna. Przed
realizacją tego projektu nie miałem do czynienia z tym frameworkiem od Apache. Z tego powodu zdecydowaną większość czasu poświęciłem na implementacje warstwy aplikacji przeglądarkowej, gdyż musiałem opanowywać niezbędne umiejętności od zera.

Życzę miłego testowania. W przypadku napotkania problemów proszę o kontakt na mój nr. telefonu.






















