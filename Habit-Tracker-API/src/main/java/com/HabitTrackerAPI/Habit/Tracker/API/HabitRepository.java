package com.HabitTrackerAPI.Habit.Tracker.API;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository layer handles database operations
// JpaRepository provides built-in CRUD methods
public interface HabitRepository extends JpaRepository<Habit, Long> {
    
}
