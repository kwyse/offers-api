package offers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AmountSerializer extends StdSerializer<Amount> {
    public AmountSerializer() {
        this(null);
    }

    public AmountSerializer(Class<Amount> t) {
        super(t);
    }

    @Override
    public void serialize(
            Amount value, JsonGenerator gen, SerializerProvider provider
    ) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("value", value.toString());
        gen.writeStringField("currency", value.getCurrency().toString());

        gen.writeEndObject();
    }
}
