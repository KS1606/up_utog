package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Дата показа не может быть пустой")
    @FutureOrPresent(message = "Дата показа не может быть в прошлом")
    private LocalDate showDate;

    @NotNull(message = "Время показа не может быть пустым")
    private LocalTime showTime;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Дата показа не может быть пустой") @FutureOrPresent(message = "Дата показа не может быть в прошлом") LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(@NotNull(message = "Дата показа не может быть пустой") @FutureOrPresent(message = "Дата показа не может быть в прошлом") LocalDate showDate) {
        this.showDate = showDate;
    }

    public @NotNull(message = "Время показа не может быть пустым") LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(@NotNull(message = "Время показа не может быть пустым") LocalTime showTime) {
        this.showTime = showTime;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
