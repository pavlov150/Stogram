package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.entity.Tag;
import run.itlife.repository.TagRepository;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TagServiceImpl implements TagService {


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

  //  @Override
  //  public void createTags(String... names) {
   //     Arrays.asList(names).forEach(this::createTag);
  //  }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll(Sort.by(Sort.Order.asc("name")));
    }
}
