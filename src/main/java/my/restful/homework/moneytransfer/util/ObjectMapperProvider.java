package my.restful.homework.moneytransfer.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperProvider {

    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            //Don't really need it right now
//            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
//            df.setTimeZone(TimeZone.getTimeZone("UTC"));
//            objectMapper.setDateFormat(df);
        }
        return objectMapper;
    }
}
