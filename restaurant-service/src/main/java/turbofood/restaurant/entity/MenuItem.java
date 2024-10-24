package turbofood.restaurant.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MenuItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Restaurant restaurant;

    private String name;

    private String description;

    private Double price;

    private Boolean available;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MenuItem other)) {
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
        return "MenuItem{" + "id=" + id + ",name=" + name + "}";
    }

}
