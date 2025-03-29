package com.analiseativo;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MonitoraAcao {
    private final String ativo;
    private final double precoVenda;
    private final double precoCompra;

    public MonitoraAcao(String ativo, double precoVenda, double precoCompra) {
        this.ativo = ativo;
        this.precoVenda = precoVenda;
        this.precoCompra = precoCompra;
    }

    public void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    double cotacaoAtual = getCotacao(ativo);
                    System.out.println("Cotação atual de " + ativo + ": " + cotacaoAtual);

                    if (cotacaoAtual >= precoVenda) {
                         System.out.println("O preço de " + ativo + " subiu para " + cotacaoAtual);
                    } else if (cotacaoAtual <= precoCompra) {
                         System.out.println("O preço de " + ativo + " caiu para " + cotacaoAtual);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao obter cotação: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }, 0, 60000);
    }

    private double getCotacao(String ativo) {
        try {
            Stock stock = YahooFinance.get(ativo + ".SA");
            return stock.getQuote().getPrice().doubleValue();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}