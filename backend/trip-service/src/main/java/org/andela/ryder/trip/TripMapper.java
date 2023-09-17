package org.andela.ryder.trip;

import org.andela.ryder.shared.dto.TripDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    private final ModelMapper modelMapper;

    public TripMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TripEntity toEntity(TripDTO tripRequest) {
        return  modelMapper.map(tripRequest, TripEntity.class);
    }

    public TripDTO toDTO(TripEntity tripEntity) {
        var t= modelMapper.map(tripEntity, TripDTO.class);
        return t;
    }
}
