package utez.edu.mx.adoptame.e1.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;
import utez.edu.mx.adoptame.e1.entity.SessionControlAccess;
import utez.edu.mx.adoptame.e1.model.responses.InfoToast;
import utez.edu.mx.adoptame.e1.repository.SessionControlAccessRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private SessionControlAccessRepository sessionControlAccessRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        FlashMapManager flashMapManager = new SessionFlashMapManager();

        FlashMap flashMap = new FlashMap();

        InfoToast info = new InfoToast();

        info.setTitle("Cierre de sesión");
        info.setMessage("Has cerrado sesión con éxito");
        info.setTypeToast("success");

        flashMap.put("info", info);

        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        response.sendRedirect("/index");

        // Consulting the information to session in the binnacle
        SessionControlAccess sessionActual = sessionControlAccessRepository.findActualSessionByUsername(authentication.getName());

        if (sessionActual != null) {
            sessionActual.setLogoutDate(new Date());
            sessionActual.setIsActualSession(false);

            sessionControlAccessRepository.save(sessionActual);
        }

        super.handle(request, response, authentication);
    }
}
