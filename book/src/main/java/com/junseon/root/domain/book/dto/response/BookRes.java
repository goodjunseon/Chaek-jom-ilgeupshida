package com.junseon.root.domain.book.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRes {
    private Long bookId;
    private String title;
    private String authorName;
    private int stock;
    private List<String> genres;
}
