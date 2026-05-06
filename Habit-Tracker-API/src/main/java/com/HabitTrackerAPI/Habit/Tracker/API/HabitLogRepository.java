package com.HabitTrackerAPI.Habit.Tracker.API;

import java.util.List;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {

    List<HabitLog> findByHabitIdOrderByDateDesc(Long habitId);

    boolean existsByHabitIdAndDate(Long habitId, LocalDate date);
}
