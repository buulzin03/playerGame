package com.hipermidia.view;

import com.hipermidia.model.Location;
import com.hipermidia.model.Player;
import com.hipermidia.model.Item;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class GameView {
    private final Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    public void noGameFound() {
        System.out.println("Nenhum arquivo de jogo encontrado.");
    }

    public File chooseGame(File[] listOfFiles) {
        System.out.println("Lista de jogos:");
        for (int i = 0; i < listOfFiles.length; i++) {
            System.out.println((i + 1) + ": " + listOfFiles[i].getName());
        }

        System.out.println("Escolha um jogo:");

        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice > 0 && choice <= listOfFiles.length) {
                return listOfFiles[choice - 1];
            } else {
                System.out.println("Escolha inválida, tente novamente.");
            }
        }
    }

    public void showGameIntro(String title, String description, String author) {
        System.out.println("=".repeat(50));
        System.out.println(title + " por " + author);
        System.out.println("=".repeat(50));
        System.out.println(description);
        System.out.println("=".repeat(50));
    }

    public void showLocationInfo(Location location) {
        System.out.println("\n" + location.getName());
        System.out.println(location.getDescription());
        System.out.println("Existem " + location.getEnemies().size() + " inimigo(s) nesta localização.");
        System.out.println();
        // Mostrar saídas disponíveis, itens visíveis, NPCs presentes, etc.
    }

    public void showInventory(List<Item> inventory) {
        System.out.println("\nInventário:");
        if (inventory.isEmpty()) {
            System.out.println("Vazio");
        } else {
            inventory.forEach(item -> System.out.println("- " + item.getName()));
        }
    }

    public void showPlayerStatus(Player player) {
        System.out.println("\nStatus do Jogador:");
        System.out.println("Vida: " + player.getLife());
        System.out.println("Ataque: " + player.getAttack());
        System.out.println("Defesa: " + player.getDefense());
    }

    public String getPlayerCommand() {
        System.out.print("\n> ");
        return scanner.nextLine().trim();
    }

    public void showError(String message) {
        System.out.println("Erro: " + message);
    }

    public void showCurrentTurn(Integer currentTurn){
        System.out.printf("O jogo esta no turno %d", currentTurn);
    }

    public void help() {
        System.out.println("\n Comandos disponiveis:");
        System.out.println("'olhar' = mostra detalhes da localização atual");
        System.out.println("'status' = mostra os status do jogador");
        System.out.println("'inventario' = mostra os itens disponiveis no inventario do jogador");
        System.out.println("'turno' = mostra o turno atual");
    }

}