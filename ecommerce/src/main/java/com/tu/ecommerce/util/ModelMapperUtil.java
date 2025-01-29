package com.tu.ecommerce.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapperUtil {

    private final ModelMapper modelMapper;

    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    public <S, D> List<D> convertAll(List<S> source, Class<D> destination) {
        return source.stream()
                .map(element -> this.modelMapper.map(element, destination))
                .collect(Collectors.toList());
    }
}
