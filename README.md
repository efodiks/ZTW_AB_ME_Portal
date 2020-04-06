# ZTW_AB_ME_Portal

## Ostatnie zmiany:
- rozwijaniem modelu domenowego i bazy danych - stworzenie modelu i tabel dla postów,
- pracą nad niedokończonymi funkcjonalnościami - logowaniem (które zostało skończone), rejestracją (połączony front i back end), wylogowywanie (stworzone).

## Uruchamianie aplikacji frontendowej
Wymagane jest zainstalowanie Node.js

### Uruchomienie
Należy pobrać i rozpakować projekt, w konsoli przejść do folderu "portal" (ZTW_AB_ME_Portal-master/front/portal).

Przed pierwszym uruchomieniem należy wprowadzić w konsoli polecenie "npm install", a następnie uruchomić przy pomocy komendy "npm start".

## Uruchamianie aplikacji backendowej
Wymagane jest zainstalowanie jdk 11. Należy pobrać i rozpakować projekt, w konsoli przejść do folderu "portal" (ZTW_AB_ME_Portal-master/back/portal). I wykonać polecenia:
### Unix:
./mvnw clean install
./mvnw spring-boot:run
### Windows:
./mvnw.cmd clean install
./mvnw.cmd spring-boot:run

## Informacja o plikach
### Folder "front/portal/src/config"
- zawiera konfigurację klienta http
### Folder "front/portal/src/hooks"
- zawiera "react hooks" - fragmenty logiki biznesowej używające innych hookow, używane w wielu komponentach
### Folder "front/portal/src/components"
- folder "layout" zawiera komponenty często używane w widokach
- folder "dashboard" zawiera komponenty do strony dashboardu
- folder "authorization" zawiera komponenty do strony logowania i rejestracji
- folder "post-view" zawiera komponenty do postów (jeszcze nieużywane na stronie)
