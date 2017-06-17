//package com.weberfly.controller;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.Principal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.weberfly.entities.User;
//import com.weberfly.service.SessionService;
//import com.weberfly.service.TweetAnalyseService;
//import com.weberfly.service.UserService;
//import com.weberfly.service.threads.PostLocationDetection;
//import com.weberfly.util.CustomErrorType;
//
//@RestController
//@RequestMapping("acount")
//public class ToolsCompareController {
//	public static final Logger logger = LoggerFactory.getLogger(ToolsCompareController.class);
//	//Save the uploaded file to this folder
//    private static String UPLOADED_FOLDER = "C://Users//kamal//workspace//fly-knowledge//src//main//resources//static//images//";
//	
//	@Autowired
//	TweetAnalyseService tweetAnalyseService;
//	
//	@Autowired
//	private ApplicationContext applicationContext;
//	@Autowired
//	HttpSession session;
//	
//	@RequestMapping(value = "/nltkRate", method = RequestMethod.GET)
//	public ResponseEntity<?> getNltkRate() {
//		Map<String, Map<String, Double>> stats = new HashMap<>();
//		try {
//			Map<String, Double> ntltkAnalyseRAte = tweetAnalyseService.getAnalyseRateForNLTK();
//			stats.put("nltk", ntltkAnalyseRAte);
//			
//			Map<String, Double> dumaxkAnalyseRAte = tweetAnalyseService.getAnalyseRateForDumax();
//			stats.put("dumax", dumaxkAnalyseRAte);
//			Map<String, Double> gatekAnalyseRAte = tweetAnalyseService.getAnalyseRateForGate();
//			stats.put("gate", gatekAnalyseRAte);
//			Map<String, Double> combainingkAnalyseRAte = tweetAnalyseService.getAnalyseRateGeneral();
//			stats.put("combaining", combainingkAnalyseRAte);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		
//
//		return new ResponseEntity<Map<String, Map<String, Double>>>(stats, HttpStatus.CREATED);
//	}
//	@RequestMapping(value = "/nltkRate", method = RequestMethod.GET)
//	public ResponseEntity<?> getNltkRate() {
//		Map<String, Map<String, Double>> stats = new HashMap<>();
//		try {
//			Map<String, Double> ntltkAnalyseRAte = tweetAnalyseService.getAnalyseRateForNLTK();
//			stats.put("nltk", ntltkAnalyseRAte);
//			
//			Map<String, Double> dumaxkAnalyseRAte = tweetAnalyseService.getAnalyseRateForDumax();
//			stats.put("dumax", dumaxkAnalyseRAte);
//			Map<String, Double> gatekAnalyseRAte = tweetAnalyseService.getAnalyseRateForGate();
//			stats.put("gate", gatekAnalyseRAte);
//			Map<String, Double> combainingkAnalyseRAte = tweetAnalyseService.getAnalyseRateGeneral();
//			stats.put("combaining", combainingkAnalyseRAte);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		
//
//		return new ResponseEntity<Map<String, Map<String, Double>>>(stats, HttpStatus.CREATED);
//	}
//	@RequestMapping(value = "/nltkRate", method = RequestMethod.GET)
//	public ResponseEntity<?> getGateRate() {
//		Map<String, Map<String, Double>> stats = new HashMap<>();
//		try {
//			Map<String, Double> ntltkAnalyseRAte = tweetAnalyseService.getAnalyseRateForNLTK();
//			stats.put("nltk", ntltkAnalyseRAte);
//			
//			Map<String, Double> dumaxkAnalyseRAte = tweetAnalyseService.getAnalyseRateForDumax();
//			stats.put("dumax", dumaxkAnalyseRAte);
//			Map<String, Double> gatekAnalyseRAte = tweetAnalyseService.getAnalyseRateForGate();
//			stats.put("gate", gatekAnalyseRAte);
//			Map<String, Double> combainingkAnalyseRAte = tweetAnalyseService.getAnalyseRateGeneral();
//			stats.put("combaining", combainingkAnalyseRAte);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		
//
//		return new ResponseEntity<Map<String, Map<String, Double>>>(stats, HttpStatus.CREATED);
//	}
//	@RequestMapping(value = "/comapreTols", method = RequestMethod.GET)
//	public ResponseEntity<?> getcompareTols() {
//		Map<String, Map<String, Double>> stats = new HashMap<>();
//		try {
//			Map<String, Double> ntltkAnalyseRAte = tweetAnalyseService.getAnalyseRateForNLTK();
//			stats.put("nltk", ntltkAnalyseRAte);
//			
//			Map<String, Double> dumaxkAnalyseRAte = tweetAnalyseService.getAnalyseRateForDumax();
//			stats.put("dumax", dumaxkAnalyseRAte);
//			Map<String, Double> gatekAnalyseRAte = tweetAnalyseService.getAnalyseRateForGate();
//			stats.put("gate", gatekAnalyseRAte);
//			Map<String, Double> combainingkAnalyseRAte = tweetAnalyseService.getAnalyseRateGeneral();
//			stats.put("combaining", combainingkAnalyseRAte);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		
//
//		return new ResponseEntity<Map<String, Map<String, Double>>>(stats, HttpStatus.CREATED);
//	}
//	
//}
