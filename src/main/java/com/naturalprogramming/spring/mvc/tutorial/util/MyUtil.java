package com.naturalprogramming.spring.mvc.tutorial.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class MyUtil {

	private static MessageSource messageSource;

	@Autowired
	public MyUtil(MessageSource messageSource) {
		MyUtil.messageSource = messageSource;
	}

	public static void flash(RedirectAttributes redirectAttributes, String kind, String messageKey) {
		redirectAttributes.addFlashAttribute("flashKind", kind);
		redirectAttributes.addFlashAttribute("flashMessage", MyUtil.getMessage(messageKey));
	}

	public static String getMessage(String messageKey, Object... args) {
		return messageSource.getMessage(messageKey, args, Locale.getDefault());
	}

	private static String hostAndPort;

	@Value("${hostAndPort}")
	public void setHostAndPort(String hostAndPort) {
		MyUtil.hostAndPort = hostAndPort;
	}

	private static String activeProfiles;

	@Value("${spring.profiles.active}")
	public void setActiveProfiles(String activeProfiles) {
		MyUtil.activeProfiles = activeProfiles;
	}

	public static boolean isDev() {
		return activeProfiles.equals("dev");
	}

	public static String hostUrl() {
		return (isDev() ? "http://" : "https://") + hostAndPort;
	}
}
