package com.milanogc.accounting.infrastructure.ui.web.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.PostingApplicationService;
import com.milanogc.accounting.application.account.commands.CreateAccountCommand;
import com.milanogc.accounting.application.account.commands.EntryCommand;
import com.milanogc.accounting.application.account.commands.PostCommand;
import com.milanogc.accounting.infrastructure.importer.hledger.HledgerImporter;
import com.milanogc.accounting.readmodel.finder.postgres.PostgresAccountFinder;
import com.milanogc.accounting.readmodel.finder.postgres.PostgresEntryFinder;
import com.milanogc.accounting.readmodel.finder.postgres.PostgresPostingFinder;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Account;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Accounts;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Entries;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Posting;

@RestController
public class AccountingController {

  @Autowired
  private PostgresAccountFinder accountFinder;
  
  @Autowired
  private PostgresPostingFinder postingFinder;
  
  @Autowired
  private PostgresEntryFinder entryFinder;

  @Autowired
  private AccountApplicationService accountApplicationService;
  
  @Autowired
  private PostingApplicationService postingApplicationService;
  
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @GetMapping("/accounts")
  public Accounts allAccounts() {
    return accountFinder.allAccounts();
  }

  @GetMapping("/accounts/{id}")
  public ResponseEntity<Account> account(@PathVariable String id) {
    return new ResponseEntity<Account>(accountFinder.account(id), HttpStatus.OK);
  }

  @PostMapping("/accounts")
  public ResponseEntity<Account> createAccount(@RequestBody Account account) {
    CreateAccountCommand command = new CreateAccountCommand(account.getName(), account.getParent(),
        account.getDescription(), new Date());
    String accountId = accountApplicationService.createAccount(command);
    return new ResponseEntity<Account>(accountFinder.account(accountId), HttpStatus.CREATED);
  }

  @GetMapping("/entries")
  public ResponseEntity<Entries> entriesOfAccount(@RequestParam("filter[account]") String accountId) {
    return new ResponseEntity<Entries>(entryFinder.entriesOfAccount(accountId), HttpStatus.OK);
  }

  @PostMapping("/transactions")
  public ResponseEntity<Posting> postTransaction(@RequestBody Posting posting) {
    List<EntryCommand> entries = posting.getEntries().stream()
        .map(e -> new EntryCommand(e.getAccount(), e.getAmount()))
        .collect(Collectors.toCollection(ArrayList::new));
    PostCommand command = new PostCommand(posting.getOccurredOn(), entries, posting.getDescription());
    String postingId = postingApplicationService.post(command);
    return new ResponseEntity<Posting>(postingFinder.posting(postingId), HttpStatus.CREATED);
  }

  @PostMapping("/uploadJournalFile")
  public ResponseEntity<?> uploadHledgerFile(@RequestParam MultipartFile file) {
    try {
      jdbcTemplate.update("DELETE FROM POSTING_ENTRY");
      jdbcTemplate.update("DELETE FROM POSTING");
      jdbcTemplate.update("DELETE FROM ACCOUNT_CLOSURE WHERE DESCENDANT_ACCOUNT_ID <> 'ROOT'");
      jdbcTemplate.update("DELETE FROM ACCOUNT WHERE ID <> 'ROOT'");
      InputStreamReader isr = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
      BufferedReader br = new BufferedReader(isr);
      new HledgerImporter(accountApplicationService, postingApplicationService, br).createAccounts();
      return new ResponseEntity<>(HttpStatus.OK);
    }
    catch (Throwable e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
