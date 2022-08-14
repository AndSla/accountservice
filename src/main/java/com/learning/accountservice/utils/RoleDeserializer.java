package com.learning.accountservice.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.learning.accountservice.exception.RoleNotFoundException;
import com.learning.accountservice.model.enums.Role;

import java.io.IOException;

public class RoleDeserializer extends StdDeserializer<Role> {

    public RoleDeserializer() {
        this(null);
    }

    protected RoleDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Role deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {

        Role result;

        try {
            result = Role.valueOf("ROLE_" + jsonParser.getText());
        } catch (Exception e) {
            throw new RoleNotFoundException();
        }

        return result;

    }

}
