package com.project.lacuccina;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Classe responsável pela chamada do WebService geral:
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class WebService extends AppCompatActivity {

    //Função para requisição GET
    public static String buscaWS(String cEndPoint) {
        String cRetorno = "ERRO: Não reconhecido";
        BufferedReader bufferedReader = null;
        // Efetuo requisição:
        URL urlServidor;

        try {
            urlServidor = new URL(cEndPoint);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlServidor.openConnection();
            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String linha;

            while((linha = bufferedReader.readLine()) != null){
                stringBuilder.append(linha+"\n");
            }
            cRetorno = stringBuilder.toString();

        } catch (final MalformedURLException e) {
            cRetorno = "ERRO_I1: " + e.getMessage();
        } catch (final IOException e) {
            cRetorno = "ERRO_I2: " + e.getMessage();
        }

        return cRetorno;
    }

    //Função para requisição POST
    public static String postWS(String cEndPoint, String cBody) {
        HttpURLConnection connection = null;

        String retorno = "";

        try {

            URL url = new URL(cEndPoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(cBody.toString());
            writer.flush();

            int code = connection.getResponseCode();
            if (code !=  200) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                retorno = line;
            }
            return retorno;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return retorno;
    }

    //Função para requisição DELETE
    public static String delWS(String cEndPoint, String cBody) {
        HttpURLConnection connection = null;

        String retorno = "";

        try {

            URL url = new URL(cEndPoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(cBody.toString());
            writer.flush();

            int code = connection.getResponseCode();
            if (code !=  200) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                retorno = line;
            }
            return retorno;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return retorno;
    }
}
