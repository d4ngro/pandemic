# Pandemic

Beispielimplementierung, die zeigt, wie Spring (Autowiring, Annotations), Spring Data (Object mapping) und ein übersichtliches Packaging eingesetzt werden können, damit der Code überschaubar bleibt.

## Applikation starten

Als Build-Tool ist Gradle im Einsatz. Finde ich schicker als Maven, ist aber Geschmacksache. Außerdem nutzt die Implementierung ein lokales DynamoDB, damit kein Deployment nötig ist.

1. Lokales DynamoDB starten: In den "dynamodb" Ordner wechseln und Shellskript "rundb.sh" ausführen mit "./rundb.sh".

2. IP Adresse im docker0 bridge network herausfinden mit "sudo ip addr show docker0".

3. IP Adresse in "src/main/resources/application.properties" bei "dynamodb.serviceEndpoint" mit eigenem Wert (aus 2.) ersetzen.

4. In zweitem Terminal eigenen build starten mit "./gradlew dockerize".

5. In den "docker" Ordner wechseln und mit "docker build -t pandemic ." image bauen.

6. Mit "docker run -ti -p 8080:8080 --name pandemic pandemic" einen Container starten. (Das "ti" kann durch "d" ersetzt werden, dann wird der Container im Hintergrund gestartet, und man muss nicht alles mitlesen...)

## Auf die Applikation zugreifen

Auf die HTTP Methode folgen die URL und der request-body in spitzen Klammern.

GET http://localhost:8080/api/game/ -> Liefert alle Spiele.
POST http://localhost:8080/api/player/NAME <> -> Liefert neuen Spieler mit Namen "NAME".
POST http://localhost:8080/api/game/ <Spieler JSON> -> Liefert neues Spiel mit dem im JSON request-body übergebenen Spieler.
POST http://localhost:8080/api/game/GAME_ID/join <Spieler JSON> -> Liefert Spiel mit ID "GAME_ID" und hinzugefügtem im JSON request-body übergebenen Spieler.

Die drei POST requests sind nicht idempotent, daher sind sie POST und nicht PUT.

## Umsetzung

Wie an den packages zu sehen, trennt die Implementierung Controller, Modell und Datenbankzugriff vernünftig voneinander.

Die Controller implementieren die Businesslogik. Außer dem Anlegen von Spielen und dem Hinzufügen von Spielern ist der Start des Spiels, wenn vier Spieler beteiligt sind, umgesetzt. Das sollte exemplarisch reichen.

Das Modell besteht aus zwei Klassen. Interessant ist nur die "Game" Klasse, da sie die einzige persistente ist und mit den entsprechenden Annotations versehen ist. Das Object Mapping passiert automatisch. Die Modellklassen nutzen zudem die "lombok" Bibliothek zur Vermeidung von Boilerplate Code.

Die Schnittstelle zur Datenbank sind Repository Interfaces, hier das "GameRepository". Es erweitert das "CrudRepository" Interface von Spring. Damit stehen z. B. die im Controller benutzten Methoden "save", "findAll" und "findOne" automatisch zur Verfügung.

Um DynamoDB als Datenbank nutzen zu können, muss eine "amazonDynamoDB" Bean als Bindeglied registriert werden. Dies geschieht in der "DynamoDBConfig" Klasse und ist auf die lokal laufende DynamoDB ausgelegt.

Auf die Nutzung von statischen Methoden wurde zugunsten der Testbarkeit verzichtet, zumal Spring durch Autowiring das nötige Mittel zur Vermeidung statischer Methoden direkt zur Verfügung stellt.