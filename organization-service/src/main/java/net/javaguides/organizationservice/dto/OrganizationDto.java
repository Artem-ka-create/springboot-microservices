package net.javaguides.organizationservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto {

    private Long id;

    private String organizationName;

    private String organizationDescription;

    private String organizationCode;

    private LocalDateTime createdDate;
}
