package com.wolox.training.factory;

import com.wolox.training.dto.BookDto;
import com.wolox.training.mapper.BookMapper;
import java.util.Arrays;

public final class BookDtoFactory {

    public static BookDto withDefaultData() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Zen Speaks");
        bookDto.setSubtitle("Shouts of Nothingness");
        bookDto.setPublishers(BookMapper.objectToJsonNode(Arrays.asList("Anchor")));
        bookDto.setPublishDate("April 15, 1994");
        bookDto.setPages(160);
        bookDto.setIsbn(BookMapper.objectToJsonNode(Arrays.asList("0385472579")));
        bookDto.setAuthors(BookMapper.objectToJsonNode(Arrays.asList("/authors/OL2677946A")));
        return bookDto;
    }

}
