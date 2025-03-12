package com.example.demo.model.service;

/**
 * service for email sending notification
 * @since 12/02/2025
 * @author alier
 * */
public abstract class EmailService {
	/**
	 * method for email sending notification
	 * @since 12/02/2025
	 * @author alier
	 * */
	public abstract void sendRegistrationEmail(String email);
}
