package com.epam.esm.service.mapper.impl;

import com.epam.esm.repository.entity.Tag;
import com.epam.esm.service.mapper.Mapper;
import com.epam.esm.service.dto.TagDto;

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
