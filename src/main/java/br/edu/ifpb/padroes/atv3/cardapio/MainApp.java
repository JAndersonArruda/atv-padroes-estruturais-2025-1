package br.edu.ifpb.padroes.atv3.cardapio;

import br.edu.ifpb.padroes.atv3.cardapio.facade.MenuFacade;

public class MainApp {

    public static void main(String[] args) {

        MenuFacade facade = new MenuFacade();

        // Adiciona itens individuais
        String id1 = facade.addItem("Feijoada", "Traditional Brazilian stew with beans and pork", 28.50);
        String id2 = facade.addItem("Caipirinha", "Cachaça, sugar and lime", 12.00);
        String id3 = facade.addItem("Pudim", "Caramel flan dessert", 8.00);
        String id4 = facade.addItem("Coxinha", "Fried dough with shredded chicken", 6.50);

        // Cria um combo e adiciona itens
        String combo1 = facade.createCombo("Combo Feijoada + Drink", "Feijoada with a drink");
        facade.addToCombo(combo1, id1);
        facade.addToCombo(combo1, id2);

        // Cria outro combo que contém o combo anterior + sobremesa
        String combo2 = facade.createCombo("Lunch Special", "Feijoada combo plus dessert");
        facade.addToCombo(combo2, combo1);
        facade.addToCombo(combo2, id3);

        // Imprime menu inicial
        System.out.println("Menu initial:");
        facade.printMenu();

        // Aplica desconto de 10% no combo1
        System.out.println("\nApplying 10% discount to combo: " + combo1);
        String decoratedComboId = facade.applyDiscount(combo1, 10.0);

        // Opcional: wrap with cache the top-level combo2 (para demonstrar Proxy)
        System.out.println("\nWrapping combo2 with price cache proxy");
        String proxyId = facade.wrapWithCache(combo2);

        // Imprime menu depois das alterações
        System.out.println("\nMenu after discount and cache:");
        facade.printMenu();

        // Calcula preços
        System.out.println("\nPrices:");
        System.out.printf("Feijoada price: %.2f%n", facade.calculatePrice(id1));
        System.out.printf("Combo1 discounted price (via decorator): %.2f%n", facade.calculatePrice(decoratedComboId));
        System.out.printf("Combo2 (cached wrapper) price: %.2f%n", facade.calculatePrice(proxyId));

        // Tocar caso de uso: se precisasse tocar/usar item, seria feito via Facade também
        // (não implementamos 'play' aqui porque é cardápio; mas poderia ser análogo ao exemplo de streaming)
    }
}
