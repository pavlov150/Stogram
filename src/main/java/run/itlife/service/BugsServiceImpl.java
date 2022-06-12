package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.itlife.dto.BugsDto;
import run.itlife.entity.Bugs;
import run.itlife.repository.BugsRepository;
import run.itlife.repository.UserRepository;
import run.itlife.utils.SecurityUtils;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BugsServiceImpl implements BugsService {

    private final BugsRepository bugsRepository;
    private final UserRepository userRepository;

    @Autowired
    public BugsServiceImpl(BugsRepository bugsRepository, UserRepository userRepository) {
        this.bugsRepository = bugsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(BugsDto bugsDto) {
        Bugs bugs = new Bugs();
        bugs.setBugText(bugsDto.getBugText());
        String username = SecurityUtils.getCurrentUserDetails().getUsername();
        bugs.setUserId(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        bugs.setCreatedAt(LocalDateTime.now());
        bugsRepository.save(bugs);
    }

    @Override
    public List<Bugs> listAllBugs() {
        List<Bugs> bugs =  bugsRepository.findAll(Sort.by("createdAt").descending());
        for (Bugs b : bugs) {
            b.getBugText().length();
        }
        return bugs;
    }

}
