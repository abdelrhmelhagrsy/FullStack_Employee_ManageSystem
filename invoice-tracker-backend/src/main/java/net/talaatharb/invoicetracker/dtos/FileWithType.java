package net.talaatharb.invoicetracker.dtos;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileWithType {
    private String type;
    private Resource resource;
}