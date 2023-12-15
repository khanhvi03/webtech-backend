package htwberlin.webtech.m2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User request) throws URISyntaxException {
        var user = userService.createUser(request);
        URI uri = new URI("/users/" + user.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User>getUserById(@PathVariable Long id) {
        logger.info("GET request on route users with {}", id);
        var user = userService.get(id);
        return user != null? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser){
        var user = userService.updateUser(id, updatedUser);
        return user != null? ResponseEntity.ok(user):ResponseEntity.notFound().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        boolean successful = userService.deleteUser(id);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
