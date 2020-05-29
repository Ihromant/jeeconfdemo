package ua.ihromant.learning;

import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.agent.QLearningTemplate;
import ua.ihromant.learning.factory.Factory;
import ua.ihromant.learning.factory.NimFactory;
import ua.ihromant.learning.state.NimState;

import java.util.Scanner;

public class Main {
    private static final Factory factory = new NimFactory();
    private static final NimState NIM_GAME = new NimState(new int[] {1, 3, 5, 7});
    public static void main(String[] args) {
        Agent ai = new QLearningTemplate(factory.createQTable());
        ai.train(NIM_GAME, 100000);
        new GameBoard(ai, factory.player(new Scanner(System.in)), NIM_GAME).play();
    }
}
