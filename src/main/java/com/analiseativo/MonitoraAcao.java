package com.analiseativo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class MonitoraAcao {
    private final String ativo;
    private final double precoVenda;
    private final double precoCompra;
    private final String KEY;
    private final EmailService emailService;

    public MonitoraAcao(String ativo, double precoVenda, double precoCompra) {
        ConfigLoader config = new ConfigLoader();
        this.ativo = ativo;
        this.precoVenda = precoVenda;
        this.precoCompra = precoCompra;
        this.KEY = config.getProperty("api.key");
        this.emailService = new EmailService();
    }

    public void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    double cotacaoAtual = getCotacao(ativo);
                    System.out.println("Cotação atual de " + ativo + ": R$" + cotacaoAtual);

                    if (cotacaoAtual >= precoVenda) {
                        System.out.println("O preço para venda está mais alto!");
                        emailService.enviarAlerta("Alerta de Venda " + ativo, "O ativo " + ativo + " atingiu o preço de venda: R$" + cotacaoAtual);
                    } else if (cotacaoAtual <= precoCompra) {
                        System.out.println("O preço para compra está mais baixo!");
                        emailService.enviarAlerta("Alerta de Compra " + ativo, "O ativo " + ativo + " atingiu o preço de compra: R$" + cotacaoAtual);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao obter cotação: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }, 0, 20000);
    }

    private double getCotacao(String ativo) throws IOException {
        String url = "https://brapi.dev/api/quote/" + ativo + "?token="+KEY;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
    
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.lines().collect(Collectors.joining());
        reader.close();
    
        JSONObject json = new JSONObject(response);
        JSONObject stockData = json.getJSONArray("results").getJSONObject(0);
    
        return stockData.getDouble("regularMarketPrice");
    }

}