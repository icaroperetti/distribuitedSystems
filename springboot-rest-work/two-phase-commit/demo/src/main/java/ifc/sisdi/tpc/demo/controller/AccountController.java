package ifc.sisdi.tpc.demo.controller;


import ifc.sisdi.tpc.demo.exception.AccountNotFoundException;
import ifc.sisdi.tpc.demo.exception.BalanceNotEnougthException;
import ifc.sisdi.tpc.demo.exception.NotAllowedOperationException;
import ifc.sisdi.tpc.demo.exception.OperationNotFoundException;
import ifc.sisdi.tpc.demo.model.Account;
import ifc.sisdi.tpc.demo.model.Action;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private List<Account> accounts = new ArrayList<Account>(); // List of accounts
    private ReplicaController replicaController = new ReplicaController(); // Replica controller
    private boolean slave = true; // Check if the instance is a slave

    //Creating the accounts in the constructor
    public AccountController(){
        this.accounts.add(new Account(1234,100.00));
        this.accounts.add(new Account(4345,50.00));
        this.accounts.add(new Account(5678,250.00));
    }

    // List all accounts
    @GetMapping
    public List<Account> getAccounts(){
        return this.accounts;
    }

    // Get an account by id
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable int id){
        for(Account account : this.accounts){
            if(account.getAccount() == id){
                return account;
            }
        }
        throw new AccountNotFoundException(id);
    }

    //
    @GetMapping("/commit")
    public ResponseEntity commit(){
        Random rand = new Random();
        int val = rand.nextInt(10) + 1; // generates random number between 1 and 10
        boolean commit = (val > 3); // If the value is from 4 to 10 (70%)
        if(commit){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account postAction(@RequestBody Action action){
        Account account = this.getAccount(action.getAccount()); // Get the account

        // Check if the opration is deposit or withdraw
        if(action.getOperation().equals("deposit")) {

            // Check if the account is a slave
            if(!this.slave) {
                //  True - All the replicas can make the operation
                boolean response = this.replicaController.getCommitReplicas();
                if(!response){
                    // If the replica controller returns false, the operation is not allowed
                    throw new NotAllowedOperationException();
                }
                // Make the execution of the operation
                this.replicaController.postReplica(action);
            }
            account.deposit(action.getValue());
            return account;
        }
        // Makes the same verifications
        else if(action.getOperation().equalsIgnoreCase("withdraw")) {
            //Check the balance of the account
            if(account.balanceIsZero(action.getValue())){
                throw new BalanceNotEnougthException(action.getAccount());
            }
            if(!this.slave){
                boolean response = this.replicaController.getCommitReplicas();
                if(!response){
                    throw new NotAllowedOperationException();
                }
                this.replicaController.postReplica(action);
            }
            account.withdraw(action.getValue());
            return account;
        }
        throw new NotAllowedOperationException(); // If the operation does not exist
    }
    @ControllerAdvice
    class AccountNotFound {
        @ResponseBody
        @ExceptionHandler(AccountNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String accountNotFound(AccountNotFoundException r) {
            return r.getMessage();
        }
    }

    @ControllerAdvice
    class OperationNotFound {
        @ResponseBody
        @ExceptionHandler(OperationNotFoundException.class)
        @ResponseStatus(HttpStatus.FORBIDDEN)
        String accountNotFound(OperationNotFoundException r) {
            return r.getMessage();
        }
    }

    @ControllerAdvice
    class BalanceNotEnougth {
        @ResponseBody
        @ExceptionHandler(BalanceNotEnougthException.class)
        @ResponseStatus(HttpStatus.FORBIDDEN)
        String accountNotFound(BalanceNotEnougthException r) {
            return r.getMessage();
        }
    }
}


