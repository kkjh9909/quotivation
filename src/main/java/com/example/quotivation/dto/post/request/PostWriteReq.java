package com.example.quotivation.dto.post.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostWriteReq {

    private String writer;
    private String password;
    private String title;
    private String content;
}
