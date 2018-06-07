package my.restful.homework.moneytransfer.controller;

import my.restful.homework.moneytransfer.entity.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static my.restful.homework.moneytransfer.dao.AccountDAO.getAccountDao;
import static my.restful.homework.moneytransfer.util.StringUtils.toPrettyJSON;

@Path("/accounts")
public class AccountController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        try {
            List<Account> result = getAccountDao().findAllAccounts();
            return Response
                    .ok(toPrettyJSON(result), MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(String json) {
        return Response
                .status(405)
                .entity("{\n \"description\": \"Sorry, POST method for this endpoint is not implemented yet\"\n}\n")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
