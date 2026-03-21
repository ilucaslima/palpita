package com.palpita.palpita.match.dto;

import com.palpita.palpita.match.entity.StatusMatch;
import com.palpita.palpita.users.entity.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RegisterMatch {
    Long id;
    String teamA;
    String teamB;
    LocalDateTime date;
    Integer scoreA;
    Integer scoreB;
    StatusMatch status;
    User user;
}
