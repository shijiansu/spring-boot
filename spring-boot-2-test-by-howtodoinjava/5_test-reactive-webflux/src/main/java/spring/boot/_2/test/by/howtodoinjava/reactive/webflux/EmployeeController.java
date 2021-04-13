package spring.boot._2.test.by.howtodoinjava.reactive.webflux;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
  @Autowired private EmployeeServiceImpl employeeService;

  @PostMapping(value = {"/create", "/"})
  @ResponseStatus(CREATED)
  public void create(@RequestBody Employee e) {
    employeeService.create(e);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Mono<Employee>> findById(@PathVariable("id") Integer id) {
    Mono<Employee> e = employeeService.findById(id);
    HttpStatus status = (e != null) ? OK : NOT_FOUND;
    return new ResponseEntity<>(e, status);
  }

  @GetMapping(value = "/name/{name}")
  @ResponseStatus(OK)
  public Flux<Employee> findByName(@PathVariable("name") String name) {
    return employeeService.findByName(name);
  }

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseStatus(OK)
  public Flux<Employee> findAll() {
    return employeeService.findAll();
  }

  @PutMapping(value = "/update")
  @ResponseStatus(OK)
  public Mono<Employee> update(@RequestBody Employee e) {
    return employeeService.update(e);
  }

  @DeleteMapping(value = "/delete/{id}")
  @ResponseStatus(ACCEPTED)
  public void delete(@PathVariable("id") Integer id) {
    employeeService.delete(id).subscribe();
  }
}
