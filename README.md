# Rock paper scissors API game

Simple application that allows the user to play rock-paper-scissors via a simple API.

## About

I took the opportunity to use Gradle as the build system, though I had never set up a new project with it before. It was
 quite easy and an interesting learning opportunity.

### How to play

Start the application using gradle:
 
```
$ gradle jettyStart
```

Use curl to try out the API.

Start by listing all existing games (there should be none).

```
$ curl -X GET \
    http://localhost:8081/rps/api/games/
  
[]  
```

Create a new game and give a suiting id (name).

```
$ curl -H "Content-Type: application/json" \
   -X POST -d '{"id":"epicgame"}' \
   http://localhost:8081/rps/api/games

[{"id":"epicgame","bets":[],"status":"PENDING"}]
```

Follow up by providing the two players' bets.

```
$ curl -H "Content-Type: application/json" \
    -X POST -d '{"playerName":"Ada", "bet": "ROCK"}' \
    http://localhost:8081/rps/api/games/epicgame/bets

{"id":"epicgame","bets":[{"playerName":"Ada","bet":"ROCK"}],"status":"ONGOING"}

$ curl -H "Content-Type: application/json" \
    -X POST -d '{"playerName":"Bertha", "bet": "PAPER"}' \
    http://localhost:8081/rps/api/games/epicgame/bets

{
  "id":"epicgame",
  "bets":[{"playerName":"Ada","bet":"ROCK"},{"playerName":"Bertha","bet":"PAPER"}],
  "status":"WIN",
  "winner":{"playerName":"Bertha","bet":"PAPER"}
}
```

Congratulations Bertha!

It is also possible to delete a game, or to create multiple games and play for as much as you want.

### Tests

Tests are executed using gradle:

```
$ gradle test               // unit tests
$ gradle integrationTest    // integration tests
$ gradle check              // all tests
```

### GUI - or lack thereof

Currently there is no GUI, but I aim to add one later in the form of a simple web page.

### TODO:

- [x] Allow multiple game to go on at once
- [ ] Add simple web page GUI
- [ ] Add limitations for game ids and player names.
