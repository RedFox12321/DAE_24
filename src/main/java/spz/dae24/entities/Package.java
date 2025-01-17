package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import spz.dae24.common.enums.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT new Package(p.code, p.status, p.client) FROM Package p ORDER BY p.code"
        ),
        @NamedQuery(
                name = "getPackagesByStatus",
                query = "SELECT new Package(p.code, p.status, p.client) FROM Package p WHERE p.status=:status ORDER BY p.code"
        )
})
@Table(name = "packages")
public class Package extends Versionable implements Serializable {
    @Id
    private long code;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "_package")
    private final List<Volume> volumes = new ArrayList<>();

    public Package() {}

    public Package(long code, Status status, Client client) {
        this.code = code;
        this.status = status;
        this.client = client;
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

    public int getVolumeCount() {
        return volumes.size();
    }
}
