package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название театра не может быть пустым")
    @Size(max = 100, message = "Название театра не может превышать 100 символов")
    private String name_theater;

    @NotBlank(message = "Адрес не может быть пустым")
    @Size(max = 200, message = "Адрес не может превышать 200 символов")
    private String address;

    @Size(max = 100, message = "Вебсайт не может превышать 100 символов")
    @Pattern(regexp = "^(https?://)?[\\w.-]+(?:\\.[\\w\\.-]+)+[/#?]?.*$",
            message = "Некорректный формат вебсайта")
    private String website;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<Hall> halls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Название театра не может быть пустым") @Size(max = 100, message = "Название театра не может превышать 100 символов") String getName_theater() {
        return name_theater;
    }

    public void setName_theater(@NotBlank(message = "Название театра не может быть пустым") @Size(max = 100, message = "Название театра не может превышать 100 символов") String name_theater) {
        this.name_theater = name_theater;
    }

    public @NotBlank(message = "Адрес не может быть пустым") @Size(max = 200, message = "Адрес не может превышать 200 символов") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Адрес не может быть пустым") @Size(max = 200, message = "Адрес не может превышать 200 символов") String address) {
        this.address = address;
    }

    public @Size(max = 100, message = "Вебсайт не может превышать 100 символов") @Pattern(regexp = "^(https?://)?[\\w.-]+(?:\\.[\\w\\.-]+)+[/#?]?.*$",
            message = "Некорректный формат вебсайта") String getWebsite() {
        return website;
    }

    public void setWebsite(@Size(max = 100, message = "Вебсайт не может превышать 100 символов") @Pattern(regexp = "^(https?://)?[\\w.-]+(?:\\.[\\w\\.-]+)+[/#?]?.*$",
            message = "Некорректный формат вебсайта") String website) {
        this.website = website;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }
}
