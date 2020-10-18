package pe.com.bodeguin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import pe.com.bodeguin.security.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired 
	private UsuarioDetailsService usuarioDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/productos/**").permitAll()
			.antMatchers("/index.html").permitAll()
		/*	.antMatchers("/carrera/**").authenticated()
			.antMatchers("/cursos/**").hasRole("ADMIN")
			.antMatchers("/estudiantes/**").hasAnyRole("USER", "MANAGER")
			.antMatchers("/docentes/**").hasAuthority("ACCESS_REST1")
			.antMatchers("/perfil/**").authenticated()
			.antMatchers("/api/rest1").hasAuthority("ACCESS_REST1")
			.antMatchers("/api/rest2").hasAuthority("ACCESS_REST2")
			.antMatchers("/api/usuarios").hasRole("ADMIN")*/
			.and().httpBasic();
		http.cors().and().csrf().disable();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.usuarioDetailService);
		return daoAuthenticationProvider;
	}
}
