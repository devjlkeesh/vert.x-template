package uz.smartbank.business.esb.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import uz.smartbank.business.esb.payload.Tuple2;

import java.io.IOException;

public class TupleArrayToMapSerializer extends JsonSerializer<Tuple2<String, String>[]> {
    public void serialize(Tuple2<String, String>[] tuples, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (tuples != null && tuples.length > 0) {
            generator.writeStartObject();
            for (Tuple2<String, String> tuple : tuples) {
                generator.writeStringField(tuple.t1(), tuple.t2());
            }
            generator.writeEndObject();
        }

    }
}
