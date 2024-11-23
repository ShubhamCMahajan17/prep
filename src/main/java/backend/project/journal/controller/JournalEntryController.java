package backend.project.journal.controller;

import backend.project.journal.entity.JournalEntry;
import backend.project.journal.entity.User;
import backend.project.journal.service.JournalEntryService;
import backend.project.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<JournalEntry> journalEntries = user.getJournalEntry();
        if (journalEntries != null && !journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUserName(authentication.getName());


            journalEntryService.saveEntry(journalEntry, user.getUserName());
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);

        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity getOneRecord(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());

        List<JournalEntry> filteredEntryByUser = user.getJournalEntry().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!filteredEntryByUser.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteOneRecord(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isRemoved = journalEntryService.deleteJournalEntryById(myId, authentication.getName());
        if (isRemoved) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateOneRecord(@PathVariable ObjectId myId, @RequestBody JournalEntry newJournalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<JournalEntry> filteredEntryByUser = user.getJournalEntry().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!filteredEntryByUser.isEmpty()) {
            JournalEntry oldJournalEntry = journalEntryService.getJournalEntryById(myId).orElse(null);
            if (oldJournalEntry != null) {
                oldJournalEntry.setTitle(newJournalEntry.getTitle() != null && !newJournalEntry.getTitle().equals("") ? newJournalEntry.getTitle() : oldJournalEntry.getTitle());
                oldJournalEntry.setContent(newJournalEntry.getContent() != null && !newJournalEntry.getContent().equals("") ? newJournalEntry.getContent() : oldJournalEntry.getContent());
                journalEntryService.saveEntry(oldJournalEntry);
                return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
