package sdre.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public @NoArgsConstructor @Getter @Setter class Comment extends Entitybase{

//  private @Id @GeneratedValue Long id;

  private @Column(name = "customer_id", nullable = false) Long customerId;

  private @Column(name = "pizza_id", nullable = false) Long pizzaId;

  private @Column(nullable = false) @Temporal(TemporalType.TIMESTAMP) Date date;

  private @Column(nullable = false) @Size(max=25, min=1) String text;

  private Integer rating;

  private @ManyToOne @JoinColumn(name = "customer_id", insertable = false, updatable = false) Customer customer;

  private @ManyToOne @JoinColumn(name = "pizza_id", insertable = false, updatable = false) Pizza pizza;
}
