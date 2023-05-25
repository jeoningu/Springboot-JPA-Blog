package com.jig.blog.dto;

import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardReqDto {
    private String title;

    private String content;
}
