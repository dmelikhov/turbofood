package turbofood.restaurant.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID ownerId;

    private String name;

    private String address;

    private String phone;

    private String website;

    private Boolean acceptingOrders;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Restaurant other)) {
            return false;
        }
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Restaurant{" + "id=" + id + ",name=" + name + "}";
    }

}
