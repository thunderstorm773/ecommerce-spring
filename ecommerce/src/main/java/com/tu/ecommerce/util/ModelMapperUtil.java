package com.tu.ecommerce.util;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
public class ModelMapperUtil {

    private final ModelMapper modelMapper;

    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public <S, D> List<D> convertAll(List<S> source, Class<D> destination) {

        return source.stream()
                .map(element -> this.modelMapper.map(element, destination))
                .collect(Collectors.toList());
    }

    public <S, D> Page<D> convertToPage(Pageable pageable,
                                        Page<S> source,
                                        Class<D> destination) {

        List<S> pageContent = source.getContent();
        List<D> pageMappedContent = this.convertAll(pageContent, destination);
        long totalElements = source.getTotalElements();
        return new PageImpl<>(pageMappedContent, pageable, totalElements);
    }
}
