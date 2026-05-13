package com.HabitTrackerAPI.Habit.Tracker.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

// Controller layer handles HTTP requests from client
// Provides APIs for managing habits (CRUD operations)

@RestController
@RequestMapping("/habits")
public class HabitController {

    @Autowired
    private HabitService service;

    // Create a new habit
    @PostMapping
    public ResponseEntity<HabitResponseDTO> addHabit(@Valid @RequestBody HabitDTO dto) {
        return ResponseEntity.ok(service.addHabit(dto));
    }

    // Get all habits
    @GetMapping
    public List<HabitResponseDTO> getAll() {
        return service.getAllHabits();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<String> markComplete(@PathVariable Long id) {
        return ResponseEntity.ok(service.markCompleted(id));
    }

    @GetMapping("/{id}/streak")
    public int getStreak(@PathVariable Long id) {
        return service.getStreak(id);
    }

    @GetMapping("/{id}/history")
    public List<LogDTO> getHistory(@PathVariable Long id) {
        return service.getHistory(id);
    }

    @GetMapping("/{id}/completed-today")
    public boolean isCompletedToday(@PathVariable Long id) {
        return service.isCompletedToday(id);
    }

    @DeleteMapping("/{id}")
    public String deleteHabit(@PathVariable Long id) {
        return service.deleteHabit(id);
    }
}