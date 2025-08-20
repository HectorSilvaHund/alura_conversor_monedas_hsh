papackage com.example.fx;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Ayudante para hacer peticiones HTTP (consultar la API de tipo de cambio).
 */
public class Http {
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /** Realiza una petición GET y devuelve el cuerpo (texto/JSON) */
    public static String get(String url) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(20))
                    .GET()
                    .build();

            HttpResponse<String> res = CLIENT.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 200 && res.statusCode() < 300) {
                return res.body();
            }
            throw new RuntimeException("Error HTTP " + res.statusCode() + " al llamar " + url);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al intentar conectar con la API", e);
        }
    }
}