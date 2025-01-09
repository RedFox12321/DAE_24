package spz.dae24.dtos;

import spz.dae24.entities.Volume;
import spz.dae24.entities.Package;

import java.util.List;

public class VolumeDTO {
    private int code;
    private int number;
    private String status;
    private Package _package;
    private List<ProductsVolumeDTO> volumeProducts;

    public VolumeDTO() {
    }

    public VolumeDTO(int code, int number, String status, Package _package, List<ProductsVolumeDTO> volumeProducts) {
        this.code = code;
        this.number = number;
        this.status = status;
        this._package = _package;
        this.volumeProducts = volumeProducts;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Package getPackage() {
        return _package;
    }
    public void setPackage(Package _package) {
        this._package = _package;
    }

    public List<ProductsVolumeDTO> getVolumeProducts() {
        return volumeProducts;
    }
    public void setVolumeProducts(List<ProductsVolumeDTO> volumeProducts) {
        this.volumeProducts = volumeProducts;
    }

    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
                volume.getCode(),
                volume.getNumber(),
                volume.getStatus().name(),
                volume.getPackage(),
                ProductsVolumeDTO.from(volume.getVolumeProducts())
        );
    }
}
