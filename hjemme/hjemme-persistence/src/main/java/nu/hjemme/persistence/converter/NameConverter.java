package nu.hjemme.persistence.converter;

import nu.hjemme.client.datatype.Name;

public class NameConverter implements TypeConverter<Name, String> {
    @Override public Name convertTo(String from) {
        return from != null ? new Name(from) : null;
    }

    @Override public String convertFrom(Name name) {
        return name != null ? name.getName() : null;
    }
}
