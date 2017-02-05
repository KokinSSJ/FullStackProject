package com.ak.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ak.service.UserService;

//TODO: sprawdzić czy napewno okreslone jest całe security
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final int ENCODE_STRENGTH = 10; //im wyższa wartość tym trudniej odszyfrować
	
	@Autowired
	private UserService userService;
	
	//włączenie, okreslenie szyfrowania 
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		//inicjalizujemy obiekt zwiazany z algorytmem szyforwania hasla
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(ENCODE_STRENGTH);
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	
	//okreslenie autoryzacji odnosnie metod kontrolera =>trzeba być zalogowanym zeby zobaczyć wypożyczone ksiazki itd
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		//mozemy dać kilka antmatchers bo wywolujem na tym obiekcie i działamy na nic 
		httpSecurity.authorizeRequests().antMatchers("/login").permitAll() //każdy może przejsc do logowania
			.antMatchers("/register").permitAll()					// kazdy moze przejsc do rejestracji
			.antMatchers("/users/**", "/create-user").hasRole("ADMIN")	//moze robić tylko admin -> zalogowany!
			.antMatchers("/ADMIN/**").hasRole("ADMIN")	//operacje admina moze robić tylko admin
			.antMatchers("/**").authenticated()	// wszystkie inne musi być zalogowany, gdyby ktoś z palca coś wrzucił
			.and()
			.formLogin() //informacje o miejscach login i password na stronie beda w formatce
			.usernameParameter("email")  //jezeli mail nie istnieje -> nie bedzie mógł otrzymać maila -> zwroci błąd że mail nie odpowiada
			.passwordParameter("password") 
			.loginPage("/login") //jaka strona do logowania
			.and()
			.logout()
			.logoutUrl("/logout") //przerwanie sesji spring security
			.logoutSuccessUrl("/logout?logout") // sprawdza czy wylgoowanie jest poprawne
			.and()
			.csrf().disable(); // można wyłączyć metodę z zapobieganiem ataku cross site request forgery - rodzaj ataku!
		
		
		
	}
	

}