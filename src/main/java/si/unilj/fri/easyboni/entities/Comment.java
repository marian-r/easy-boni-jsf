package si.unilj.fri.easyboni.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "comments", schema = "", catalog = "easy_boni")
public class Comment implements Serializable {
    private CommentPK primaryKey;
    private String text;
    private Timestamp timestamp;
    private User user;

    @EmbeddedId
    public CommentPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(CommentPK pk) {
        primaryKey = pk;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (!primaryKey.equals(comment.primaryKey)) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (timestamp != null ? !timestamp.equals(comment.timestamp) : comment.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = primaryKey.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
