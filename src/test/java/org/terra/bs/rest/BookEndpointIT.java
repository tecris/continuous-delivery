package org.terra.bs.rest;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.terra.bs.entities.Author;
import org.terra.bs.entities.Book;
import org.terra.bs.entities.Genre;
import org.terra.bs.entities.Publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookEndpointIT {

	private static final String HOST = System.getProperty("test.host","localhost");
	private static final String PORT = System.getProperty("test.port","8080");

	private static final String REST_URL = "http://"+HOST+":"+PORT+"/bookstore/rest/books";

    @Test
    public void testCreateBook() throws JsonProcessingException {

        String expectedShortDescription = "Good read for the week-end";
        String expectedIsbnNo = "12-345-7777-00";
        double expectedPrice = 10D;

        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);

        ZonedDateTime zdt = ZonedDateTime.of(todayMidnight, ZoneId.of("UTC"));
        Calendar expectedDate = GregorianCalendar.from(zdt);


        String jsonInString = this.buildBookJson(expectedShortDescription, expectedIsbnNo, expectedPrice, expectedDate);
        given().contentType("application/json").body(jsonInString).when().post(REST_URL).then().body("shortDesc", equalTo(expectedShortDescription))
                .body("isbnNo", equalTo(expectedIsbnNo)).body("price", equalTo((float) expectedPrice))
                .body("publishedDate", equalTo(expectedDate.getTimeInMillis()));

    }

    @Test
    public void testGetBook() throws JsonProcessingException {

        String expectedShortDescription = "Good read for the week-end";
        String expectedIsbnNo = "12-345-7777-00";
        double expectedPrice = 10D;

        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);

        ZonedDateTime zdt = ZonedDateTime.of(todayMidnight, ZoneId.of("UTC"));
        Calendar expectedDate = GregorianCalendar.from(zdt);

        int bookId = this.createBook(expectedShortDescription, expectedIsbnNo, expectedPrice, expectedDate).getBookId();

        get(REST_URL + "/" + bookId).then().body("shortDesc", equalTo(expectedShortDescription))
                .body("isbnNo", equalTo(expectedIsbnNo)).body("price", equalTo((float) expectedPrice))
                .body("publishedDate", equalTo(expectedDate.getTimeInMillis()));
    }

    @Test
    public void testUpdateBook() throws JsonProcessingException {

        String expectedShortDescription = "Good read for the week-end";
        String expectedIsbnNo = "12-345-7777-00";
        double expectedPrice = 10D;

        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);

        ZonedDateTime zdt = ZonedDateTime.of(todayMidnight, ZoneId.of("UTC"));
        Calendar expectedDate = GregorianCalendar.from(zdt);

        Book book = this.createBook(expectedShortDescription, expectedIsbnNo, expectedPrice, expectedDate);

        String updatedShortDescription = "Good read for the week-end and holidays";
        String updatedIsbnNo = "12-345-7777-01";
        double updatedPrice = 20D;
        expectedDate = GregorianCalendar.from(zdt.plusDays(1));
        book.setIsbnNo(updatedIsbnNo);
        book.setPrice(updatedPrice);
        book.setPublishedDate(expectedDate);
        book.setShortDesc(updatedShortDescription);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(book);
        given().contentType("application/json").body(jsonInString).when().put(REST_URL + "/" + book.getBookId());

        get(REST_URL + "/" + book.getBookId()).then().body("shortDesc", equalTo(updatedShortDescription))
                .body("isbnNo", equalTo(updatedIsbnNo)).body("price", equalTo((float) updatedPrice))
                .body("publishedDate", equalTo(expectedDate.getTimeInMillis()));
    }

    @Test
    public void testDeleteBook() throws JsonProcessingException {

        String expectedShortDescription = "Good read for the week-end";
        String expectedIsbnNo = "12-345-7777-00";
        double expectedPrice = 10D;

        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);

        ZonedDateTime zdt = ZonedDateTime.of(todayMidnight, ZoneId.of("UTC"));
        Calendar expectedDate = GregorianCalendar.from(zdt);

        int bookId = this.createBook(expectedShortDescription, expectedIsbnNo, expectedPrice, expectedDate).getBookId();

        given().delete(REST_URL + "/" + bookId);
        given().expect().statusCode(404).get(REST_URL + "/" + bookId);
    }

    public Book createBook(String shortDescription, String isbnNo, double price, Calendar calendar)
            throws JsonProcessingException {

        String jsonInString = this.buildBookJson(shortDescription, isbnNo, price, calendar);

        return given().contentType("application/json").body(jsonInString).when().post(REST_URL).as(Book.class);
    }

    public String buildBookJson(String shortDescription, String isbnNo, double price, Calendar calendar)
            throws JsonProcessingException {
        Book book = this.buildBook(shortDescription, isbnNo, price, calendar);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(book);
    }

    private Book buildBook(String shortDescription, String isbnNo, double price, Calendar calendar)
            throws JsonProcessingException {
        Book book = new Book();
        Author author = new Author();
        author.setAuthorId(new AuthorEndpointIT().createGenericAuthor());
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);
        book.setAuthor(authorList);
        Genre genre = new Genre();
        genre.setGenreId(new GenreEndpointIT().createGenre("fantasy").getGenreId());
        Set<Genre> genreSet = new HashSet<>();
        genreSet.add(genre);
        book.setGenre(genreSet);
        book.setIsbnNo(isbnNo);
        book.setPrice(price);
        book.setPublishedDate(calendar);
        Publisher publisher = new Publisher();
        publisher.setPublisherId(new PublisherEndpointIT().createPublisher());
        book.setPublisher(publisher);
        book.setShortDesc(shortDescription);
        return book;
    }
}
