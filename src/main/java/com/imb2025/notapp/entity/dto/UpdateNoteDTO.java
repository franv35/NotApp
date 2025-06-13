package com.imb2025.notapp.entity.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateNoteDTO {

    private Long id;
    private String title;
    private String content;

}
