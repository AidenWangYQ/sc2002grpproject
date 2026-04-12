package com.sc2002.arena;

import com.sc2002.arena.controller.GameController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Game starting...");
        GameController gameController = new GameController();
        gameController.run();
    }
}
