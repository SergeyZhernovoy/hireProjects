package ru.biblio.web.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс предназначен для передачи контента книги на сторону браузера,
 * за содержимым файла ходим только один раз, листание страниц происходит на
 * стороне клиента
 */
public class BookDTO {
    /**идентификатор книги*/
    private Integer bookId;
    /**идентификатор пользователя */
    private Long userId;
    /**содержимое страниц */
    private Map<Integer,Page> contentPage = new HashMap<>();
    /**всего страниц */
    private Integer totalPage = 0;
    /**текущая страница */
    private Integer currentPage = 1;

    private String style;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Map<Integer, Page> getContentPage() {
        return contentPage;
    }

    public void setContentPage(Page page) {
        this.contentPage.put(page.getPageIndex(),page);
        totalPage++;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    @SuppressWarnings("unused")
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
