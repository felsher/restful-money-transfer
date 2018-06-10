package my.restful.homework.moneytransfer.controller;

import my.restful.homework.moneytransfer.entity.Transaction;
import my.restful.homework.moneytransfer.service.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static my.restful.homework.moneytransfer.util.ObjectMapperProvider.getObjectMapper;

@Path("/transfers")
public class TransactionController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(String json) {
        try {
            Transaction transaction = getObjectMapper().readValue(json, Transaction.class);

            AccountService
                    .getInstance()
                    .transfer(transaction.getFromAccountId(), transaction.getToAccountId(), transaction.getAmount());

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
