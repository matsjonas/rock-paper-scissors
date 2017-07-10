$(document).ready(function () {

    var GAME_NAME = randomId();

    newGame();

    $(".gameChoices img").on("click", function () {
        var that = $(this);
        var gameBox = that.closest(".gameBox");

        var playerName = gameBox.find("input").val();
        var bet = that.data("value");

        $.ajax({
            url: "http://localhost:8081/rps/api/games/" + GAME_NAME + "/bets",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({playerName: playerName, bet: bet}),
            success: function(data) {
                console.log("Bet placed: " + playerName + " " + bet, data);
                if (data.status == "WIN" || data.status == "DRAW") {
                    endGame(data.status, data.winner);
                }
            }
        });

        var gameBoxCover = gameBox.find(".gameBoxCover");
        gameBoxCover.css({
            position: "absolute",
            width: "100%",
            height: "100%",
            left: 0,
            top: 0
        });

    });

    function newGame() {
        $.ajax({
            url: "http://localhost:8081/rps/api/games",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({id: GAME_NAME}),
            success: function() {
                console.log("New game created: " + GAME_NAME);
            }
        });
    }

    function endGame(status, winner) {
        var popupMessage = $("#popup .message");
        if (status == "WIN") {
            popupMessage.html("Congratulations " + winner.playerName + " (" + winner.bet + ")");
        } else if (status == "DRAW") {
            popupMessage.html("It was a draw!");
        }
        $("#popup").removeClass("hidden");
    }

    function randomId() {
        var text = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (var i = 0; i < 9; i++)
            text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text;
    }

});