import jakarta.persistence.*;

@Entity
@Table(name = "needs")
public class Need {
    @Id
    private int needId;
    private String title;
    private String text;
    private String picture;
    private String type;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "userid")
    private User owner;

    public int getNeedId() {
        return needId;
    }

    public void setNeedId(int needId) {
        this.needId = needId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}