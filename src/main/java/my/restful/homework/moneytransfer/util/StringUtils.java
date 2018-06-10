package my.restful.homework.moneytransfer.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.StringJoiner;

import static my.restful.homework.moneytransfer.util.ObjectMapperProvider.getObjectMapper;

public class StringUtils {

    public static String toPrettyJSON(Object o) throws JsonProcessingException {
        if (o == null)
            return null;
        return getObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(o) + "\n";
    }

    public static String toPrettyJSON(List list) throws JsonProcessingException {
        if (list == null)
            return null;

        if (list.isEmpty())
            return "{\n\"result\":[]\n}\n";

        StringJoiner joiner = new StringJoiner(",\n");

        for (Object o : list) {
            joiner.add(getObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(o)
            );
        }

        return "{\n\"result\":[\n" + joiner.toString() + "\n]\n}\n";
    }
}
