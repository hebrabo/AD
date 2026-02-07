package org.accesodatos.semana14PostPut.mappers;

import org.accesodatos.semana14PostPut.dtos.response.CasaDTO;
import org.accesodatos.semana14PostPut.models.Casa;
import org.springframework.stereotype.Component;

@Component
public class CasaMapper {
    private final ProfesorMapper profesorMapper;

    public CasaMapper(ProfesorMapper profesorMapper) {
        this.profesorMapper = profesorMapper;
    }

    public CasaDTO toDto(Casa casa) {
        if (casa == null) {
            return null;
        }
        CasaDTO dto = new CasaDTO();
        dto.setId(casa.getIdCasa());
        dto.setNombre(casa.getNombre());
        dto.setFundador(casa.getFundador());
        dto.setFantasma(casa.getFantasma());
        dto.setJefe(profesorMapper.toDto(casa.getJefeCasa()));
        dto.setEstudiantes(
                casa.getEstudiantes() != null
                ? casa.getEstudiantes().stream()
                        .map(t -> t.getNombre() + " " + t.getApellido())
                        .toList()
                        : null);
        return dto;
    }
}
