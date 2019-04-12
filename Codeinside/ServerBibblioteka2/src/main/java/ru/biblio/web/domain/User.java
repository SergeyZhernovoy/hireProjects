package ru.biblio.web.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Column(name = "ENABLE")
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public User(){

    }

    private User(Builder builder){
        this.name = builder.username;
        this.email  =builder.email;
        this.password = builder.password;
        this.enabled = builder.enable;
    }

    public static class Builder{

        private String username;
        private String password;
        private String email;
        private Boolean enable = false;

        public Builder setName(String name){
            this.username = name;
            return this;
        }

        public Builder setEmail(String email){
           this.email = email;
           return this;
        }

        public Builder setPassword(String password){
            this.password = password;
            return this;
        }

        public Builder setEnable(Boolean enable){
            this.enable = enable;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }

}
