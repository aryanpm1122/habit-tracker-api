package com.HabitTrackerAPI.Habit.Tracker.API;

import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class HabitResponseDTO {
    
    private Long id;
    private String name;
    private LocalTime time;

    private boolean completedToday;
    private int streak;

    private List<LogDTO> logs;
}
