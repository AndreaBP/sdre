package sdre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import fsp3.domain.EntityBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public @NoArgsConstructor @Getter @Setter class Customer extends EntityBase {

//  private @Id @GeneratedValue Long id;

  private @Column(nullable = false) @Size(max=25, min=1) String name;

  private @Column(nullable = false) String email;

  private @OneToMany(mappedBy = "customer") Set<Comment> comments = new HashSet<>();
}
