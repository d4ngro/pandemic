# Pandemic

Beispielimplementierung, die zeigt, wie Spring (Autowiring, Annotations), Spring Data (Object mapping), das MVC Architektur Pattern und ein übersichtliches Packaging sinnvoll eingesetzt werden können, damit der Code überschaubarer bleibt.

## Applikation starten

Als Build-Tool ist Gradle im Einsatz. Finde ich schicker als Maven, ist aber Geschmacksache. Außerdem nutzt die Implementierung ein lokales DynamoDB, damit kein Deployment nötig ist.

1. Lokales DynamoDB starten: In den "dynamodb" Ordner wechseln und Shellskript "rundb.sh" ausführen mit `./rundb.sh`.

2. IP Adresse im docker0 bridge network herausfinden mit `sudo ip addr show docker0`.

3. IP Adresse in "src/main/resources/application.properties" bei "dynamodb.serviceEndpoint" mit eigenem Wert (aus 2.) ersetzen.

4. In zweitem Terminal eigenen build starten mit `./gradlew dockerize`.

5. In den "docker" Ordner wechseln und mit `docker build -t pandemic .` image bauen.

6. Mit `docker run -ti -p 8080:8080 --name pandemic pandemic` einen Container starten. (Das "ti" kann durch "d" ersetzt werden, dann wird der Container im Hintergrund gestartet, wenn man sowieso nicht mitlesen mag...)

## Auf die Applikation zugreifen

In der folgenden Liste folgen auf die HTTP Methode (GET/POST) die URL und der request body (nur bei POST) in geschweiften Klammern.

1. GET http://localhost:8080/api/game/ -> Liefert alle Spiele.

2. POST http://localhost:8080/api/player/NAME {} -> Liefert neuen Spieler mit Namen "NAME".

3. POST http://localhost:8080/api/game/ {Spieler JSON} -> Liefert neues Spiel mit dem im JSON request-body übergebenen Spieler.

4. POST http://localhost:8080/api/game/GAME_ID/join {Spieler JSON} -> Liefert Spiel mit ID "GAME_ID" und hinzugefügtem im JSON request-body übergebenen Spieler.

Die drei POST requests sind nicht idempotent, daher sind sie POST und nicht PUT.

## Umsetzung

Wie an den Packages zu sehen, trennt die Implementierung Controller und Model vernünftig voneinander (MVC Architektur Pattern). Die View ist ja extern umgesetzt.

Die Controller bilden die Schnittstelle zwischen View und Model, nehmen also die API Aufrufe entgegen, geben sie an das Model weiter und leiten wiederum Model-Änderungen an die View. Ist ja bekannt.

Das Modell besteht aus Service Interfaces und deren Implementierungen, sowie Datenbank Repository Interfaces und den Entity Klassen.

Die Entity Klassen liegen im "model" Package. Sie nutzen die "lombok" Bibliothek zur Vermeidung von Boilerplate Code. Die "Game" Klasse enthält zudem die zur Persistierung nötigen Informationen als Annotationen. Das Object/JSON Mapping erfolgt damit automatisch.

Die Repository Interfaces bilden in Spring die Schnittstelle zur Datenbank, uns reicht ein "GameRepository". Es erweitert das "CrudRepository" Interface von Spring. Damit stehen z. B. die in der "GameServiceImpl" benutzten Methoden "save", "findAll" und "findOne" automatisch zur Verfügung. Dem Interface können aber auch eigene Zugriffsmethoden hinzugefügt werden.

Die Business Logic ist in den Services implementiert. Außer dem Anlegen von Spielen und Spielern und dem Hinzufügen von Spielern zu Spielen sind der Start des Spiels, wenn vier Spieler erreicht sind und das Holen aller Spiele aus der DB umgesetzt. Das soll mal exemplarisch reichen.

Um DynamoDB als Datenbank nutzen zu können, muss eine "amazonDynamoDB" Bean als Bindeglied registriert werden. Dies geschieht in der "DynamoDBConfig" Klasse und ist auf die lokal laufende DynamoDB ausgelegt. Dort wird auch der "pandemic" Table angelegt, falls er noch nicht existiert.

Auf die Nutzung von statischen Methoden wurde zugunsten der Testbarkeit verzichtet, zumal Spring durch implizites Autowiring das nötige Mittel zur Vermeidung statischer Methoden direkt zur Verfügung stellt.

## Nachwort

Der Grund dafür, dass ich mir die Zeit genommen habe, diese alternative Grundstockimplementierung "noch einmal schnell" aufzusetzen, ist, dass ich die aktuelle Implementierung wegen etwas wildwüchsiger Umsetzung recht schwer verständlich fand.

Sicher führen in der Softwareentwicklung viele Wege nach Rom und immer wieder ist die Umsetzung von Projekten vom persönlichen Geschmack der Entwickler geprägt, zudem möchte ich auch nicht oberlehrerhaft daher kommen, aber ich denke, der vorliegende Ansatz wird in größeren Spring Projekten so oder sehr ähnlich angewendet und gibt die Möglichkeit, den Code sofort zu verstehen und sich schnell einzuarbeiten.

Daher hoffe ich, er ist für euch eine Anregung für kommende Aufgaben.

In diesem Sinne Happy Coding!