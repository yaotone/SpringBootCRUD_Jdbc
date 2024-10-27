package ru.yaotone.project.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    private int id;

    @NotEmpty(message = "Строка не должна быть пустой")
    @Size(min = 2, message = "Имя должно быть длиннее 2 символов")
    @Size(max = 24, message = "Имя должно быть короче 24 символов")
    private String name;

    @NotEmpty(message = "Строка не должна быть пустой")
    @Size(min = 2, message = "Фамилия должна быть длиннее 2 символов")
    @Size(max = 30, message = "Фамилия должна быть короче 30 символов")
    private String surname;

    @Min(value = 12, message = "Вы должны быть старше 12 лет")
    @Max(value = 100, message = "Возраст должен быть меньше 101")
    private int age;

    public User() {

    }

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
