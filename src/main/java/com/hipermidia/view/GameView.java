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
        System.out.println("\n" + "★".repeat(60));
        System.out.println(title.toUpperCase());
        System.out.println("por " + author);
        System.out.println("★".repeat(60));
        System.out.println("\n" + description + "\n");
        System.out.println("★".repeat(60) + "\n");
    }

    public void showLocationInfo(Location location) {
        System.out.println("\n📍 " + location.getName().toUpperCase());
        System.out.println("▔".repeat(location.getName().length() + 3));
        System.out.println(location.getDescription() + "\n");
        
        System.out.println("Neste local você encontra:");
        if (!location.getEnemies().isEmpty()) 
            System.out.println("👾 " + location.getEnemies().size() + " inimigo(s)");
        if (!location.getItems().isEmpty()) 
            System.out.println("🎁 " + location.getItems().size() + " item(ns)");
        if (!location.getPuzzles().isEmpty()) 
            System.out.println("🧩 " + location.getPuzzles().size() + " puzzle(s)");
        if (!location.getNpcs().isEmpty()) 
            System.out.println("👤 " + location.getNpcs().size() + " NPC(s)");
        if (!location.getExits().isEmpty()) 
            System.out.println("🚪 " + location.getExits().size() + " saída(s)");
        System.out.println();
    }

    public String showItemsAtLocation(Location location) {
        System.out.println("\n🎁 ITENS DISPONÍVEIS");
        System.out.println("▔".repeat(17));
        location.getItems().forEach(item -> 
            System.out.printf("📦 %s - %s (ID: %s)%n", 
                item.getName(), item.getDescription(), item.getId())
        );
        System.out.println("\nQual item deseja pegar? Informe o ID: ");
        return getPlayerCommand();
    }

    public void showInventory(List<Item> inventory) {
        System.out.println("\n🎒 INVENTÁRIO");
        System.out.println("▔".repeat(12));
        if (inventory.isEmpty()) {
            System.out.println("Inventário vazio");
        } else {
            inventory.forEach(item -> {
                String status = item.isInactive() ? "❌" : "✅";
                System.out.printf("%s %s (ID: %s)%n", status, item.getName(), item.getId());
            });
        }
        System.out.println();
    }

    public void showPlayerStatus(Player player) {
        System.out.println("\n📊 STATUS DO JOGADOR");
        System.out.println("▔".repeat(18));
        System.out.printf("❤️  Vida: %.1f%n", player.getLife());
        System.out.printf("⚔️  Ataque: %.1f%n", player.getAttack());
        System.out.printf("🛡️  Defesa: %.1f%n", player.getDefense());
        System.out.println();
    }

    public String getPlayerCommand() {
        System.out.print("\n► ");
        return scanner.nextLine().trim();
    }

    public void showError(String message) {
        System.out.println("\n❌ Erro: " + message);
    }

    public void showCurrentTurn(Integer currentTurn) {
        System.out.println("\n🕒 Turno atual: " + currentTurn);
    }

    public void help() {
        System.out.println("\n❔ COMANDOS DISPONÍVEIS");
        System.out.println("▔".repeat(20));
        System.out.println("👀 'olhar'     - Ver detalhes do local");
        System.out.println("📊 'status'    - Ver status do jogador");
        System.out.println("🎒 'inventario' - Ver itens no inventário");
        System.out.println("🕒 'turno'     - Ver turno atual");
        System.out.println("🚶 'ir para'   - Mover para outra sala");
        System.out.println("⚔️  'lutar'     - Iniciar combate");
        System.out.println("🎁 'pegar item' - Coletar item");
        System.out.println("🔧 'usar'      - Usar/equipar item\n");
        System.out.println("  'falar' - Falar com um npc");
    }
}