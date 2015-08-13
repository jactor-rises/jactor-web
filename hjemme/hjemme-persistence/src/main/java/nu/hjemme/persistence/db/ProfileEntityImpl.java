package nu.hjemme.persistence.db;

import nu.hjemme.client.datatype.Name;
import nu.hjemme.client.domain.Profile;
import nu.hjemme.persistence.ProfileEntity;
import nu.hjemme.persistence.UserEntity;
import nu.hjemme.persistence.meta.ProfileMetadata;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Objects;

import static java.util.Objects.hash;

/** @author Tor Egil Jacobsen */
public class ProfileEntityImpl extends PersistentEntity<Long> implements ProfileEntity {
    @Id
    @Column(name = ProfileMetadata.PROFILE_ID)
    // brukes av hibernate
    @SuppressWarnings("unused")
    void setProfileId(Long profileId) {
        setId(profileId);
    }

    @Column(name = ProfileMetadata.PERSON_ID)
    private PersonEntityImpl personEntity;

    @Column(name = ProfileMetadata.DESCRIPTION)
    private String description;

    @Column(name = ProfileMetadata.USER_ID)
    private UserEntity userEntity;

    public ProfileEntityImpl() {
        personEntity = new PersonEntityImpl();
    }

    public ProfileEntityImpl(Profile profile) {
        this();
        description = profile.getDescription();
        initPersonEntity();
        personEntity.setAddress(profile.getAddress() != null ? new AddressEntityImpl(profile.getAddress()) : null);
        personEntity.setFirstName(profile.getFirstName());
        personEntity.setLastName(profile.getLastName());
        userEntity = profile.getUser() != null ? new UserEntityImpl(profile.getUser()) : null;
    }

    public void addLastName(String lastName) {
        personEntity.setLastName(new Name(lastName));
    }

    public void addFirstName(String firstName) {
        personEntity.setFirstName(new Name(firstName));
    }

    public void addAddressEntity(AddressEntityImpl addressEntity) {
        personEntity.setAddress(addressEntity);
    }

    private void initPersonEntity() {
        if (personEntity == null) {
            personEntity = new PersonEntityImpl();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileEntityImpl that = (ProfileEntityImpl) o;

        return Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return hash(getDescription(), getAddress(), getFirstName(), getLastName(), getUser());
    }

    @Override
    public AddressEntityImpl getAddress() {
        return personEntity != null ? personEntity.getAddress() : null;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public UserEntity getUser() {
        return userEntity;
    }

    @Override
    public Name getFirstName() {
        return personEntity != null ? personEntity.getFirstName() : null;
    }

    @Override
    public Name getLastName() {
        return personEntity != null ? personEntity.getLastName() : null;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}