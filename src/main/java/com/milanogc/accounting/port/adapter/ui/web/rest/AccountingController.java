package com.milanogc.accounting.port.adapter.ui.web.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.PostingApplicationService;
import com.milanogc.accounting.application.account.commands.CreateAccountCommand;
import com.milanogc.accounting.application.account.commands.EntryCommand;
import com.milanogc.accounting.application.account.commands.PostCommand;
import com.milanogc.accounting.readmodel.finder.postgres.PostgresAccountFinder;
import com.milanogc.accounting.readmodel.finder.postgres.PostgresEntryFinder;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Account;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Accounts;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Entries;
import com.milanogc.accounting.readmodel.finder.postgres.dto.Posting;

@RestController
public class AccountingController {

  @Autowired
  private PostgresAccountFinder accountFinder;
  
  @Autowired
  private PostgresEntryFinder entryFinder;

  @Autowired
  private AccountApplicationService accountApplicationService;
  
  @Autowired PostingApplicationService postingApplicationService;

  @RequestMapping(value = "/accounts", method = RequestMethod.GET)
  @ResponseBody
  public Accounts allAccounts() {
    return accountFinder.allAccounts();
  }

  @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Account> account(@PathVariable("id") String accountId) {
    return new ResponseEntity<Account>(accountFinder.account(accountId), HttpStatus.OK);
  }

  @RequestMapping(value = "/accounts", method = RequestMethod.POST)
  public ResponseEntity<Account> createAccount(@RequestBody Account account) {
    CreateAccountCommand command = new CreateAccountCommand(account.getName(), account.getParent(),
        account.getDescription(), new Date());
    String accountId = accountApplicationService.createAccount(command);
    return new ResponseEntity<Account>(accountFinder.account(accountId), HttpStatus.CREATED);
  }

  @RequestMapping(value = "/entries", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Entries> entriesOfAccount(@RequestParam("filter[account]") String accountId) {
    return new ResponseEntity<Entries>(entryFinder.entriesOfAccount(accountId), HttpStatus.OK);
  }

  @RequestMapping(value = "/transactions", method = RequestMethod.POST)
  public void postTransaction(@RequestBody Posting posting) {
    List<EntryCommand> entries = posting.getEntries().stream()
        .map(e -> new EntryCommand(e.getAccount(), e.getAmount()))
        .collect(Collectors.toCollection(ArrayList::new));
    PostCommand command = new PostCommand(posting.getOccurredOn(), entries, posting.getDescription());
    postingApplicationService.post(command);
  }
}
