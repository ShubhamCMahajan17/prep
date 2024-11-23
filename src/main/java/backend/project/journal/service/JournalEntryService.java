package backend.project.journal.service;

import backend.project.journal.entity.JournalEntry;
import backend.project.journal.entity.User;
import backend.project.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findByUserName(username);

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntry().add(savedEntry);
            userService.saveUser(user);
        } catch (Exception exception) {
            System.out.println(exception);
            throw new RuntimeException("an error occurred while saving the entry. ", exception);
        }

    }

    public void saveEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntry() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteJournalEntryById(ObjectId id, String userName) {
        boolean isRemoved = false;
        try {
            User user = userService.findByUserName(userName);
            isRemoved = user.getJournalEntry().removeIf(x -> x.getId().equals(id));
            if (isRemoved) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception exception) {
            throw new RuntimeException("An error occurred while deleting the entry.", exception);
        }
        return isRemoved;
    }

}
