package com.nitin.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenHelper 
{
	
	public static final long JWT_TOKEN_VALIDITY= 5 * 60 * 60;
	private String secret = "jwtTokenKey";
	
	public String getUsernameFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver)
	{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public Claims getAllClaimsFromToken(String token)
	{
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token)
	{
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails userDetails)
	{
		System.out.println("========================================="+new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY));
		System.out.println("========================================="+userDetails.getUsername());

		Map<String,Object> claims = new HashMap<>();
		return doGenerateToken(claims,userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String,Object> claims,String subject )
	{
		System.out.println("========================================="+new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY));
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public Boolean validateToken(String token,UserDetails userDetails)
	{
		System.out.println("========================================="+token);
		final String username = getUsernameFromToken(token);
		System.out.println("=================================================username"+username);
		System.out.println("----------------NITIN RAWAT-------------------------");
		System.out.println("===========---------------------------------------"+!isTokenExpired(token));
		System.out.println(userDetails);
		System.out.println(userDetails.getPassword());
		System.out.println("================+++++++++++++++++++++++++++++++++++++++++username from method  "+userDetails.getUsername());
//		System.out.println("===========---------------------------------------"+!isTokenExpired(token));
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	

}
