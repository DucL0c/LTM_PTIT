/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP;

import java.io.Serializable;

/**
 *
 * @author Duc Loc
 */
public class Book implements Serializable {
    private static final long serialVersionUID = 20102003;
    private String id;
    private String title;
    private String author;
    private double price;
    private int pageCount;

    public Book(String id, String title, String author, double price, int pageCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.pageCount = pageCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "Book{id='" + id + "', title='" + title + "', author='" + author + "', price=" + price + ", pageCount=" + pageCount + "}";
    }
}
