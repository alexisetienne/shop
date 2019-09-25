package co.simplon.shop.Web;

import co.simplon.shop.dao.ProductRepository;
import co.simplon.shop.entities.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
@CrossOrigin("*")
@RestController
public class CatalogueRestController {
    private ProductRepository productRepository;

    public CatalogueRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping(path="/photoProduct/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        Product p=productRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/shop/products/"+p.getPhotoName()));
    }
    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception{
        Product p=productRepository.findById(id).get();
        p.setPhotoName(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home")+"/shop/products/"+p.getPhotoName()),file.getBytes());
        productRepository.save(p);
    }
}
