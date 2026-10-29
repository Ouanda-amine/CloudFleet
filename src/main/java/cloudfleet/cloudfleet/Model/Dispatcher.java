package cloudfleet.cloudfleet.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Dispatcher extends User {

    private String phoneNumber;
    private String department;

    @OneToMany(mappedBy = "dispatcher")
    private List<Mission> missions;

    public Dispatcher() {
        this.setRole(Role.DISPATCHER);
    }

    public Dispatcher(Long id, String firstName, String lastName, String email, String password,
                      boolean isEnabled, String phoneNumber, String department) {
        super(id, firstName, lastName, email, password, isEnabled, Role.DISPATCHER);
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

}
