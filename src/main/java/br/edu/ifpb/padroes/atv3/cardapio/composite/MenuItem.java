package br.edu.ifpb.padroes.atv3.cardapio.composite;

import br.edu.ifpb.padroes.atv3.cardapio.composite.interfaces.MenuComponent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * MenuItem: representa um item individual (dish, drink, dessert).
 */
public class MenuItem implements MenuComponent {

    private final String id;
    private final String name;
    private final String description;
    private final double price;

    // main constructor
    public MenuItem(String name, String description, double price) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name);
        this.description = description;
        this.price = price;
    }

    // Getters
    @Override
    public String getId() { return id; }

    @Override
    public String getName() { return name; }

    public String getDescription() { return description; }

    @Override
    public double getPrice() { return price; }

    @Override
    public void print(int indent) {
        String pad = " ".repeat(Math.max(0, indent));
        System.out.printf("%s- %s : %s (%.2f)%n", pad, name, description == null ? "" : description, price);
    }

    @Override
    public Optional<MenuComponent> findById(String id) {
        if (this.id.equals(id)) return Optional.of(this);
        return Optional.empty();
    }
}
