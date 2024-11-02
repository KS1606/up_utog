package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название зала не может быть пустым")
    @Size(max = 100, message = "Название зала не должно превышать 100 символов")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Min(value = 1, message = "Количество мест должно быть больше нуля")
    @Max(value = 1000, message = "Количество мест не должно превышать 1000")
    @Column(name = "seat_count", nullable = false)
    private int seatCount;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Show> shows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Название зала не может быть пустым") @Size(max = 100, message = "Название зала не должно превышать 100 символов") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Название зала не может быть пустым") @Size(max = 100, message = "Название зала не должно превышать 100 символов") String name) {
        this.name = name;
    }

    @Min(value = 1, message = "Количество мест должно быть больше нуля")
    @Max(value = 1000, message = "Количество мест не должно превышать 1000")
    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(@Min(value = 1, message = "Количество мест должно быть больше нуля") @Max(value = 1000, message = "Количество мест не должно превышать 1000") int seatCount) {
        this.seatCount = seatCount;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
