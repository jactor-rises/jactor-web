package nu.hjemme.client.domain;

import nu.hjemme.client.datatype.Description;
import nu.hjemme.client.datatype.Name;

public interface Person extends Persistent<Long> {

    Description getDescription();

    User getUser();

    Address getAddress();

    Name getFirstName();

    Name getLastName();

}
