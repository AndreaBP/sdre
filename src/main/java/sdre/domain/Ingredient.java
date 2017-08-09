package sdre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import fsp3.domain.EntityBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public @NoArgsConstructor @Getter @Setter class Ingredient extends EntityBase {

//  private @Id @GeneratedValue Long id;

  private @Column(nullable = false) @Size(max=25, min=1) String name;

  private @ManyToMany(mappedBy = "ingredients") Set<Pizza> pizzas = new HashSet<>();
}
