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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagValidator validator;
    private final TagMapper mapper;

    @Autowired
    public TagServiceImpl(TagRepository repository, TagValidator validator,
                          @Qualifier("tagMapper") TagMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean create(Map<String, Object> tagData) {
        if (validator.checkName(String.valueOf(tagData.get(ParameterName.NAME)))) {
            Tag tag = new Tag();
            tag.setName(String.valueOf(tagData.get(ParameterName.NAME)));
            repository.create(tag);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }

    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = repository.findAll();
        return tags.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
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
}
