package web.modele;

import jakarta.servlet.http.HttpServletRequest;

public abstract class Action {

    public Action() {
        
    }

    public abstract void execute(HttpServletRequest request);
}