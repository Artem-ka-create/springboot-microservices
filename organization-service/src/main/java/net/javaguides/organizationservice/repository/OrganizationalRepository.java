package net.javaguides.organizationservice.repository;

import net.javaguides.organizationservice.dto.OrganizationDto;
import net.javaguides.organizationservice.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationalRepository  extends JpaRepository<Organization,Long> {

    Organization findByOrganizationCode(String code);
}
