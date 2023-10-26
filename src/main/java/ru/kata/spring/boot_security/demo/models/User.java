package ru.kata.spring.boot_security.demo.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * //UserDetails нужен д/того, чтобы преобразовать юзера из БД к определенному стандарту, чтобы его понял Спринг Секьюрити.
 * //Т.е. UserDetails - это такая обертка д/Entity-класса.
 * //UserDetails заведует самым основным: полномочиями - getAuthorities(), паролем - getPassword() и
 * // именем юзера - getUsername()
 * <p>
 * Аннотации @NotEmpty, @Size, @Email - это проверка на валидность.
 * Проверка на то, что объект типа User пришел от клиента корректный
 **/
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Имя не может быть пустым.")
    @Size(min = 2, max = 10, message = "Имя должно состоять от 2 до 10 символов.")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Фамилия не может быть пустым.")
    @Size(min = 2, max = 10, message = "Фамилия должно состоять от 2 до 10 символов.")
    @Column(name = "lastname")
    private String lastname;

    @Min(14)
    @Column(name = "age")
    private Byte age;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    //Здесь жадная загрузка, чтобы сразу грузились все дочерние зависимости юзера. fetch (извлечение)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),//Это колонка текущей сущности, т.е. User.
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    //Это колонка второй (обратной) сущности, с которой связан User, т.е. Role.
    private Collection <Role> roles;

    public User() {
    }

    public User(String username, String lastname, Byte age, String email, String password, List<Role> roles) {
        this.username = username;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && lastname.equals(user.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, lastname);
    }
}


