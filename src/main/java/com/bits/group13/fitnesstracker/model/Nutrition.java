package com.bits.group13.fitnesstracker.model;
import com.bits.group13.fitnesstracker.database.NutritionRecord;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public final class Nutrition {

    private final String id;
    private final String nutritionType;

    public Nutrition(String id, String nutritionType) {
        this.id = id;
        this.nutritionType = nutritionType;
    }

    @JsonProperty
    public String id() {
        return id;
    }

    @JsonProperty
    public String nutritionType() {
        return nutritionType;
    }

    public NutritionRecord toNutritionRecord() {
        return new NutritionRecord(id, nutritionType);
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Nutrition) obj;
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.nutritionType, that.nutritionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nutritionType);
    }

    @Override
    public String toString() {
        return "Nutrition["
                + "id="
                + id
                + ", "
                + "nutritionType="
                + nutritionType
                + ']';
    }
}
