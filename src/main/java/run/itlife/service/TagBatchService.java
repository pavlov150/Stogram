package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionOperations;

import java.util.Arrays;

@Service
@Transactional
public class TagBatchService {

    private TransactionOperations transactionOperations;
    private final TagService tagService;
    @Autowired
    public TagBatchService(TransactionOperations transactionOperations,
                           TagService tagService) {
        this.transactionOperations = transactionOperations;
        this.tagService = tagService;
    }

    public void createTags(String... names) {
        Arrays.asList(names).forEach(tagService::createTag);

/*        transactionOperations.executeWithoutResult(status -> {
            Arrays.asList(names).forEach(tagService::createTag);
        });*/
    }


}
