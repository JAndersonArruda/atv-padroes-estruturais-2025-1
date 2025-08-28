package br.edu.ifpb.padroes.atv3.cardapio.composite.interfaces;

import java.util.Optional;

public interface MenuComponent {

    String getId();
    String getName();
    /**
     * Calcula o preço do componente considerando hierarquia ou decoradores.
     * @return price em double
     */
    double getPrice();

    /**
     * Imprime a descrição do componente com indentação.
     * @param indent nivel de indentação (número de espaços)
     */
    void print(int indent);

    /**
     * Alguns componentes (combos) permitem adicionar filhos.
     * Implementações folhas podem lançar UnsupportedOperationException.
     */
    default void add(MenuComponent component) {
        throw new UnsupportedOperationException("This component does not support add()");
    }

    /**
     * Buscar um componente por id dentro da hierarquia.
     * Retorna Optional.empty() se não encontrado.
     */
    Optional<MenuComponent> findById(String id);
}
