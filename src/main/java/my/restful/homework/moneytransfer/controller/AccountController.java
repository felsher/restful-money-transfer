package my.restful.homework.moneytransfer.controller;

import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.entity.Deposit;
import my.restful.homework.moneytransfer.service.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static my.restful.homework.moneytransfer.util.ObjectMapperProvider.getObjectMapper;
import static my.restful.homework.moneytransfer.util.StringUtils.toPrettyJSON;

@Path("/accounts")
public class AccountController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String json) {
        try {
            Account account = getObjectMapper().readValue(json, Account.class);

            int id = AccountService.getInstance().create(account.getBalance());

            return Response
                    .ok("{\n \"id\": " + id + "\n}\n", MediaType.APPLICATION_JSON_TYPE)
                    .build();

        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity("{ \n\"error_message\":\"" + e.getMessage() + "\" \n}\n")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            List<Account> accounts = AccountService.getInstance().list();

            return Response
                    .ok(toPrettyJSON(accounts), MediaType.APPLICATION_JSON_TYPE)
                    .build();

        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity("{ \n\"error_message\":\"" + e.getMessage() + "\" \n}\n")
                    .build();
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        try {
            Account account = AccountService.getInstance().get(id);

            return Response
                    .ok(toPrettyJSON(account), MediaType.APPLICATION_JSON_TYPE)
                    .build();

        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity("{ \n\"error_message\":\"" + e.getMessage() + "\" \n}\n")
                    .build();
        }
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        try {
            AccountService.getInstance().delete(id);

            return Response
                    .status(204)
                    .build();

        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity("{ \n\"error_message\":\"" + e.getMessage() + "\" \n}\n")
                    .build();
        }
    }

    @Path("/deposit")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deposit(String json) {
        try {
            Deposit deposit = getObjectMapper().readValue(json, Deposit.class);

            AccountService.getInstance().deposit(deposit.getAccountId(), deposit.getAmount());

            return Response
                    .status(204)
                    .build();

        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity("{ \n\"error_message\":\"" + e.getMessage() + "\" \n}\n")
                    .build();
        }
    }
}
