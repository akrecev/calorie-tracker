package com.kretsev.calorietracker.model;

import com.kretsev.calorietracker.calculator.HarrisBenedictCalculator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email(message = "Некорректный адрес электронной почты")
    @Column(unique = true, nullable = false)
    private String email;

    @Size(max = 150, message = "Некорректный возраст")
    private int age;

    @Size(max = 400, message = "Некорректный вес")
    private double weight;

    @Size(max = 300, message = "Некорректный рост")
    private double height;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Goal goal;

    private int dailyCalorieNorm;

    @PrePersist
    @PreUpdate
    public void calculateDailyNorm() {
        if (this.goal == null) {
            this.goal = Goal.MAINTENANCE;
        }
        dailyCalorieNorm = HarrisBenedictCalculator.calculate(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "id = "
                + id + ", " + "name = "
                + name + ", " + "email = "
                + email + ", " + "age = "
                + age + ", " + "weight = "
                + weight + ", " + "height = "
                + height + ", " + "goal = "
                + goal + ", " + "dailyCalorieNorm = "
                + dailyCalorieNorm + ")";
    }
}
