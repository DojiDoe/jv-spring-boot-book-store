package mate.academy.bookstore.repository;

import mate.academy.bookstore.dto.book.BookSearchParametersDto;
import mate.academy.bookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

public interface SpecificationBuilder<T> {
    Specification<Book> build(BookSearchParametersDto bookSearchParametersDto);

    @Component
    class TitleSpecificationProvider implements SpecificationProvider<Book> {
        @Override
        public String getKey() {
            return "title";
        }

        public Specification<Book> getSpecification(String param) {
            return (root, query, criteriaBuilder) -> root.get("title").in(param);
        }
    }
}
