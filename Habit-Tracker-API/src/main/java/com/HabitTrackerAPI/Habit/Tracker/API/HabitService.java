package com.HabitTrackerAPI.Habit.Tracker.API;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// Service layer contains business logic
// Acts as a bridge between Controller and Repository
@Service
public class HabitService {

    private final HabitRepository habitRepo;
    private final HabitLogRepository logRepo;

    public HabitService(HabitRepository habitRepo,
            HabitLogRepository logRepo) {

        this.habitRepo = habitRepo;
        this.logRepo = logRepo;
    }

    private static final Logger logger = LoggerFactory.getLogger(HabitService.class);

    // Fetch all habits from database
    public List<HabitResponseDTO> getAllHabits() {
        return habitRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Save habit to database
    public HabitResponseDTO addHabit(HabitDTO dto) {

        Habit habit = new Habit();
        habit.setName(dto.getName());
        habit.setTime(dto.getTime());
        habit.setCreatedDate(LocalDate.now());

        return convertToDTO(habitRepo.save(habit));
    }

    public String markCompleted(Long habitId) {

        Habit habit = habitRepo.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        if (logRepo.existsByHabitIdAndDate(habitId, LocalDate.now())) {
            throw new RuntimeException("Already marked today");
        }

        HabitLog log = new HabitLog();
        log.setHabit(habit);
        log.setDate(LocalDate.now());
        log.setCompleted(true);

        logRepo.save(log);

        habit.setCompletedDate(LocalDate.now());
        habitRepo.save(habit);

        logger.info("Habit marked completed");

        return "Habit marked completed";
    }

    public int getStreak(Long habitId) {

        List<HabitLog> logs = logRepo.findByHabitIdOrderByDateDesc(habitId);

        int streak = 0;
        LocalDate today = LocalDate.now();

        for (HabitLog log : logs) {
            if (log.isCompleted() && log.getDate().equals(today.minusDays(streak))) {
                streak++;
            } else {
                break;
            }
        }

        return streak;
    }

    public List<LogDTO> getHistory(Long habitId) {
        return logRepo.findByHabitIdOrderByDateDesc(habitId)
                .stream()
                .map(this::convertLogToDTO)
                .toList();
    }

    public boolean isCompletedToday(Long habitId) {

        return logRepo.existsByHabitIdAndDate(habitId, LocalDate.now());
    }

    public String deleteHabit(Long id) {

        if (!habitRepo.existsById(id)) {
            throw new RuntimeException("Habit not found");
        }

        habitRepo.deleteById(id);

        logger.info("Habit deleted");

        return "Habit deleted successfully";
    }

    private HabitResponseDTO convertToDTO(Habit habit) {

        HabitResponseDTO dto = new HabitResponseDTO();
        dto.setId(habit.getId());
        dto.setName(habit.getName());
        dto.setTime(habit.getTime());

        dto.setCompletedToday(
                logRepo.existsByHabitIdAndDate(habit.getId(), LocalDate.now()));

        dto.setStreak(getStreak(habit.getId()));

        if (habit.getLogs() != null) {
            dto.setLogs(
                    habit.getLogs().stream()
                            .map(this::convertLogToDTO)
                            .toList());
        }

        return dto;
    }

    private LogDTO convertLogToDTO(HabitLog log) {

        LogDTO dto = new LogDTO();
        dto.setId(log.getId());
        dto.setCompleted(log.isCompleted());
        dto.setDate(log.getDate());

        return dto;
    }
}