package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ParameterName;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Tag service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagValidator validator;
    private final TagMapper mapper;

    /**
     * Instantiates a new Tag service.
     *
     * @param repository the repository
     * @param validator  the validator
     * @param mapper     the mapper
     */
    @Autowired
    public TagServiceImpl(TagRepository repository, TagValidator validator,
                          @Qualifier("tagServiceMapper") TagMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public boolean create(Map<String, Object> tagData) {
        if (validator.checkName((String) tagData.get(ParameterName.NAME))) {
            String name = (String) tagData.get(ParameterName.NAME);
            if (!findByName(name).isPresent()) {
                Tag tag = new Tag();
                tag.setName(name);
                repository.create(tag);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        Optional<Tag> tag = repository.findById(id);
        if (tag.isPresent()) {
            repository.delete(tag.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<TagDto> findAll() {
        Set<Tag> tags = repository.findAll();
        return tags.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<TagDto> findById(long id) {
        Optional<Tag> tag = repository.findById(id);
        if (tag.isPresent()) {
            TagDto tagDto = mapper.mapToDto(tag.get());
            return Optional.of(tagDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TagDto> findByName(String name) {
        Optional<Tag> tag = repository.findByName(name);
        if (tag.isPresent()) {
            TagDto tagDto = mapper.mapToDto(tag.get());
            return Optional.of(tagDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Set<TagDto> findMostUsedTag() {
        return repository.findMostUsedTag()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

}
