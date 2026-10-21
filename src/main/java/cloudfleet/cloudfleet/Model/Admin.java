package cloudfleet.cloudfleet.Model;

import jakarta.persistence.Entity;
import  lombok.AllArgsConstructor;

@Entity

public class Admin extends User{

    public Admin() {
        super();
        this.setRole(Role.ADMIN);
    }
    public Admin(Long id, String nom, String prenom, String email, String password, boolean isEnabled) {
        super(id, nom, prenom, email, password,isEnabled, Role.ADMIN);
    }
}
