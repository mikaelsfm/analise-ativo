package com.analiseativo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String ativo;
        double precoVenda;
        double precoCompra;

        if (args.length != 3) {
            System.out.println("Qual a ação desejada? ");
            ativo = input.nextLine();
            System.out.println("Ação escolhida: " + ativo);

            System.out.println("Qual o valor de venda desejado? ");
            precoVenda = Double.parseDouble(input.nextLine());
            System.out.println("Valor de venda escolhido: " + precoVenda);

            System.out.println("Qual o valor de compra desejado? ");
            precoCompra = Double.parseDouble(input.nextLine());
            System.out.println("Valor de compra escolhido: " + precoCompra);
            
            input.close();
        } else {
            ativo = args[0];
            precoVenda = Double.parseDouble(args[1]);
            precoCompra = Double.parseDouble(args[2]);
        }

        MonitoraAcao monitor = new MonitoraAcao(ativo, precoVenda, precoCompra);
        monitor.start();
    }
}