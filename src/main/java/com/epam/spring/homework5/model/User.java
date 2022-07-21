package com.epam.spring.homework5.model;

import com.epam.spring.homework5.model.enums.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Updatable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Override
    public void update(User entity) {
        this.name = entity.name;
        this.surname = entity.surname;
        this.email = entity.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
