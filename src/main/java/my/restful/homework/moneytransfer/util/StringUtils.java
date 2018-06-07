package my.restful.homework.moneytransfer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.entity.Transaction;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.StringJoiner;

import static my.restful.homework.moneytransfer.util.ObjectMapperProvider.getObjectMapper;

public class StringUtils {

    public static String errorResponseBody(Response.Status status, String errorMessage, String developerDescription) {
        StringJoiner joiner = new StringJoiner(",\n");
        joiner.add(String.format("\"code\":%s", status.getStatusCode()));
        joiner.add(String.format("\"status\":%s", status.getReasonPhrase()));
        joiner.add(String.format("\"description\":\"%s\"", developerDescription));
        joiner.add(String.format("\"errorMessage\":\"%s\"", errorMessage));
        return "{\n" + joiner.toString() + "\n}\n";

    }

    public static String okResponseBody(Transaction transaction) {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(String.format("\"successfulTransactionId\":\"%s\"", transaction.getUuid()));
        return "{\n" + joiner.toString() + "\n}\n";
    }

    public static String okResponseBody(List<Account> result) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        for (Account a : result) {
            sb.append(
                    getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(a)
            );
        }
        return sb.toString();
    }
}
