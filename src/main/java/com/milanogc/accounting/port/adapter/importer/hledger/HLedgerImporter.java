package com.milanogc.accounting.port.adapter.importer.hledger;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.milanogc.accounting.application.account.AccountApplicationService;
import com.milanogc.accounting.application.account.PostingApplicationService;
import com.milanogc.accounting.application.account.commands.CreateAccountCommand;
import com.milanogc.accounting.application.account.commands.EntryCommand;
import com.milanogc.accounting.application.account.commands.PostCommand;

public class HLedgerImporter {
  
  private AccountApplicationService accountApplicationService;
  private PostingApplicationService postingApplicationService;

  public HLedgerImporter(AccountApplicationService accountApplicationService, PostingApplicationService postingApplicationService) {
    this.accountApplicationService = accountApplicationService;
    this.postingApplicationService = postingApplicationService;
  }

  public void createAccounts() {
    Context context = new Context();
    context.state(LedgerState.NOOP);

    while (context.state().process(context));

    new PostsProcessor(accountApplicationService, postingApplicationService, context.posts());
  }
}

class PostsProcessor {
  
  private AccountApplicationService accountApplicationService;
  private PostingApplicationService postingApplicationService;
  private List<PostCommand> posts;
  private HashMap<String, String> accounts;
  
  public PostsProcessor(AccountApplicationService accountApplicationService, PostingApplicationService postingApplicationService, List<PostCommand.Builder> posts) {
    this.accountApplicationService = accountApplicationService;
    this.postingApplicationService = postingApplicationService;
    this.posts = buildAllPosts(posts);
    createAccounts();
  }
  
  private List<PostCommand> buildAllPosts(List<PostCommand.Builder> postsToBuild) {
    List<PostCommand> posts = new ArrayList<>();
    
    for (PostCommand.Builder postToBuild : postsToBuild) {
      posts.add(postToBuild.build());
    }
    
    return posts;
  }
  
  private void createAccounts() {
    accounts = new LinkedHashMap<>();
    accounts.put("Root", "ROOT");
    
    for (PostCommand postCommand : posts) {
      PostCommand.Builder postBuilder = new PostCommand.Builder(postCommand.occurredOn())
          .description(postCommand.description());
      EntryCommand incompleteEntry = null;
      BigDecimal sum = BigDecimal.ZERO;
      
      for (EntryCommand entry : postCommand.entries()) {
        String parentAccountName = "Root";
        
        for (String accountName : entry.accountId().split(":")) {
          String completeAccountName = parentAccountName + ":" + accountName;
          
          if (!accounts.containsKey(completeAccountName)) {
            String id = accountApplicationService.createAccount(new CreateAccountCommand(accountName, accounts.get(parentAccountName), null, new Date()));
            accounts.put(completeAccountName, id);
          }
          
          parentAccountName = completeAccountName;
        }
        
        if (entry.amount() == null) {
          if (incompleteEntry != null) {
            throw new RuntimeException("There should be a single empty entry.");
          }
          
          incompleteEntry = entry;
        } else {
          postBuilder.addEntry(accounts.get("Root:" + entry.accountId()), entry.amount());
          sum = sum.add(entry.amount());
        }
      }
      
      if (incompleteEntry != null) {
        BigDecimal difference = BigDecimal.ZERO.subtract(sum);
        postBuilder.addEntry(accounts.get("Root:" + incompleteEntry.accountId()), difference);
      }
      
      postingApplicationService.post(postBuilder.build());
    }
  }
  
  public void print() {
    for (PostCommand post : posts) {
      System.out.println("Occurred on: " + post.occurredOn());
      System.out.println("Description: " + post.description());

      for (EntryCommand entry : post.entries()) {
        System.out.println("Account name: " + entry.accountId());
        System.out.println("Value: " + entry.amount());
      }
    }
    
    System.out.println("--- ACCOUNTS ---");
    
    for (Map.Entry<String, String> a : accounts.entrySet()) {
      System.out.println(a.getKey());
    }
  }
}

class Context {
  private State state;
  private String line;
  private BufferedReader reader;
  private List<PostCommand.Builder> posts;
  private PostCommand.Builder currentPost;
  private Date previousOccurredOn;

  public Context() {
    String home = System.getProperty("user.home");
    String fileName = home + "/.hledger.journal";

    try {
      reader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    posts = new ArrayList<>();
    previousOccurredOn = Date.from(LocalDate.of(1981, 6, 9).atStartOfDay(ZoneId.systemDefault()).toInstant());
    nextLine();
  }

  public String currentLine() {
    return line;
  }

  public void nextLine() {
    try {
      line = reader.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public State state() {
    return state;
  }

  public void state(State state) {
    this.state = state;
  }

  public void addPost(Date occurredOn, String description) {
    if (occurredOn.before(previousOccurredOn)) {
      throw new RuntimeException(occurredOn + " should not be before " + previousOccurredOn);
    }
    
    previousOccurredOn = occurredOn;
    currentPost = new PostCommand.Builder(occurredOn)
        .description(description);
    posts.add(currentPost);
  }

  public void addEntry(String accountName, BigDecimal amount) {
    currentPost.addEntry(accountName, amount);
  }
  
  public List<PostCommand.Builder> posts() {
    return posts;
  }
}

interface State {
  boolean process(Context context);
}

enum LedgerState implements State {
  NOOP {
    @Override
    public boolean process(Context context) {
      if (context.currentLine() == null) {
        return false;
      }

      Matcher matcher = NOOP_PATTERN.matcher(context.currentLine());

      if (!matcher.matches()) {
        context.state(DATE_AND_DESCRIPTION);
        return true;
      }

      context.nextLine();
      context.state(NOOP);
      return true;
    }
  },
  DATE_AND_DESCRIPTION {
    @Override
    public boolean process(Context context) {
      Matcher matcher = DATE_AND_DESCRIPTION_PATTERN.matcher(context.currentLine());

      if (!matcher.matches()) {
        return false;
      }

      LocalDate localDate = LocalDate.parse(matcher.group(1), DATE_TIME_FORMATTER);
      Date occurredOn = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      String description = matcher.group(2);
      context.addPost(occurredOn, description);
      context.nextLine();
      context.state(ACCOUNT_AND_VALUE);
      return true;
    }
  },
  ACCOUNT_AND_VALUE {
    @Override
    public boolean process(Context context) {
      Matcher matcher = ACCOUNT_AND_VALUE_PATTERN.matcher(context.currentLine());

      if (!matcher.matches()) {
        matcher = ACCOUNT_WITHOUT_VALUE_PATTERN.matcher(context.currentLine());

        if (!matcher.matches()) {
          context.state(NOOP);
          return true;
        }

        String accountName = matcher.group(1);
        context.addEntry(accountName, null);
        context.nextLine();
        context.state(ACCOUNT_AND_VALUE);
        return true;
      }

      String accountName = matcher.group(1);
      BigDecimal value = new BigDecimal(matcher.group(2));
      context.addEntry(accountName, value);
      context.nextLine();
      context.state(ACCOUNT_AND_VALUE);
      return true;
    }
  };

  private static Pattern NOOP_PATTERN = Pattern.compile("^\\s*(;.*)?$");
  private static Pattern DATE_AND_DESCRIPTION_PATTERN = Pattern.compile("^\\s*(\\d{4}/\\d{2}/\\d{2})\\s+(.*)\\s*$");
  private static Pattern ACCOUNT_AND_VALUE_PATTERN = Pattern.compile("^\\s+(.*)\\s\\s+([+-]?[0-9]*[.]?[0-9]+).*$");
  private static Pattern ACCOUNT_WITHOUT_VALUE_PATTERN = Pattern.compile("^\\s+(.*)\\s*(\\s.*)?$");

  private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
}
