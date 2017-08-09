package sdre.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fsp3.domain.EntityBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sdre.annotations.ValidRange;

@Entity
public @NoArgsConstructor @Getter @Setter class Pizza extends EntityBase {

	private @Column(nullable = false, length = 25) @NotNull @ValidRange(min=5, max=15) @Size(max=25, min=1) String name;

	private @Column(nullable = false) BigDecimal price;

	@JoinTable(name = "Pizza_Ingredient", joinColumns = @JoinColumn(name = "pizza_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
	private @ManyToMany Set<Ingredient> ingredients = new HashSet<>();

	private @OneToMany(mappedBy = "pizza") Set<Comment> comments = new HashSet<>();
	
}
