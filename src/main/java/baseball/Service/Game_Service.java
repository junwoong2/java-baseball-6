package baseball.Service;

import baseball.Controller.User_Controller;
import baseball.Domain.Game_Domain;
import baseball.Model.User;
import baseball.Model.Computer;
import baseball.View.SystemMessage;
import baseball.View.RequestMessage;

import camp.nextstep.edu.missionutils.Console;

public class Game_Service {

    int size;
    Game_Domain game;
    User user = new User();
    User_Controller parser = new User_Controller();
    SystemMessage systemMessage = new SystemMessage();

    public void setGame(int size, int start, int end) {
        this.size = size;
        game = new Game_Domain(Computer.computerNum(size, start, end));
    }

    public void playGame() {
        int strike = 0;
        while (strike != 3) {
            play();
            systemMessage.printScoreMessage(game.getBallCount(), game.getStrikeCount());
            strike = game.getStrikeCount();
        }
    }

    private void play() {
        game.initBaseBall();
        user.setUserNumbers(getUserNumber());
        computeScore();
    }

    private int[] getUserNumber() throws IllegalArgumentException {
        RequestMessage.requestInputData();
        String input = Console.readLine();
        return parser.UserInput(input, size);
    }

    private void computeScore() {
        for (int i = 0; i < size; i++) {
            compute(game.getGameNumbers(), user.getUserNumbers(), i);
        }
    }

    private void compute(int[] gameNumber, int[] userNumber, int index) {
        int temp = -1;
        for (int i = 0; i < gameNumber.length; i++) {
            if (gameNumber[i] == userNumber[index]) {
                temp = i;
                break;
            }
        }
        incCount(index, temp);
    }

    private void incCount(int index, int temp) {
        if (temp != index && temp != -1) {
            game.incBallCount();
        }
        if (temp == index) {
            game.incStrikeCount();
        }
    }
}