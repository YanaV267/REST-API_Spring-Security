package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component("tagMapper")
public class TagMapper implements Mapper<Tag, TagDto> {
    @Override
    public TagDto mapToDto(Tag tag) {
        return null;
    }

    @Override
    public Tag mapToEntity(TagDto tagDto) {
        return null;
    }
}
