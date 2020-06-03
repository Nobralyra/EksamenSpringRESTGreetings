package greetring.exercise.controllers;

import greetring.exercise.model.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(GreetingsController.BASE_URL)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class GreetingsController
{
    public static final String BASE_URL = "/greeting";
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final List<Greeting> greetingList;

    public GreetingsController(List<Greeting> greetingList)
    {
        this.greetingList = greetingList;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Greeting>> listAll()
    {
        if (!greetingList.isEmpty())
        {
            return new ResponseEntity<>(greetingList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * The @PathVariable method parameter annotation is used to indicate that a method parameter should be bound to the value of a URI template variable.
     * @param id
     * @return  ResponseEntity<Greeting>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Greeting> findGreetingById(@PathVariable("id") long id)
    {
        if (!greetingList.isEmpty() && !(greetingList.size() < id) && !(id <= 0))
        {
            Greeting foundGreeting = greetingList.get((int) id-1); //so the id matches with the index - not a beautiful way to do it
            return new ResponseEntity<Greeting>(foundGreeting, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @RequestBody maps the request to the model class (Greeting) so you can retrueve or set values from getter and setters
     * @param greeting
     * @return ResponseEntity<Greeting>
     */
    @PostMapping("/create")
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting)
    {
        Greeting createdGreeting = new Greeting(counter.incrementAndGet(), String.format(template, greeting.getContent()));
        greetingList.add(createdGreeting);
        return new ResponseEntity<Greeting>(createdGreeting, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Greeting> updateGreeting(@PathVariable("id") long id, @RequestBody Greeting greeting)
    {
        if (!greetingList.isEmpty() && !(greetingList.size() < id) && !(id <= 0))
        {
            Greeting updatedGreeting = new Greeting(id, String.format(template, greeting.getContent()));
            greetingList.set((int) id-1, updatedGreeting);
            return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGreeting(@PathVariable("id") long id)
    {
        if (!greetingList.isEmpty() && !(greetingList.size() < id) && !(id <= 0))
        {
            greetingList.remove((int) id-1);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
