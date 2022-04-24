package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.entity.Post;
import run.itlife.entity.Tag;
import run.itlife.repository.TagRepository;
import java.util.List;

// Уровень обслуживания
// Класс, реализующий интерфейс, который отвечает за логику создания тегов
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TagServiceImpl implements TagService {

    // сервисы в свою очередь включают репозиторий
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name.toLowerCase());
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll(Sort.by(Sort.Order.asc("name")));
    }

   /* @Override
    public List<Tag> findByTagName(String username, long postId) {
        List<Tag> tags = tagRepository.findByTagName(username, postId);
        for (Tag p : tags) {
            p.getName().length();
        }
        return tags;
    }*/

}