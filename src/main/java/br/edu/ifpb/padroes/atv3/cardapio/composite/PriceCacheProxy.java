package br.edu.ifpb.padroes.atv3.cardapio.composite;

import br.edu.ifpb.padroes.atv3.cardapio.composite.interfaces.MenuComponent;

import java.util.Objects;
import java.util.Optional;

public class PriceCacheProxy implements MenuComponent {

    private final String id;
    private final MenuComponent wrapped;
    private Double cachedPrice = null;
    private long lastComputedAt = 0L;

    public PriceCacheProxy(MenuComponent wrapped) {
        this.id = java.util.UUID.randomUUID().toString();
        this.wrapped = Objects.requireNonNull(wrapped);
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getName() { return wrapped.getName() + " (cached)"; }

    @Override
    public double getPrice() {
        if (cachedPrice == null) {
            cachedPrice = wrapped.getPrice();
            lastComputedAt = System.currentTimeMillis();
        }
        return cachedPrice;
    }

    /**
     * Força recálculo no próximo getPrice()
     */
    public void invalidate() {
        cachedPrice = null;
        lastComputedAt = 0L;
    }

    @Override
    public void print(int indent) {
        String pad = " ".repeat(Math.max(0, indent));
        System.out.printf("%s# %s : (price %.2f) [cachedAt=%d]%n",
                pad, getName(), getPrice(), lastComputedAt);
        wrapped.print(indent + 4);
    }

    @Override
    public void add(MenuComponent component) {
        wrapped.add(component);
        // se alterou estrutura, invalidar cache para refletir mudanças
        invalidate();
    }

    @Override
    public Optional<MenuComponent> findById(String id) {
        if (this.id.equals(id)) return Optional.of(this);
        // procurar dentro do wrapped
        return wrapped.findById(id);
    }
}
