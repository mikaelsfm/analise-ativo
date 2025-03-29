package com.analiseativo;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Uso: java -jar analise-ativo.jar <ATIVO> <PREÇO_VENDA> <PREÇO_COMPRA>");
            return;
        }

        String ativo = args[0];
        double precoVenda = Double.parseDouble(args[1]);
        double precoCompra = Double.parseDouble(args[2]);

        MonitoraAcao monitor = new MonitoraAcao(ativo, precoVenda, precoCompra);
        monitor.start();
    }
}