package com.hipermidia.controller;

import com.google.gson.Gson;
import com.hipermidia.model.*;
import com.hipermidia.model.states.GameStates;
import com.hipermidia.view.GameView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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
                update();
            } else if(command.equals("olhar")) {
                currentState = GameStates.SHOWINGLOCATION;
                update();
            } else if(command.equals("help")) {
                currentState = GameStates.SHOWINGHELP;
                update();
            } else if(command.equals("status")) {
                currentState = GameStates.SHOWINGSTATUS;
                update();
            } else if(command.equals("turno")) {
                currentState = GameStates.SHOWINGTURN;
                update();
            } else if(command.contains("usar") || command.contains("equipar")) {
                currentState = GameStates.USINGITEM;
                update();
            } else if(command.contains("lutar") || command.contains("atacar")) {
                currentState = GameStates.FIGHTING;
                update();
            } else if(command.contains("pegar item")){
                currentState = GameStates.GETTINGITEM;
                update();
            } else if(command.contains("ir para") || command.contains("mover")) {
                currentState = GameStates.LEAVINGROOM;
                update();
            } else if(command.contains("puzzle")) {
                currentState = GameStates.RESOLVINGPUZZLE;
                update();
            } else if(command.contains("falar") || command.contains("npc")) {
                currentState = GameStates.TALKINGNPC;
                update();
            }
            if(currentGame.getMaxTurnsEasy() == currentGame.getCurrentTurn()) {
                gameRunning = false;
            }
        }
    }

    private void update() {
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
                System.out.println("Informe com qual inimigo deseja iniciar um combate! id: ");
                currentLocation.getEnemies().stream().forEach(System.out::println);
                String idEnemy = view.getPlayerCommand();
                fighting(idEnemy);
                currentGame.increaseTurn();
                break;

            case RESOLVINGPUZZLE:
                System.out.println("Informe qual puzzle deseja resolver! id: ");
                currentLocation.getPuzzles().stream().forEach(System.out::println);
                String idPuzzle = view.getPlayerCommand();
                resolvingPuzzle(idPuzzle);
                currentGame.increaseTurn();
                break;

            case TALKINGNPC:
                System.out.println("Informe com qual NPC desejas interagir! id: ");
                currentLocation.getNpcs().stream().forEach(System.out::println);
                String idNpc = view.getPlayerCommand();
                talkingNpc(idNpc);
                currentGame.increaseTurn();
                break;

            case LEAVINGROOM:
                System.out.println("\n🚪 SAÍDAS DISPONÍVEIS");
                System.out.println("▔".repeat(18));
                currentLocation.getExits().forEach(exit -> {
                    Location targetLocation = findLocationById(Integer.parseInt(exit.getTargetLocationId()));
                    String status = exit.isInactive() ? "❌ BLOQUEADA" : "✅ DISPONÍVEL";
                    System.out.printf("📍 %s (ID: %s) - %s%n", targetLocation.getName(), exit.getTargetLocationId(), status);
                    System.out.printf("   Direção: %s%n", exit.getDirection());
                    System.out.printf("   %s%n", exit.getDescription());
                    if (exit.isInactive()) {
                        System.out.println("   ⚠️  Esta saída está bloqueada e precisa ser desbloqueada");
                    }
                    System.out.println();
                });
                System.out.println("Para qual sala você deseja ir? Informe o ID: ");
                String destinationId = view.getPlayerCommand();
                moveToLocation(destinationId);
                currentGame.increaseTurn();
                break;

            case GETTINGITEM:
                String idItem = view.showItemsAtLocation(currentLocation);

                getItemsAtLocation(idItem);
                break;

            case USINGITEM:
                view.showInventory(currentGame.getPlayer().getInventory());
                System.out.println("Qual item deseja usar ou equipar? Informe o id: ");
                String itemId = view.getPlayerCommand();
                currentGame.getPlayer().useItem(itemId);
                view.showPlayerStatus(currentGame.getPlayer());

            case DEFEATED:
                break;
                
            case ENDING:
                break;

        }
    }

    public void getItemsAtLocation(String idItem) {
        try{
            Item currentItem = currentLocation.getItems().stream().filter(item -> item.getId().equals(idItem)).findFirst().get();
            currentGame.getPlayer().addItem(currentItem);
            System.out.println("Voce pegou o item " + currentItem.getName());
            currentLocation.getItems().remove(currentItem);
            currentState = GameStates.SHOWINGLOCATION;
            update();
        } catch (NumberFormatException e) {
            view.showError("Por favor, informe um ID válido.");
        } catch (RuntimeException e) {
            view.showError(e.getMessage());
        }
    }

    public void talkingNpc(String idNpc) {
        try {
            if(!currentLocation.getNpcs().isEmpty()){
                Npc npc = currentLocation.getNpcs().stream().filter(np -> np.getId().equals(idNpc)).findFirst().get();



                System.out.println("\n👤 " + npc.getName().toUpperCase());
                System.out.println(npc.getName());
                
                for(Dialogue dialogue : npc.getDialogues()){

                    System.out.println(dialogue.getText());

                    String action = view.getPlayerCommand();
                    // Comparar com a resposta alvo
                    boolean hasAction = dialogue.getResponses().stream().anyMatch(response -> response.getResult().getActive().contains(action));
                            
                    if(hasAction){
                        Result result = dialogue.getResponses().stream().filter(response -> response.getResult().getActive().contains(action)).findFirst().get().getResult();
                        System.out.println("\n💎 Itens dropados pelo npc:");
                        List<Item> loseItem = result.getLoseItem();
                        loseItem.stream().forEach(System.out::println);
                        System.out.println("Voce deseja pegar os itens dropados? s/n");
                        String response = view.getPlayerCommand();
                        if(response.toLowerCase().contains("s")) {
                            loseItem.stream().forEach(item -> currentGame.getPlayer().addItem(item));
                            currentLocation.getNpcs().remove(npc);
                            currentState = GameStates.SHOWINGLOCATION;
                            update();
                        } else if(response.toLowerCase().contains("n")){
                            currentLocation.getNpcs().remove(npc);
                            currentState = GameStates.SHOWINGLOCATION;
                            update();
                        }
                    }else {
                        System.out.println("Voce errou na resposta");
                        currentState = GameStates.SHOWINGLOCATION;
                        update();
                    }
                }
            }
        } catch (NumberFormatException e) {
            view.showError("Por favor, informe um ID válido.");
        } catch (RuntimeException e) {
            view.showError(e.getMessage());
        }

    }

    public void resolvingPuzzle(String idPuzzle){
        try {
            if (!currentLocation.getPuzzles().isEmpty()) {
                Puzzle currentPuzzle = currentLocation.getPuzzles().stream().filter(puzzle -> puzzle.getId().equals(idPuzzle)).findFirst().get();

                boolean hasAllItems = true;
                for (String requiredItem : currentPuzzle.getSolution().getRequiredItems()) {
                    boolean hasItem = currentGame.getPlayer().getInventory().stream()
                            .anyMatch(item -> item.getName().equals(requiredItem));
                    if (!hasItem) {
                        hasAllItems = false;
                    }
                }

                System.out.println("O que você deseja fazer?");


                String action = view.getPlayerCommand();

                if (hasAllItems && action.toLowerCase().equals(currentPuzzle.getSolution().getActions().toLowerCase())) {
                    System.out.println("\n🎉 Você resolveu o puzzle!");

                    for (String activeElement : currentPuzzle.getResult().getActive()) {
                        System.out.println(activeElement + " foi ativado!");
                    }

                    System.out.println("\n💎 Este puzzle dropou os seguintes itens:");
                    List<Item> loseItem = currentPuzzle.getResult().getLoseItem();
                    loseItem.stream().forEach(System.out::println);
                    System.out.println("Voce deseja pegar os itens dropados? s/n");
                    String response = view.getPlayerCommand();
                    
                    if(response.toLowerCase().contains("s")) {
                        loseItem.stream().forEach(item -> currentGame.getPlayer().addItem(item));
                        currentPuzzle.getResult().getLoseItem().removeAll(currentPuzzle.getResult().getLoseItem());
                        currentState = GameStates.SHOWINGLOCATION;
                        update();

                    } else if(response.toLowerCase().contains("n")){
                        currentState = GameStates.SHOWINGLOCATION;
                        update();
                    }
                    currentLocation.getPuzzles().remove(currentPuzzle);
                } else {
                    if (!hasAllItems) {
                        System.out.println("\n❌ Você não tem todos os itens necessários.");
                    } else {
                        System.out.println("\n❌ Essa ação não resolve o puzzle.");
                    }

                    Double currentLife = currentGame.getPlayer().getLife();
                    currentGame.getPlayer().setLife(currentLife - currentPuzzle.getResult().getLoseLife());
                    System.out.println("Você perdeu " + currentPuzzle.getResult().getLoseLife() + " pontos de vida!");

                    if(currentGame.getPlayer().getLife() <= 0) {
                        System.out.println("\n💀 Você foi derrotado! Game Over...");
                        currentState = GameStates.DEFEATED;
                        update();
                    }
                }
                currentGame.increaseTurn();
            } else {
                System.out.println("Não há puzzles para resolver nesta localização.");
            }
        } catch (NumberFormatException e) {
            view.showError("Por favor, informe um ID válido.");
        } catch (RuntimeException e) {
            view.showError(e.getMessage());
        }      
    }

    public void fighting(String idEnemy) {
        try {
            Player player = currentGame.getPlayer();
            Enemy enemy = currentLocation.getEnemies().stream().filter(en -> en.getId().equals(idEnemy)).findFirst().get();
            if(player.getDefense() > enemy.getAttack()) {
                System.out.println("\n🛡️ Você defendeu o ataque inimigo!");
            } else {
                currentGame.getPlayer().setLife(player.getLife() - enemy.getAttack());
                if(currentGame.getPlayer().getLife() <= 0) {
                    System.out.println("\n💀 Você foi derrotado! Game Over...");
                    currentState = GameStates.DEFEATED;
                    update();
                } else if(player.getAttack() > enemy.getDefense()) {
                    System.out.println("\n🎉 Parabéns! Você derrotou o inimigo!");
                    System.out.println("\n💎 Itens dropados pelo inimigo:");
                    List<Item> loseItem = enemy.getResult().getLoseItem();
                    loseItem.stream().forEach(System.out::println);
                    System.out.println("Voce deseja pegar os itens dropados? s/n");
                    String response = view.getPlayerCommand();
                    if(response.toLowerCase().contains("s")) {
                        loseItem.stream().forEach(item -> currentGame.getPlayer().addItem(item));
                        currentLocation.getEnemies().remove(enemy);
                        currentState = GameStates.SHOWINGLOCATION;
                        update();
                    } else if(response.toLowerCase().contains("n")){
                        currentLocation.getEnemies().remove(enemy);
                        currentState = GameStates.SHOWINGLOCATION;
                        update();
                    }
                } else if(player.getAttack() < enemy.getDefense()){
                    System.out.println("Voce foi derrotado pelo inimigo, volte quando estiver mais forte\n\n");
                    currentState = GameStates.SHOWINGLOCATION;
                    update();
                }
            }
        } catch (NumberFormatException e) {
            view.showError("Por favor, informe um ID válido.");
        } catch (RuntimeException e) {
            view.showError(e.getMessage());
        }
    }

    private void moveToLocation(String destinationId) {
        try {
            Integer locationId = Integer.parseInt(destinationId);
            boolean isConnected = currentLocation.getExits().stream()
                .filter(exit -> !exit.isInactive())
                .anyMatch(exit -> exit.getTargetLocationId().equals(destinationId));
            
            if (isConnected) {
                Location newLocation = findLocationById(locationId);
                currentLocation = newLocation;
                currentState = GameStates.SHOWINGLOCATION;
                view.showLocationInfo(currentLocation);
            } else {
                view.showError("Você não pode ir para esta localização. Escolha uma saída válida.");
            }
        } catch (NumberFormatException e) {
            view.showError("Por favor, informe um ID válido.");
        } catch (RuntimeException e) {
            view.showError(e.getMessage());
        }
    }
}
