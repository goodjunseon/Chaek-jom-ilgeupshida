package com.junseon.root.domain.book.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookCreateReq {
    private String title;
    private String authorName;
    private String isbn;
    private String publisher;
    private int stock;
    private String publishedAt; // String으로 받아서 뱉을 때 LocalDate로 변환
    private List<Long> genreIds; // 여러 장르를 선택할 수 있으니 List로 생성

}
