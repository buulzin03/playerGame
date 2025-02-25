package com.hipermidia.controller;

import com.google.gson.Gson;
import com.hipermidia.model.*;
import com.hipermidia.model.states.GameStates;
import com.hipermidia.view.GameView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GameController {
    private Game currentGame;
    private Location currentLocation;
    private final GameView view;
    private GameStates currentState = GameStates.INIT;


    public GameController(GameView view) {
        this.view = view;
    }

    public void start() {
        File folder = new File("src/main/java/com/hipermidia/gamefile");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (listOfFiles == null || listOfFiles.length == 0) {
            view.noGameFound();
            return;
        }

        File gameFile = view.chooseGame(listOfFiles);
        if (loadGame(gameFile)) {
            initializeGameState();
            gameLoop();
        }
    }

    private boolean loadGame(File gameFile) {
        try (FileReader reader = new FileReader(gameFile)) {
            Gson gson = new Gson();
            currentGame = gson.fromJson(reader, Game.class);
            currentLocation = findLocationById(currentGame.getStartLocationId());
            return true;
        } catch (IOException e) {
            view.showError("Erro ao carregar o jogo: " + e.getMessage());
            return false;
        }
    }

    private void initializeGameState() {
        view.showGameIntro(currentGame.getTitle(), currentGame.getDescription(), currentGame.getAuthor());
        view.showLocationInfo(currentLocation);
    }

    private Location findLocationById(Integer locationId) {
        System.out.println(currentGame.getStartLocationId());
        return currentGame.getLocations().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Location not found: " + locationId));
    }

    private void gameLoop() {
        boolean gameRunning = true;
        while (gameRunning) {
            String command = view.getPlayerCommand();

            if(command.equals("inventario")) {
                currentState = GameStates.SHOWINGINVENTORY;
                processCommand();
            } else if(command.equals("olhar")) {
                currentState = GameStates.SHOWINGLOCATION;
                processCommand();
            } else if(command.equals("help")) {
                currentState = GameStates.SHOWINGHELP;
                processCommand();
            } else if(command.equals("status")) {
                currentState = GameStates.SHOWINGSTATUS;
                processCommand();
            } else if(command.equals("turno")) {
                currentState = GameStates.SHOWINGTURN;
                processCommand();
            } else if(command.contains("usar") || command.contains("equipar")) {
                currentState = GameStates.USINGITEM;
                processCommand();
            }
            // Verificar condições de fim de jogo
            if(currentGame.getMaxTurnsEasy() == currentGame.getCurrentTurn()) {
                gameRunning = false;
            }
        }
    }

    private void processCommand() {
        // Implementar processamento de comandos
        switch (currentState) {
            case INIT:
                initializeGameState();
            case SHOWINGLOCATION:
                view.showLocationInfo(currentLocation);
                break;
            case SHOWINGINVENTORY:
                view.showInventory(currentGame.getPlayer().getInventory());
                break;
            case SHOWINGSTATUS:
                view.showPlayerStatus(currentGame.getPlayer());
                break;
            case SHOWINGTURN:
                view.showCurrentTurn(currentGame.getCurrentTurn());
                break;
            case SHOWINGHELP:
                view.help();
                break;
            case FIGHTING:
                
                currentGame.increaseTurn();
                break;
            case USINGITEM:
                view.showInventory(currentGame.getPlayer().getInventory());
                System.out.println("Qual item deseja usar ou equipar? ");
                String idItem = view.getPlayerCommand();
                currentGame.getPlayer().useItem(idItem);
                view.showPlayerStatus(currentGame.getPlayer());

            
        }
    }

    public void talkingNpc() {
        // Lógica para interação com NPCs
    }

    public void resolvingPuzzle() {
        // Lógica para resolução de puzzles
    }

    // public void fighting() {
    //     if(currentGame.getPlayer().getDefense() < currentLocation.getEnemies().)
    // }
}
