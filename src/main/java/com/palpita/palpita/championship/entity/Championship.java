package com.palpita.palpita.championship.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Championship {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  String name;

  String slug;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  @OneToMany(mappedBy = "championship")
  @JsonIgnore
  private List<Match> matches;
}
