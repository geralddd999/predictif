package web.vue;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Serialisation {

    public Serialisation() {
    }

    public abstract void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException;
}