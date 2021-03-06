package parser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Vacancy {
    private String title;
    private String url;
    private String authorName;
    private LocalDateTime dateTime;
    private String message;

    public Vacancy(String title, String url, String authorName, LocalDateTime dateTime, String message) {
        this.title = title;
        this.url = url;
        this.authorName = authorName;
        this.dateTime = dateTime;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthorName() {
        return authorName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(title, vacancy.title)
                && Objects.equals(url, vacancy.url)
                && Objects.equals(authorName, vacancy.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, authorName);
    }
}
