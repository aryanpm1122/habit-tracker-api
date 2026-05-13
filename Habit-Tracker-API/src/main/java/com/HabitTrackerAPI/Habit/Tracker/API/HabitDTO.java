package com.HabitTrackerAPI.Habit.Tracker.API;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// DTO is used to transfer only required data
// Helps in hiding unnecessary or sensitive fields
@Data
public class HabitDTO {

    @NotBlank(message = "Habit name is required")
    private String name;
    
    private LocalTime time;

}
