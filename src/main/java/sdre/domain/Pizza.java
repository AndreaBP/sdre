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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public @NoArgsConstructor @Getter @Setter class Pizza extends Entitybase {

	// private @Id @GeneratedValue Long id;

	private @Column(nullable = false, length = 25) @NotNull @Pattern(regexp="") @Size(max=25, min=1) String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	private @Column(nullable = false) BigDecimal price;

	@JoinTable(name = "Pizza_Ingredient", joinColumns = @JoinColumn(name = "pizza_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
	private @ManyToMany Set<Ingredient> ingredients = new HashSet<>();

	private @OneToMany(mappedBy = "pizza") Set<Comment> comments = new HashSet<>();
}
