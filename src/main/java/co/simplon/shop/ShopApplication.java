package co.simplon.shop;

import co.simplon.shop.dao.CategoryRepository;
import co.simplon.shop.dao.ProductRepository;
import co.simplon.shop.entities.Category;
import co.simplon.shop.entities.Product;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;


import java.util.Random;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);

		categoryRepository.save(new Category(null,"Computers",null,null,null));
		categoryRepository.save(new Category(null,"Tablets",null,null,null));
		categoryRepository.save(new Category(null,"Smart phones",null,null,null));
		Random rnd=new Random();
		categoryRepository.findAll().forEach(c->{
			for (int i = 0; i <10 ; i++) {
				Product p=new Product();
				p.setName(RandomString.make(18));
				p.setCurrentPrice(100+rnd.nextInt(10000));
				p.setAvailable(rnd.nextBoolean());
				p.setPromotion(rnd.nextBoolean());
				p.setSelected(rnd.nextBoolean());
				p.setCategory(c);
				p.setPhotoName("sony.png");
				productRepository.save(p);
			}
		});
	}

}