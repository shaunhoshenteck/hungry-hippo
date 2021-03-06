package edu.northeastern.cs5500.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class FoodItem implements Model {
    private ObjectId id;
    private String foodItem;
    private Integer foodPrice;

    /** @return true if this food item is valid */
    @JsonIgnore
    public boolean isValid() {
        return (foodItem != null) && (foodPrice != null);
    }
}
