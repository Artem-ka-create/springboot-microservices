package net.javaguides.organizationservice.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.organizationservice.dto.OrganizationDto;
import net.javaguides.organizationservice.entity.Organization;
import net.javaguides.organizationservice.mapper.OrganizationMapper;
import net.javaguides.organizationservice.repository.OrganizationalRepository;
import net.javaguides.organizationservice.service.OrganizationService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private OrganizationalRepository organizationalRepository;
    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        Organization organization = OrganizationMapper.mapToEntity(organizationDto);
         return OrganizationMapper.mapToDto(organizationalRepository.save(organization));
    }

    @Override
    public OrganizationDto getOrganizationByCode(String code) {
        Organization organization = organizationalRepository.findByOrganizationCode(code);
        return OrganizationMapper.mapToDto(organization);
    }
}
