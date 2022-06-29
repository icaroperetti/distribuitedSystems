package ifc.sisdi.tpc.demo.controller;

import ifc.sisdi.tpc.demo.exception.ReplicaNotFoundException;
import ifc.sisdi.tpc.demo.model.Account;
import ifc.sisdi.tpc.demo.model.Action;
import ifc.sisdi.tpc.demo.model.Replica;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/replicas")
public class ReplicaController {
    private List<Replica> replicas = new ArrayList<Replica>(); // List of replicas
    private RestTemplate restTemplate = new RestTemplate(); //Synchronous client to perform HTTP requests

    // Create two replicas in the constructor
    public ReplicaController() {
        replicas.add(new Replica(1, "http://localhost:8081/"));
        replicas.add(new Replica(2, "http://localhost:8082/"));
    }

    // List all replicas
    @GetMapping()
    public List<Replica> getReplicas() {
        return replicas;
    }

    // Get a replica by id
    @GetMapping("/{id}")
    public Replica getReplica(@PathVariable int id) {
        for (Replica replica : replicas) {
            if (replica.getId() == id) {
                return replica;
            }
        }
        throw new ReplicaNotFoundException(id);
    }

    // Get the action from AccountController and makes a POST request in each replica
    public void postReplica(Action action) {
        for (Replica replica : replicas) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Action> entity = new HttpEntity<Action>(action, headers);
            restTemplate.postForEntity(replica.getEndpoint() + "/accounts", entity, Account.class);
        }
    }


    public boolean getCommitReplicas() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity entity = new HttpEntity<>(null, headers);
       // To each replica makes a request to check if the commit was successful
        for (Replica replica : this.replicas) {
            try {
                this.restTemplate
                        .exchange(replica.getEndpoint() + "/accounts/commit", HttpMethod.GET, entity, RequestEntity.class);

            } catch (Exception e) {
                return false;
            }

        }
        // If pass the for in the replica, it means that the commit could be done
        return true;
    }

    @ControllerAdvice
    class ReplicaNotFount {
        @ResponseBody
        @ExceptionHandler(ReplicaNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String accountNotFound(ReplicaNotFoundException r) {
            return r.getMessage();
        }
    }
}


