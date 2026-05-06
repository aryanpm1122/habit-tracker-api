package com.HabitTrackerAPI.Habit.Tracker.API;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LogDTO {

    private Long id;
    private boolean completed;
    private LocalDate date;

}