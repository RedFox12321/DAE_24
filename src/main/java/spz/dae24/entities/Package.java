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
                query = "SELECT new Package(p.status) FROM Package p"
        )
})
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "_package")
    private final List<Volume> volumes = new ArrayList<>();

    public Package() {
    }

    public Package(Status status) {
        this.status = status;
    }

    public long getCode() {
        return code;
    }
    public void setCode(long code) {
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
    public void setClient(Client client) {
        this.client = client;
    }
    public List<Volume> getVolumes() {
        return volumes;
    }
    public boolean addVolume(Volume volume) {return volumes.add(volume);}
    public boolean removeVolume(Volume volume) {return volumes.remove(volume);}
}
