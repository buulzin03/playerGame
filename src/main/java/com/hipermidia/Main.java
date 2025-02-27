package com.hipermidia;

import com.hipermidia.controller.GameController;
import com.hipermidia.view.GameView;

public class Main {
    public static void main(String[] args) {

        GameView view = new GameView();

        GameController controller = new GameController(view);

        controller.start();
    }
}