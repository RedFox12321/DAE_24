package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import spz.dae24.common.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT new Package(p.code, p.status) FROM Package p"
        )
})
public class Package {
    @Id
    private int code;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @ManyToOne
    private User client;

    @NotNull
    @OneToMany(mappedBy = "_package")
    private final List<Volume> volumes = new ArrayList<>();

    public Package() {
    }

    public Package(int code, Status status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public User getClient() {
        return client;
    }
    public void setClient(User client) {
        this.client = client;
    }
    public List<Volume> getVolumes() {
        return volumes;
    }
    public boolean addVolume(Volume volume) {return volumes.add(volume);}
    public boolean removeVolume(Volume volume) {return volumes.remove(volume);}
}
