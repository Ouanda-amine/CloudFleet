package cloudfleet.cloudfleet.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Dispatcher extends User {

    private String phoneNumber;
    public String department;


    public Dispatcher() {
        this.setRole(Role.DISPATCHER);
    }

    public Dispatcher(Long id, String nom, String prenom, String email, String password,boolean isEnabled , String phoneNumber, String department) {
        super(id, nom, prenom, email, password,isEnabled, Role.DISPATCHER);
        this.phoneNumber= phoneNumber;
        this.department = department;
    }

    @OneToMany
    private List<Mission> missions;


}
