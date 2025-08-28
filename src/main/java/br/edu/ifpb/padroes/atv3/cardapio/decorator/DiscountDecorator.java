package br.edu.ifpb.padroes.atv3.cardapio.decorator;

import br.edu.ifpb.padroes.atv3.cardapio.composite.interfaces.MenuComponent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class DiscountDecorator implements MenuComponent {

    private final String id;
    private final MenuComponent wrapped;
    private final double discountPercent; // ex: 10.0 = 10%

    /**
     * @param wrapped componente que será decorado
     * @param discountPercent percentual de desconto (0..100)
     */
    public DiscountDecorator(MenuComponent wrapped, double discountPercent) {
        this.id = UUID.randomUUID().toString();
        this.wrapped = Objects.requireNonNull(wrapped);
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("discountPercent must be between 0 and 100");
        }
        this.discountPercent = discountPercent;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getName() { return wrapped.getName() + " (discount " + discountPercent + "%)"; }

    @Override
    public double getPrice() {
        double base = wrapped.getPrice();
        double discounted = base * (1.0 - discountPercent / 100.0);
        return Math.round(discounted * 100.0) / 100.0; // arredonda 2 casas
    }

    @Override
    public void print(int indent) {
        // imprime o wrapper e delega a impressão do componente original com indentação maior
        String pad = " ".repeat(Math.max(0, indent));
        System.out.printf("%s~ %s : (original price %.2f -> discounted price %.2f)%n",
                pad, getName(), wrapped.getPrice(), getPrice());
        wrapped.print(indent + 4);
    }

    @Override
    public void add(MenuComponent component) {
        // Delegar para wrapped se suportado
        try {
            wrapped.add(component);
        } catch (UnsupportedOperationException ex) {
            throw new UnsupportedOperationException("Wrapped component does not support add()");
        }
    }

    @Override
    public Optional<MenuComponent> findById(String id) {
        if (this.id.equals(id)) return Optional.of(this);
        // verificar também dentro do wrapped
        return wrapped.findById(id);
    }
}
