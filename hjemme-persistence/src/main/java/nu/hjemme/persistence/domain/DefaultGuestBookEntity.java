package nu.hjemme.persistence.domain;

import nu.hjemme.client.domain.GuestBook;
import nu.hjemme.client.domain.User;
import nu.hjemme.persistence.GuestBookEntity;
import nu.hjemme.persistence.UserEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

import static java.util.Objects.hash;
import static nu.hjemme.persistence.meta.GuestBookMetadata.GUEST_BOOK_TABLE;
import static nu.hjemme.persistence.meta.GuestBookMetadata.TITLE;
import static nu.hjemme.persistence.meta.GuestBookMetadata.USER;

@Entity
@Table(name = GUEST_BOOK_TABLE)
public class DefaultGuestBookEntity extends DefaultPersistentEntity implements GuestBookEntity {

    @Column(name = TITLE) private String title;
    @JoinColumn(name = USER) @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY) private DefaultUserEntity user;

    public DefaultGuestBookEntity() { }

    /** @param guestBook will be used to copy an instance... */
    public DefaultGuestBookEntity(GuestBook guestBook) {
        title = guestBook.getTitle();
        user = castOrInitializeCopyWith(guestBook.getUser(), DefaultUserEntity.class);
    }

    @Override public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() &&
                Objects.equals(getId(), ((DefaultGuestBookEntity) o).getId()) &&
                Objects.equals(title, ((DefaultGuestBookEntity) o).title) &&
                Objects.equals(user, ((DefaultGuestBookEntity) o).user);
    }

    @Override public int hashCode() {
        return hash(title, user);
    }

    @Override public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .appendSuper(super.toString())
                .append(title)
                .append(user)
                .toString();
    }

    @Override public String getTitle() {
        return title;
    }

    @Override public User getUser() {
        return user;
    }

    @Override public void setTitle(String title) {
        this.title = title;
    }

    @Override public void setUser(UserEntity user) {
        this.user = castOrInitializeCopyWith(user, DefaultUserEntity.class);
    }
}