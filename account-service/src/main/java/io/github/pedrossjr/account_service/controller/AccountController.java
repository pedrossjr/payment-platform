package io.github.pedrossjr.account_service.controller;

import io.github.pedrossjr.account_service.entity.Account;
import io.github.pedrossjr.account_service.record.AccountRecord;
import io.github.pedrossjr.account_service.record.AccountTransferRecord;
import io.github.pedrossjr.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRecord accountRecord) {
        return ResponseEntity.ok(accountService.createAccount(accountRecord));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Account>> get() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{accountNumber}/get")
    public ResponseEntity<Account> getAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountNumber(accountNumber));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody AccountTransferRecord accountTransferRecord ) {
        try {
            accountService.transferAmountAccount(accountTransferRecord);
            return ResponseEntity.ok("Transferência realizada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
