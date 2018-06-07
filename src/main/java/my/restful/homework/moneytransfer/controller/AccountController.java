package my.restful.homework.moneytransfer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import my.restful.homework.moneytransfer.dao.AccountDAO;
import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.entity.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static my.restful.homework.moneytransfer.util.ObjectMapperProvider.getObjectMapper;
import static my.restful.homework.moneytransfer.util.StringUtils.errorResponseBody;
import static my.restful.homework.moneytransfer.util.StringUtils.okResponseBody;

@Path("/accounts")
public class AccountController {

    private final AccountDAO accountDao = new AccountDAO();

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(String jsonCommand) {
        Transaction transaction;

        try {
            transaction = getObjectMapper().readValue(jsonCommand, Transaction.class);

            accountDao.transfer(transaction); //TODO keep in mind, this Dao method is empty right now

        } catch (IOException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST.getStatusCode())
                    .entity(errorResponseBody(
                            Response.Status.BAD_REQUEST,
                            e.getMessage(),
                            "Expected parameters: amount, fromAccountId, toAccountId"))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }

        return Response
                .ok(okResponseBody(transaction), MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccounts() throws JsonProcessingException {
        List<Account> result = accountDao.findAllAccounts();
        return Response
                .ok(okResponseBody(result), MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
