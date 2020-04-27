# ZTW_AB_ME_Portal
projekt frontendu: https://github.com/efodiks/ZTW_AB_ME_Portal-frontend
aplikacja dostępna: http://64.225.92.27/

## Ostatnie zmiany:
Rozdzielenie backendu i frontendu na dwa repozytoria.
Implementacja kontrolera “UsersController” mającego, na razie, jedną metodę zwracającą posty użytkownika wraz z testami pokrywającymi wszystkie możliwe drogi wykonania.
Dodano możliwość wyświetlenia strony użytkownika. Nie jest to jeszcze możliwe z poziomu listy postów, jednak przeglądać stronę wybranego użytkownika można zobaczyć po wprowadzeniu adresu: http://64.225.92.27/user/1, gdzie 1 to id użytkownika. Użytkownik portalu musi być zalogowany, aby przeglądać strony innych użytkowników.

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

# Docker instructions

### locally
docker build -t efodikss/portal-backend:latest .
docker push efodikss/portal-backend:latest


### AWS
docker pull efodikss/portal-backend
docker run --name backend -d -p 8080:8080 -e "SECURITY_SECRET=nkHdAWNrPz6iS4skz79mnwbLaxaeW9aXrwi7ijKi/GoONIk45VjnwLFn3/voMIhYvO/nBjdnUHEs/x66Pt0/BQ==" efodikss/portal-backend

## Watchtower
docker run -d \
    --name watchtower \
    -v /var/run/docker.sock:/var/run/docker.sock \
    containrrr/watchtower \
    --cleanup \
    --interval 30

TODO: doesn't build
ENV JAVA_OPTS = ""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
