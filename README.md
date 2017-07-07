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

To try it out you can use curl to call the api.

Start by naming a player and submit his/hers bet.

```
$ curl -H "Content-Type: application/json" \
  -X POST -d '{"player":"Ada","bet":"ROCK"}' \
  http://localhost:8081/rps/api/game
> {"message":"Game started"}
```

And follow up with another player and bet.

```
$ curl -H "Content-Type: application/json" \
  -X POST -d '{"player":"Bertha","bet":"PAPER"}' \
  http://localhost:8081/rps/api/game
> {"message":"Winner is Bertha"}
```

Congratulations Bertha!

After a winner is named, any following request will begin a new game. In this way it is possible to keep playing 
forever! 

### GUI - or lack thereof

Currently there is no GUI, but I aim to add one later in the form of a simple web page.

### TODO:

- [ ] Allow multiple game to go on at once
- [ ] Add simple web page GUI
- [ ] Make the whole thing more RESTful