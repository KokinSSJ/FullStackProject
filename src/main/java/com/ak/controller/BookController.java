package com.ak.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ak.entity.Book;
import com.ak.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	// pobranie listy ksiazek
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String getAllBooks(Model model) { // model -> warstwa
		List<Book> list = bookService.findAll();
		model.addAttribute("bookList", list);//atrybuty potrzebne w *.jsp

		return "books"; // zeby zostala wyswietlona strona "books.jsp"
	}

	// zmiana widoku do dodania ksiazki (czyli z listy wszystkich do okna
	// dodania konkretnej)
	@RequestMapping(value = "/book/create", method = RequestMethod.GET)
	public String getCreateBookForm(Model model) {
		model.addAttribute("book", new Book());

		return "book-create"; // => book_create.jsp
	}

	// przygotowuje odpowiedni obiekt ksiazki do edycji (na podstawie id
	// ksiazki)
	// zwraca odpowiedni widok (strone *.jsp)
	@RequestMapping(value = "/book/edit/{id}", method = RequestMethod.GET)
	public String getEditBookForm(Model model, @PathVariable Long id) {
		// bookService.save(bookService.findOne(id)); //zapis gdzie indziej
		model.addAttribute("book", bookService.findOne(id));

		return "book-create"; // przekierowanie na ta sama strone co tworzenie
	}

	@RequestMapping(value = "/book/save", method = RequestMethod.POST)
	public String postCreateBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
		// book pochodzi z "model" dzieki @ModelAttribute,
		// @Valid sprawdza czy @Min i @Size sa ok (z Book.java)
		// BindingResult -> przechowywane informacje o powodzeniu /
		// niepowodzeniu wprowadzania danych (np. za malo znakow) -> wtedy
		// zostanie na stronie!
		if (bindingResult.hasErrors()) {
			return "book-create";
		} else {
			bookService.save(book);
			return "redirect:/books"; //bez redirect, nie zostanie pobrana aktualna baza danych, ale zostaje wyświetlona!
		}

	}
	
	//metoda do usuwania ksiazki na podstawie id przekazanego jako parametr 
	@RequestMapping(value="/book/delete/{id}", method=RequestMethod.POST)
	public String deleteBook ( @PathVariable Long id){
		bookService.delete(id);
		return "redirect:/books";
	}
	
}
