package com.bits.group13.fitnesstracker.database;

import com.bits.group13.fitnesstracker.model.Goal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goals")
public final class GoalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String type;

    public Goal toGoal() {
        return new Goal(id, type);
    }
}
