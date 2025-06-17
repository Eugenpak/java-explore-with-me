package ru.practicum.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stats")
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    @NotBlank(message = "app не должно быть пустым")
    private String app;

    @Column(name = "uri")
    @NotBlank(message = "uri не должно быть пустым")
    private String uri;

    @Column(name = "ip")
    @NotBlank(message = "ip не должно быть пустым")
    private String ip;

    @Column(name = "time_stamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "timestamp не должно быть null")
    private LocalDateTime timestamp;
}
