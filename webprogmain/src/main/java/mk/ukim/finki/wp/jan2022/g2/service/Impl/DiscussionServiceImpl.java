package mk.ukim.finki.wp.jan2022.g2.service.Impl;

import mk.ukim.finki.wp.jan2022.g2.model.Discussion;
import mk.ukim.finki.wp.jan2022.g2.model.DiscussionTag;
import mk.ukim.finki.wp.jan2022.g2.model.User;
import mk.ukim.finki.wp.jan2022.g2.model.exceptions.InvalidDiscussionIdException;
import mk.ukim.finki.wp.jan2022.g2.repository.DiscussionRepository;
import mk.ukim.finki.wp.jan2022.g2.repository.UserRepository;
import mk.ukim.finki.wp.jan2022.g2.service.DiscussionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final UserRepository userRepository;
    private final  DiscussionRepository discussionRepository;

    public DiscussionServiceImpl(UserRepository userRepository, DiscussionRepository discussionRepository) {
        this.userRepository = userRepository;
        this.discussionRepository = discussionRepository;
    }

    @Override
    public List<Discussion> listAll() {
        return this.discussionRepository.findAll();
    }

    @Override
    public Discussion findById(Long id) {
        return this.discussionRepository.findById(id).orElseThrow(InvalidDiscussionIdException::new);
    }

    @Override
    public Discussion create(String title, String description, DiscussionTag discussionTag,
                             List<Long> participants, LocalDate dueDate) {

        List<User> participants1 = this.userRepository.findAllById(participants);
        Discussion d = new Discussion(title,description,discussionTag,participants1,dueDate);
        return this.discussionRepository.save(d);
    }

    @Override
    public Discussion update(Long id, String title, String description,
                             DiscussionTag discussionTag, List<Long> participants) {
        List<User> participants1 = this.userRepository.findAllById(participants);
        Discussion d = this.findById(id);
        d.setParticipants(participants1);
        d.setTitle(title);
        d.setDescription(description);
        d.setTag(discussionTag);

        return this.discussionRepository.save(d);
    }

    @Override
    public Discussion delete(Long id) {
        Discussion d = findById(id);
        this.discussionRepository.delete(d);
        return d;
    }

    @Override
    public Discussion markPopular(Long id) {
        return null;
    }

    @Override
    public List<Discussion> filter(Long participantId, Integer daysUntilClosing) {
        return null;
    }
}
