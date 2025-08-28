package br.edu.ifpb.padroes.atv3.cardapio.composite;

import br.edu.ifpb.padroes.atv3.cardapio.composite.interfaces.MenuComponent;

import java.util.*;

public class Combo implements MenuComponent {

    private final String id;
    private final String name;
    private final String description;
    private final List<MenuComponent> children = new ArrayList<>();

    public Combo(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name);
        this.description = description;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public double getPrice() {
        // Soma recursiva dos filhos
        return children.stream().mapToDouble(MenuComponent::getPrice).sum();
    }

    @Override
    public void print(int indent) {
        String pad = " ".repeat(Math.max(0, indent));
        System.out.printf("%s+ %s : %s (%.2f)%n", pad, name, description == null ? "" : description, getPrice());
        // Imprime filhos com mais indentação
        children.forEach(c -> c.print(indent + 4));
    }

    @Override
    public void add(MenuComponent component) {
        children.add(Objects.requireNonNull(component));
    }

    @Override
    public Optional<MenuComponent> findById(String id) {
        if (this.id.equals(id)) return Optional.of(this);
        for (MenuComponent c : children) {
            Optional<MenuComponent> found = c.findById(id);
            if (found.isPresent()) return found;
        }
        return Optional.empty();
    }

    public List<MenuComponent> getChildren() {
        return List.copyOf(children);
    }
}
