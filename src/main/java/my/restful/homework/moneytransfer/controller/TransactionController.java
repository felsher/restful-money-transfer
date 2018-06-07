package my.restful.homework.moneytransfer.controller;

import my.restful.homework.moneytransfer.entity.SuccessfulTransaction;
import my.restful.homework.moneytransfer.entity.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static my.restful.homework.moneytransfer.dao.TransactionDAO.getTransactionDao;
import static my.restful.homework.moneytransfer.util.ObjectMapperProvider.getObjectMapper;
import static my.restful.homework.moneytransfer.util.StringUtils.toPrettyJSON;

@Path("/transfers")
public class TransactionController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(String json) {
        try {
            Transaction transaction = getObjectMapper().readValue(json, Transaction.class);

            //some transfer method whom returns SuccessfulTransaction
            SuccessfulTransaction result = new SuccessfulTransaction(12345L);

            return Response
                    .ok(toPrettyJSON(result), MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransactions() {
        try {
            List<Transaction> result = getTransactionDao()
                    .findAllTransactions();

            return Response
                    .ok(toPrettyJSON(result), MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }
}
