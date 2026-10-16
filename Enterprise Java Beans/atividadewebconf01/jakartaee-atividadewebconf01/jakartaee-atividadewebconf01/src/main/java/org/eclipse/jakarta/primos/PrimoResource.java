package org.eclipse.jakarta.primos;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("primos")
@Produces(MediaType.APPLICATION_JSON)
public class PrimoResource {

    @EJB
    private PrimoService primoService;

    @GET
    public Response proximoPrimo(@QueryParam("numero") String valor) {
        final int numero;
        try {
            numero = Integer.parseInt(valor == null ? "" : valor.trim());
        } catch (NumberFormatException exception) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", "Informe um número inteiro entre -2147483648 e 2147483647."))
                    .build();
        }
        return Response.ok(Map.of("numero", numero,
                "proximoPrimo", primoService.proximoPrimo(numero))).build();
    }
}
