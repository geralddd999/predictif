/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import dao.RDVDao;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import metier.modele.ProfilAstral;
import metier.modele.RDV;

/**
 *
 * @author adupire
 */
public class RequeteAPI {
    public static ProfilAstral calculProfilAstral (String prenom, LocalDate date) {
        
        JsonObject result = null;
        ProfilAstral pa = new ProfilAstral();
        
        try {

            // TODO: adapter l'URL de l'API et la liste des paramètres
            URI requestUri = URI.create(
                    "https://servif.insa-lyon.fr/WebDataGenerator/Astro"
                    + "?service=profil"                           
                    + "&key=ASTRO-01-M0lGLURBU0ktQVNUUk8tQjAx"
                    + "&prenom="+ URLEncoder.encode(prenom, StandardCharsets.UTF_8)
                    + "&date-naissance="+ URLEncoder.encode(date.toString(), StandardCharsets.UTF_8)
                );

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(requestUri).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                String body = httpResponse.body();
                //System.out.println(body);

                result = Json.createReader(new StringReader(body)).readObject();
            }
            else {
                throw new IOException("HTTP Error Status Code "+httpResponse.statusCode());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            result = null;
        }
        pa.setSigneZodiaque(result.getJsonObject("profil").getString("signe-zodiaque"));
        pa.setAnimalTotem(result.getJsonObject("profil").getString("animal"));
        pa.setCouleurPorteBonheur(result.getJsonObject("profil").getString("couleur"));
        pa.setSigneAstroChinois(result.getJsonObject("profil").getString("signe-chinois"));

        return pa;
    }
    
    public static List<String> calculConseil(String couleur, String animal,Integer niveau_amour, Integer niveau_sante, Integer niveau_travail){
        JsonObject result = null;
        List<String> predi = new ArrayList<>();
        
        try {

            URI requestUri = URI.create(
                    "https://servif.insa-lyon.fr/WebDataGenerator/Astro"
                    + "?service=predictions"                           
                    + "&key=ASTRO-01-M0lGLURBU0ktQVNUUk8tQjAx"
                    + "&couleur="+ URLEncoder.encode(couleur, StandardCharsets.UTF_8)
                    + "&animal="+ URLEncoder.encode(animal, StandardCharsets.UTF_8)
                    + "&niveau-amour="+ niveau_amour
                    + "&niveau-sante="+ niveau_sante
                    + "&niveau-travail="+ niveau_travail
                );
            System.out.println(requestUri);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(requestUri).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                String body = httpResponse.body();
                //System.out.println(body);

                result = Json.createReader(new StringReader(body)).readObject();
            }
            else {
                throw new IOException("HTTP Error Status Code "+httpResponse.statusCode());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            result = null;
        }        
       predi.add(result.getString("prediction-amour"));
       predi.add(result.getString("prediction-sante"));
       predi.add(result.getString("prediction-travail"));
       
        return predi;
    }
    
    public static double[] appelCoordonneesGeo(String libelle){
        JsonObject result = null;
        double[] coords = new double[2];
        
        try {

            // TODO: adapter l'URL de l'API et la liste des paramètres
            URI requestUri = URI.create(
                    "https://data.geopf.fr/geocodage/search?"                      
                    + "&autocomplete=0"
                    + "&index="+ URLEncoder.encode("address", StandardCharsets.UTF_8)
                    + "&limit=1"
                    + "&returntruegeometry=false"
                    + "&q="+ URLEncoder.encode(libelle, StandardCharsets.UTF_8)
                );

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(requestUri).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                String body = httpResponse.body();
                //System.out.println(body);

                result = Json.createReader(new StringReader(body)).readObject();
            }
            else {
                throw new IOException("HTTP Error Status Code "+httpResponse.statusCode());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            result = null;
        }
        
        coords[0] = result.getJsonArray("features").getJsonObject(0).getJsonObject("geometry").getJsonArray("coordinates").getJsonNumber(0).doubleValue();
        coords[1] = result.getJsonArray("features").getJsonObject(0).getJsonObject("geometry").getJsonArray("coordinates").getJsonNumber(1).doubleValue();

        return coords;
    }
    
 
}
