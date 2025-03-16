package org.urfu.adservice.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    private UUID id;
    private UUID author;
    private String content;
    private long timestamp;
}
