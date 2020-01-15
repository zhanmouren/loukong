package com.koron.inwlms.util;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * gson string转int适配器
 * @author jkw
 *
 */
public class IntTypeAdapter extends TypeAdapter<Number>{

	@Override
    public void write(JsonWriter out, Number value)
            throws IOException {
        out.value(value);
    }

    @Override
    public Number read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            String result = in.nextString();
            if ("".equals(result)) {
                return null;
            }
            return Integer.parseInt(result);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

}
