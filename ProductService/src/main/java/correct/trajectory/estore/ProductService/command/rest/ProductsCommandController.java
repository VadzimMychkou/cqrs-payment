package correct.trajectory.estore.ProductService.command.rest;

import correct.trajectory.estore.ProductService.command.CreateProductCommand;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {

    private final Environment env;
    private final CommandGateway commandGateway;

    public ProductsCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductRestModel createProductRestModel) {

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .productId(UUID.randomUUID().toString()).build();

        String returnValue;

        //try {
            returnValue = commandGateway.sendAndWait(createProductCommand);
        //} catch (Exception ex) {
            //returnValue = ex.getLocalizedMessage();
        //}

        return returnValue;
    }

    //@GetMapping
    //public String getProduct() {
    //    return "HTTP GET handler " + env.getProperty("local.server.port");
    //}

    //@PutMapping
    //public String updateProduct() {
    //    return "product updated";
    //}

    //@DeleteMapping
    //public String deleteProduct() {
    //    return "product deleted";
    //}
}