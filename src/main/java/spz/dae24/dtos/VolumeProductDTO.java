package spz.dae24.dtos;

import spz.dae24.entities.Volume;
import spz.dae24.entities.VolumeProduct;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeProductDTO {
    private int id;
    private int quantity;
    private Volume volume;

    public VolumeProductDTO() {
    }

    public VolumeProductDTO(int id, int quantity, Volume volume) {
        this.id = id;
        this.quantity = quantity;
        this.volume = volume;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Volume getVolume() {
        return volume;
    }
    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public static VolumeProductDTO from(VolumeProduct volumeProduct) {
        return new VolumeProductDTO(
                volumeProduct.getId(),
                volumeProduct.getQuantity(),
                volumeProduct.getVolume()
        );
    }

    public static List<VolumeProductDTO> from(List<VolumeProduct> volumeProducts) {
        return volumeProducts.stream().map(VolumeProductDTO::from).collect(Collectors.toList());
    }
}
