package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    @Size(max = 100, message = "Название не должно превышать 100 символов")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    @Min(value = 1, message = "Продолжительность должна быть не менее 1 минуты")
    private int duration;

    @Min(value = 0, message = "Возрастное ограничение должно быть не менее 0")
    @Max(value = 18, message = "Возрастное ограничение должно быть не более 18")
    private int ageRestriction;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL)
    private List<Show> shows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Название не может быть пустым") @Size(max = 100, message = "Название не должно превышать 100 символов") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Название не может быть пустым") @Size(max = 100, message = "Название не должно превышать 100 символов") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Описание не может быть пустым") @Size(max = 1000, message = "Описание не должно превышать 1000 символов") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Описание не может быть пустым") @Size(max = 1000, message = "Описание не должно превышать 1000 символов") String description) {
        this.description = description;
    }

    @Min(value = 1, message = "Продолжительность должна быть не менее 1 минуты")
    public int getDuration() {
        return duration;
    }

    public void setDuration(@Min(value = 1, message = "Продолжительность должна быть не менее 1 минуты") int duration) {
        this.duration = duration;
    }

    @Min(value = 0, message = "Возрастное ограничение должно быть не менее 0")
    @Max(value = 18, message = "Возрастное ограничение должно быть не более 18")
    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(@Min(value = 0, message = "Возрастное ограничение должно быть не менее 0") @Max(value = 18, message = "Возрастное ограничение должно быть не более 18") int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
